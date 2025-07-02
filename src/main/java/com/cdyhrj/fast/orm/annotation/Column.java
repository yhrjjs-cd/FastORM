package com.cdyhrj.fast.orm.annotation;

import com.cdyhrj.fast.orm.util.NameUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段标记
 *
 * @author huangqi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface Column {
    String value() default "";

    /**
     * 是否把字段的命名方式从驼峰式大小写(camelCase)变成蛇底式小写(snake_case)
     *
     * @return true: 蛇底式小写，false: 驼峰式大小写
     */
    boolean hump() default true;

    /**
     * @return 字段长度，当为字符串时有效
     */
    int length() default 255;

    /**
     * @return 数字总长度
     */
    int precision() default 20;

    /**
     * @return 小数位数
     */
    int scale() default 10;

    /**
     * @return 只读字段，不插入
     */
    boolean readonly() default false;


    class Utils {
        /**
         * 获取Column对应的字段名
         *
         * @param column       列
         * @param propertyName 属性名
         * @return 对应的字段名
         */
        public static String getEntityFieldName(Column column, String propertyName) {
            if (column.hump()) {
                return NameUtils.toUnderScoreCase(propertyName);
            } else {
                return column.value();
            }
        }
    }
}
