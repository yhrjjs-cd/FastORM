package com.cdyhrj.fastorm.adapter;

import java.sql.ResultSet;

/**
 * 封装字段取值逻辑
 *
 * @author huangqi
 */

public interface ValueAdapter<T> {
    /**
     * 从ResultSet中读取
     *
     * @param rs        rs
     * @param index     index
     * @param fieldType Field type
     * @return 对象
     */
    T readFromRs(ResultSet rs, int index, Class<T> fieldType);

    /**
     * 值转换
     *
     * @param value 值
     * @return 序列化字符串
     */
    Object transValue(Object value);
}
