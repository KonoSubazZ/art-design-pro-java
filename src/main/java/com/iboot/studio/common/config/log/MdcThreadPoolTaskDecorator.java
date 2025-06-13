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
