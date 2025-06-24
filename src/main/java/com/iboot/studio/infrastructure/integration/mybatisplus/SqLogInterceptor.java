package com.iboot.studio.infrastructure.integration.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/** SQL日志拦截器，用于打印完整的SQL语句（包含参数值） 支持格式化SQL和统计执行时间 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SqLogInterceptor implements InnerInterceptor {
  /** 配置属性 */
  private final MybatisPlusSqLogProperties mybatisPlusSqLogProperties;
  
  /** 用于格式化SQL的正则表达式模式（线程安全） */
  private static final Pattern SQL_FORMAT_PATTERN = Pattern.compile("'[^']*'|\"[^\"]*\"|[^'\"]++");
  
  /** 日期格式化器（线程安全） */
  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /** 问号占位符 */
  private static final String QUESTION_MARK = "?";
  
  /** NULL值的字符串表示 */
  private static final String NULL_STRING = "NULL";

  @Override
  public void beforePrepare(
      StatementHandler statementHandler, Connection connection, Integer transactionTimeout) {
    // 如果未启用SQL日志，直接返回
    if (!mybatisPlusSqLogProperties.isEnabled()) {
      return;
    }
    
    // 获取SQL相关信息
    MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
    MappedStatement mappedStatement =
        (MappedStatement) metaObject.getValue("delegate.mappedStatement");
    BoundSql boundSql = statementHandler.getBoundSql();
    
    // 获取SQL语句
    String sql = boundSql.getSql();
    if (StringUtils.isBlank(sql)) {
      return;
    }
    
    // 创建线程安全的性能计时器
    String taskId = "SQL-" + Thread.currentThread().getId() + "-" + System.nanoTime();
    StopWatch stopWatch = new StopWatch(taskId);
    stopWatch.start("解析");
    
    try {
      // 处理SQL并打印
      String processedSql = processSql(mappedStatement, boundSql, sql);
      stopWatch.stop();
      log.info("==> 执行SQL: {}", processedSql);
      
      // 执行时间统计
      if (mybatisPlusSqLogProperties.isShowExecuteTime()) {
        executeWithTiming(statementHandler, connection, transactionTimeout, stopWatch);
      }
    } catch (Exception e) {
      log.error("SQL日志处理异常", e);
      safelyStopWatch(stopWatch);
    }
  }
  
  /** 处理SQL语句：格式化并替换参数 */
  private String processSql(
      MappedStatement mappedStatement, BoundSql boundSql, String originalSql) {
    // 格式化SQL（如果启用）
    String sql = mybatisPlusSqLogProperties.isFormatSql() ? formatSql(originalSql) : originalSql;
    
    // 获取参数
    Object parameterObject = boundSql.getParameterObject();
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    
    // 如果没有参数，直接返回SQL
    if (parameterObject == null || parameterMappings == null || parameterMappings.isEmpty()) {
      return sql;
    }
    
    // 替换参数
    return replaceSqlParameters(
        mappedStatement.getConfiguration(), boundSql, sql, parameterObject, parameterMappings);
  }
  
  /** 执行SQL并统计时间 */
  private void executeWithTiming(
      StatementHandler statementHandler,
      Connection connection,
      Integer transactionTimeout,
      StopWatch stopWatch) {
    stopWatch.start("执行");
    try {
      InnerInterceptor.super.beforePrepare(statementHandler, connection, transactionTimeout);
    } finally {
      safelyStopWatch(stopWatch);
      log.info("==> 执行SQL耗时: {} ms", stopWatch.getTotalTimeMillis());
      
      if (log.isDebugEnabled()) {
        log.debug("==> SQL耗时详情: {}", stopWatch.prettyPrint());
      }
    }
  }
  
  /** 安全停止计时器 */
  private void safelyStopWatch(StopWatch stopWatch) {
    if (stopWatch.isRunning()) {
      stopWatch.stop();
    }
  }
  
  /** 替换SQL中的参数占位符 */
  private String replaceSqlParameters(
      Configuration configuration,
      BoundSql boundSql,
      String sql,
      Object parameterObject,
      List<ParameterMapping> parameterMappings) {
    TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
    StringBuilder sqlBuilder = new StringBuilder(sql);
    int offset = 0;
    
    for (ParameterMapping parameterMapping : parameterMappings) {
      if (parameterMapping.getMode().name().equals("OUT")) {
        continue;
      }
      
      // 获取参数值
      Object value =
          extractParameterValue(boundSql, parameterObject, typeHandlerRegistry, parameterMapping);
      
      // 替换参数
      offset = replaceParameterPlaceholder(sqlBuilder, value, offset);
    }
    
    return sqlBuilder.toString();
  }
  
  /** 提取参数值 */
  private Object extractParameterValue(
      BoundSql boundSql,
      Object parameterObject,
      TypeHandlerRegistry typeHandlerRegistry,
      ParameterMapping parameterMapping) {
    String propertyName = parameterMapping.getProperty();
    
    if (boundSql.hasAdditionalParameter(propertyName)) {
      return boundSql.getAdditionalParameter(propertyName);
    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
      return parameterObject;
    } else {
      MetaObject metaObject = SystemMetaObject.forObject(parameterObject);
      return metaObject.getValue(propertyName);
    }
  }
  
  /**
   * 在SQL中替换参数占位符
   *
   * @return 替换后的新偏移量
   */
  private int replaceParameterPlaceholder(StringBuilder sqlBuilder, Object value, int offset) {
    int questionMarkIndex = sqlBuilder.indexOf(QUESTION_MARK, offset);
    if (questionMarkIndex == -1) {
      return offset;
    }
    
    String replacement = formatParameterValue(value);
    sqlBuilder.replace(questionMarkIndex, questionMarkIndex + 1, replacement);
    return questionMarkIndex + replacement.length();
  }
  
  /** 格式化参数值为SQL字符串 */
  private String formatParameterValue(Object value) {
    if (value == null) {
      return NULL_STRING;
    } else if (value instanceof String) {
      return "'" + escapeQuotes(value.toString()) + "'";
    } else if (value instanceof Date) {
      return "'" + formatDate((Date) value) + "'";
    } else if (value instanceof LocalDateTime) {
      return "'" + ((LocalDateTime) value).format(DATE_FORMATTER) + "'";
    } else {
      return value.toString();
    }
  }
  
  /** 转义SQL字符串中的引号 */
  private String escapeQuotes(String str) {
    return str.replace("'", "''");
  }
  
  /** 日期格式化 */
  private String formatDate(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(DATE_FORMATTER);
  }
  
  /** 格式化SQL语句，移除多余空格但保留字符串字面量中的空格 */
  private String formatSql(String sql) {
    Matcher matcher = SQL_FORMAT_PATTERN.matcher(sql);
    StringBuilder result = new StringBuilder(sql.length());
    
    while (matcher.find()) {
      String group = matcher.group();
      if (isQuotedString(group)) {
        result.append(group);
      } else {
        result.append(normalizeWhitespace(group));
      }
    }
    
    return result.toString().trim();
  }
  
  /** 判断是否为引号包围的字符串 */
  private boolean isQuotedString(String text) {
    return text.startsWith("'") || text.startsWith("\"");
  }
  
  /** 规范化空白字符 */
  private String normalizeWhitespace(String text) {
    return text.replaceAll("\\s+", " ");
  }
}
