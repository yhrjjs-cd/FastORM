package com.cdyhrj.fast.orm.adapter.impl;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;

/**
 * Boolean ValueAdapter
 *
 * @author huangqi
 */

public class BooleanValueAdapter implements ValueAdapter<Boolean> {

    @SneakyThrows
    @Override
    public Boolean readFromRs(ResultSet rs, int index, Class<Boolean> fieldType) {
        return rs.getBoolean(index);
    }

    @Override
    public Boolean transValue(Object value) {
        return (Boolean) value;
    }
}
