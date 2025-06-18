package com.cdyhrj.fastorm.entity.enhance.generator.proxy;

/**
 * Fun 生成器
 *
 * @author huangqi
 */

public interface FunGenerator {
    /**
     * 生成函数
     *
     * @param classOfT 类
     * @return 函数字符串
     */
    String generate(Class<?> classOfT);
}
