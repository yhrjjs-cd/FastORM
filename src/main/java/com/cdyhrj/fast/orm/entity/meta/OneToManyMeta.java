package com.cdyhrj.fast.orm.entity.meta;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一对多元数据
 *
 * @author huangqi
 */
@Data
@Accessors(fluent = true)
public class OneToManyMeta {
    /**
     * 属性名
     */
    private String propertyName;

    /**
     * 字段名
     */
    private String fieldName;
}
