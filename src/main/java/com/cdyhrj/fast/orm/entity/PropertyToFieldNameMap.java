package com.cdyhrj.fast.orm.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体对象 属性->字段名 映射
 *
 * @author huangqi
 */
public class PropertyToFieldNameMap {
    public static PropertyToFieldNameMap of() {
        return new PropertyToFieldNameMap();
    }

    private final Map<String, String> map = new HashMap<>();

    /**
     * 添加映射
     *
     * @param propertyName 属性名
     * @param fieldName    字段名
     */
    public void add(String propertyName, String fieldName) {
        this.map.put(propertyName, fieldName);
    }

    /**
     * 获取字段名，如果没有，返回属性名
     *
     * @param propertyName 属性名
     * @return 字段名
     */
    public String getFieldName(String propertyName) {
        return this.map.getOrDefault(propertyName, propertyName);
    }
}
