package com.novo.report.common.enumdict;

import java.lang.annotation.*;

/**
 * 枚举字典注解
 * 被此注解标记的枚举类必须实现{@link IEnumDict}接口
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumDict {
    /**
     * 唯一标识符（确保全局唯一），如果不指定，则使用类的全限定名
     */
    String value() default "";

    /**
     * 显示名称，用于前端展示
     */
    String displayName() default "";
} 