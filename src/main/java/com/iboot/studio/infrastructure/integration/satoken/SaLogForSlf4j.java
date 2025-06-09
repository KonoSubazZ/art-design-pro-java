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

package com.iboot.studio.infrastructure.integration.satoken;

import cn.dev33.satoken.log.SaLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** 将 Sa-Token log 信息转接到 Slf4j */
@Component
public class SaLogForSlf4j implements SaLog {
  Logger log = LoggerFactory.getLogger(SaLogForSlf4j.class);

  @Override
  public void trace(String str, Object... args) {
    log.trace(str, args);
  }

  @Override
  public void debug(String str, Object... args) {
    log.debug(str, args);
  }

  @Override
  public void info(String str, Object... args) {
    log.info(str, args);
  }

  @Override
  public void warn(String str, Object... args) {
    log.warn(str, args);
  }

  @Override
  public void error(String str, Object... args) {
    log.error(str, args);
  }

  @Override
  public void fatal(String str, Object... args) {
    log.error(str, args);
  }
}
