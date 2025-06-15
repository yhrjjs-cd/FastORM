package com.cdyhrj.fastorm.adapter.impl;

import com.cdyhrj.fastorm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;

/**
 * Long ValueAdapter
 *
 * @author huangqi
 */

public class LongValueAdapter implements ValueAdapter<Long> {

    @SneakyThrows
    @Override
    public Long readFromRs(ResultSet rs, int index, Class<Long> fieldType) {
        long value = rs.getLong(index);

        return rs.wasNull() ? null : value;
    }

    @Override
    public Long transValue(Object value) {
        return (Long) value;
    }
}
