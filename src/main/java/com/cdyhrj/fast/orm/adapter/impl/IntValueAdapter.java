package com.cdyhrj.fast.orm.adapter.impl;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;

/**
 * Integer ValueAdapter
 *
 * @author huangqi
 */

public class IntValueAdapter implements ValueAdapter<Integer> {

    @SneakyThrows
    @Override
    public Integer readFromRs(ResultSet rs, int index, Class<Integer> fieldType) {
        int value = rs.getInt(index);

        return rs.wasNull() ? null : value;
    }

    @Override
    public Integer transValue(Object value) {
        return (Integer) value;
    }
}
