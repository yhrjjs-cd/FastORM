package com.cdyhrj.fastorm.adapter;

import com.alibaba.fastjson2.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.util.Objects;

/**
 * Class
 *
 * @author huangqi
 */
@Slf4j
public class ObjectValueAdapter implements ValueAdapter<Object> {
    @Override
    @SneakyThrows
    public Object readFromRs(ResultSet rs, int index, Class<Object> fieldType) {
        String value = rs.getString(index);
        if (Objects.isNull(value)) {
            return null;
        } else {
            return JSON.parseObject(value, fieldType);
        }
    }

    @Override
    public Object transValue(Object entity) {
        return Objects.isNull(entity) ? null : JSON.toJSONString(entity);
    }
}

