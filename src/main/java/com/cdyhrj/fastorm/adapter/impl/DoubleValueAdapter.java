package com.cdyhrj.fastorm.adapter.impl;

import com.cdyhrj.fastorm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;

/**
 * Integer ValueAdapter
 *
 * @author huangqi
 */

public class DoubleValueAdapter implements ValueAdapter<Double> {

    @SneakyThrows
    @Override
    public Double readFromRs(ResultSet rs, int index, Class<Double> fieldType) {
        double value = rs.getDouble(index);

        return rs.wasNull() ? null : value;
    }

    @Override
    public Double transValue(Object value) {
        return (Double) value;
    }
}
