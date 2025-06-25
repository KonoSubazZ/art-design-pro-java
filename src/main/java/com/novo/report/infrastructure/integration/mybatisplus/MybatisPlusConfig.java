package com.novo.report.infrastructure.integration.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.novo.report.infrastructure.integration.minioplus.MetadataRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MybatisPlusSqLogProperties.class)
@MapperScan(
    value = {"com.novo.report.infrastructure.persistence.repository"},
    basePackageClasses = {MetadataRepositoryImpl.class})
public class MybatisPlusConfig {
  private final SqLogInterceptor sqLogInterceptor;
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(sqLogInterceptor);
    // 如果配置多个插件, 切记分页最后添加 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    return interceptor;
  }
}
