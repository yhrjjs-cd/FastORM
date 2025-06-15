package com.cdyhrj.fastorm.annotation;

import com.cdyhrj.fastorm.annotation.enums.DefaultValueType;
import com.cdyhrj.fastorm.annotation.enums.OperationType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Column
 *
 * @author huangqi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface Default {
    /**
     * 默认值类型
     */
    DefaultValueType valueType() default DefaultValueType.STRING;

    /**
     * 额外值，当默认值类型为Object时有效
     */
    String extra() default "";

    /**
     * 有效操作类型
     */
    OperationType operationType() default OperationType.INSERT;
}
