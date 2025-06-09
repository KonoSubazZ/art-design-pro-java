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

package com.iboot.studio.common.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/** 可重复读取请求体的请求包装器 用于解决请求体只能读取一次的问题 */
@Slf4j
public class CachedBodyRequestWrapper extends HttpServletRequestWrapper {
  private final byte[] body;
  private final long startTime;

  public CachedBodyRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    this.startTime = System.currentTimeMillis();
    // 读取请求体并缓存
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
      String bodyStr = reader.lines().collect(Collectors.joining());
      this.body = bodyStr.getBytes(StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error("读取请求体异常: {}", e.getMessage(), e);
      throw e;
    }
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return byteArrayInputStream.available() == 0;
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException("不支持的操作");
      }

      @Override
      public int read() throws IOException {
        return byteArrayInputStream.read();
      }
    };
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
  }

  /** 获取缓存的请求体内容 */
  public String getBodyAsString() {
    return new String(body, StandardCharsets.UTF_8);
  }

  /** 获取请求开始时间 */
  public long getStartTime() {
    return startTime;
  }
}
