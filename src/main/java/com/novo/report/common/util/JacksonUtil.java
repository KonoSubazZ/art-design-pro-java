/*
 * Proprietary Software License
 *
 * Copyright (c) 2025 iboot
 *
 * This software and its associated documentation ("Software") are proprietary property of iboot.
 * Without explicit written permission from iboot, no individual or entity may:
 *
 * 1. Copy, modify, merge, publish, distribute, sublicense, or sell copies of the Software;
 * 2. Reverse engineer, decompile, or disassemble the Software;
 * 3. Remove or alter any copyright notices or other proprietary markings in the Software;
 * 4. Use the Software for any commercial purposes.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * IBOOT BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * The Software may not be used without explicit written permission from iboot.
 * Author: tangsc.
 */

package com.novo.report.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/** Jackson工具类 提供与Hutool的JSONUtil类似的功能 */
@Slf4j
public class JacksonUtil {
  private static final ObjectMapper MAPPER =
      new ObjectMapper()
          // 注册Java8时间模块
          .registerModule(new JavaTimeModule())
          // 忽略未知属性
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          // 美化输出
          .configure(SerializationFeature.INDENT_OUTPUT, false)
          // 日期时间格式化
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
          // 允许空对象
          .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

  /**
   * 对象转JSON字符串
   *
   * @param obj 对象
   * @return JSON字符串
   */
  public static String toJsonStr(Object obj) {
    try {
      return MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.error("对象转JSON字符串失败", e);
      return null;
    }
  }

  /**
   * 对象转JSON字符串（美化输出）
   *
   * @param obj 对象
   * @return 美化后的JSON字符串
   */
  public static String toJsonPrettyStr(Object obj) {
    try {
      return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.error("对象转JSON字符串失败", e);
      return null;
    }
  }

  /**
   * JSON字符串转对象
   *
   * @param json JSON字符串
   * @param clazz 目标类型
   * @return 对象
   */
  public static <T> T parseObject(String json, Class<T> clazz) {
    try {
      return MAPPER.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      log.error("JSON字符串转对象失败", e);
      return null;
    }
  }

  /**
   * JSON字符串转对象（支持泛型）
   *
   * @param json JSON字符串
   * @param typeReference 类型引用
   * @return 对象
   */
  public static <T> T parseObject(String json, TypeReference<T> typeReference) {
    try {
      return MAPPER.readValue(json, typeReference);
    } catch (JsonProcessingException e) {
      log.error("JSON字符串转对象失败", e);
      return null;
    }
  }

  /**
   * JSON字符串转List
   *
   * @param json JSON字符串
   * @param clazz 目标类型
   * @return List对象
   */
  public static <T> List<T> parseArray(String json, Class<T> clazz) {
    try {
      return MAPPER.readValue(
          json, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (JsonProcessingException e) {
      log.error("JSON字符串转List失败", e);
      return null;
    }
  }

  /**
   * JSON字符串转Map
   *
   * @param json JSON字符串
   * @return Map对象
   */
  public static Map<String, Object> parseMap(String json) {
    try {
      return MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
    } catch (JsonProcessingException e) {
      log.error("JSON字符串转Map失败", e);
      return null;
    }
  }

  /**
   * 从输入流读取JSON
   *
   * @param inputStream 输入流
   * @param clazz 目标类型
   * @return 对象
   */
  public static <T> T parseObject(InputStream inputStream, Class<T> clazz) {
    try {
      return MAPPER.readValue(inputStream, clazz);
    } catch (IOException e) {
      log.error("从输入流读取JSON失败", e);
      return null;
    }
  }

  /**
   * 从Reader读取JSON
   *
   * @param reader Reader对象
   * @param clazz 目标类型
   * @return 对象
   */
  public static <T> T parseObject(Reader reader, Class<T> clazz) {
    try {
      return MAPPER.readValue(reader, clazz);
    } catch (IOException e) {
      log.error("从Reader读取JSON失败", e);
      return null;
    }
  }

  /**
   * 获取JSON节点
   *
   * @param json JSON字符串
   * @return JsonNode对象
   */
  public static JsonNode parseNode(String json) {
    try {
      return MAPPER.readTree(json);
    } catch (JsonProcessingException e) {
      log.error("解析JSON节点失败", e);
      return null;
    }
  }

  /**
   * 判断字符串是否为JSON格式
   *
   * @param json JSON字符串
   * @return 是否为JSON格式
   */
  public static boolean isJson(String json) {
    try {
      MAPPER.readTree(json);
      return true;
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  /**
   * 获取ObjectMapper实例
   *
   * @return ObjectMapper实例
   */
  public static ObjectMapper getMapper() {
    return MAPPER;
  }
}
