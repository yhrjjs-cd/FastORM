package com.cdyhrj.fast.orm.adapter.impl;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;

/**
 * String ValueAdapter
 *
 * @author huangqi
 */

public class StringValueAdapter implements ValueAdapter<String> {

    @SneakyThrows
    @Override
    public String readFromRs(ResultSet rs, int index, Class<String> fieldType) {
        return rs.getString(index);
    }

    @Override
    public String transValue(Object value) {
        return (String) value;
    }
}
