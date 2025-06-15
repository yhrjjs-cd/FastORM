package com.cdyhrj.fastorm.adapter.impl;

import com.cdyhrj.fastorm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;

/**
 * Integer ValueAdapter
 *
 * @author huangqi
 */

public class FloatValueAdapter implements ValueAdapter<Float> {
    @SneakyThrows
    @Override
    public Float readFromRs(ResultSet rs, int index, Class<Float> fieldType) {
        float value = rs.getFloat(index);

        return rs.wasNull() ? null : value;
    }

    @Override
    public Float transValue(Object value) {
        return (Float) value;
    }
}
