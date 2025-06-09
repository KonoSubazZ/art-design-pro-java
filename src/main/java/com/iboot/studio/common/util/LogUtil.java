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

package com.iboot.studio.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** 日志工具类 */
public class LogUtil {
  private static final Logger logger = LogManager.getLogger(LogUtil.class);

  public static void info(String message) {
    logger.info(message);
  }

  public static void info(String message, Object... params) {
    logger.info(message, params);
  }

  public static void error(String message) {
    logger.error(message);
  }

  public static void error(String message, Throwable throwable) {
    logger.error(message, throwable);
  }

  public static void error(String message, Object... params) {
    logger.error(message, params);
  }

  public static void warn(String message) {
    logger.warn(message);
  }

  public static void warn(String message, Object... params) {
    logger.warn(message, params);
  }

  public static void debug(String message) {
    logger.debug(message);
  }

  public static void debug(String message, Object... params) {
    logger.debug(message, params);
  }
}
