package com.cdyhrj.fastorm.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字段名称
 *
 * @author huangqi
 */
@Data
@Accessors(fluent = true)
public class FieldNameSpec {
    public static FieldNameSpec of() {
        return new FieldNameSpec();
    }

    /**
     * 数据库表字段名称
     */
    private String fieldName;

    /**
     * 对象属性名称
     */
    private String propertyName;

    /**
     * 字段类型
     */
    private FieldNameType type = FieldNameType.Normal;

    /**
     * 数据类型
     */
    private Class<?> dataType;

    /**
     * 只读
     */
    private boolean readonly;
}
