package com.cdyhrj.fastorm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 视图
 *
 * @author huangqi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface View {
    String value() default "";

    /**
     * 视图前缀
     */
    String prefix() default "";

    /**
     * 视图后缀
     */
    String suffix() default "";
}
