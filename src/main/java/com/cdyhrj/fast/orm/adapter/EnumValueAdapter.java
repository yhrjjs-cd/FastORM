package com.cdyhrj.fast.orm.adapter;

import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Objects;

/**
 * EnumValue
 *
 * @author huangqi
 */

public class EnumValueAdapter implements ValueAdapter<Enum> {

    @SuppressWarnings("unchecked")
    @Override
    @SneakyThrows
    public Enum readFromRs(ResultSet rs, int index, Class<Enum> fieldType) {
        String value = rs.getString(index);
        if (Objects.isNull(value)) {
            return null;
        }

        return Enum.valueOf(fieldType, value);
    }

    @Override
    public String transValue(Object value) {
        return Objects.isNull(value) ? null : value.toString();
    }
}
