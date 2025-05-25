/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fastorm.meta;

import com.cdyhrj.fastorm.entity.Entity;
import lombok.Data;

/**
 * 字段信息
 *
 * @author 黄奇
 */
@Data
public class FieldInfo {
    public static FieldInfo of(String name, Class<?> type, Class<? extends Entity> entityClass) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setName(name);
        fieldInfo.setType(type);
        fieldInfo.setEntityClass(entityClass);

        return fieldInfo;
    }

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段类型
     */
    private Class<?> type;

    /**
     * 对应表对象类
     */
    private Class<?> entityClass;
}
