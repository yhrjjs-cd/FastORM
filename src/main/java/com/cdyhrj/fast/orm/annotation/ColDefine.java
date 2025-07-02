package com.cdyhrj.fast.orm.annotation;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列定义
 *
 * @author huangqi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface ColDefine {
    /**
     * @return 字段类型
     */
    ColType type() default ColType.VARCHAR;

    /**
     * @return 字段长度，为数字时为数字总长
     */
    int length() default 10;

    /**
     * @return 精度，为数字时有效
     */
    int precision() default 2;

    /**
     * @return 是否可为空
     */
    boolean nullable() default true;

    /**
     * @return 备注信息
     */
    String comment() default "";

    /**
     * 值适配器
     *
     * @return 值适配器
     */
    Class<? extends ValueAdapter<?>> adapter();
}
