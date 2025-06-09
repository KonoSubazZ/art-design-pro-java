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

package com.iboot.studio.common.config.log;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

/** MDC线程装饰器，用于传递MDC上下文到子线程 */
public class MdcThreadPoolTaskDecorator implements TaskDecorator {

  @NotNull
  @Override
  public Runnable decorate(@NotNull Runnable runnable) {
    // 获取当前线程的MDC上下文
    Map<String, String> contextMap = MDC.getCopyOfContextMap();
    return () -> {
      try {
        // 将父线程的MDC上下文设置到子线程中
        if (contextMap != null) {
          MDC.setContextMap(contextMap);
        }
        runnable.run();
      } finally {
        // 清理子线程的MDC上下文
        MDC.clear();
      }
    };
  }
}
