package com.iboot.studio.common.enumdict;

/**
 * 枚举字典接口
 * 所有使用@EnumDict注解的枚举类必须实现此接口
 */
public interface IEnumDict {
    /**
     * 获取枚举值
     * @return 枚举值
     */
    Object getValue();

    /**
     * 获取枚举标签
     * @return 枚举标签
     */
    String getLabel();
} 