package com.cdyhrj.fast.orm.adapter.impl;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Objects;

/**
 * LocalDateTime ValueAdapter
 *
 * @author huangqi
 */

public class LocalDateValueAdapter implements ValueAdapter<LocalDate> {

    @SneakyThrows
    @Override
    public LocalDate readFromRs(ResultSet rs, int index, Class<LocalDate> fieldType) {
        Date d = rs.getDate(index);

        if (Objects.isNull(d)) {
            return null;
        } else {
            return d.toLocalDate();
        }

    }

    @Override
    public Date transValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return Date.valueOf((LocalDate) value);
    }
}
