package com.cdyhrj.fastorm.adapter.impl;

import com.cdyhrj.fastorm.adapter.ValueAdapter;
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
        return rs.getDate(index).toLocalDate();
    }

    @Override
    public Date transValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return Date.valueOf((LocalDate) value);
    }
}
