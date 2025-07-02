package com.cdyhrj.fast.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表名
 *
 * @author huangqi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Table {
    /**
     * @return 表名
     */
    String name();

    /**
     * @return schema
     */
    String schema() default "";

    /**
     * @return 备注信息
     */
    String comment() default "";
}
