package com.cdyhrj.fast.orm.adapter.impl;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * LocalDateTime ValueAdapter
 *
 * @author huangqi
 */

public class LocalDateTimeValueAdapter implements ValueAdapter<LocalDateTime> {

    @SneakyThrows
    @Override
    public LocalDateTime readFromRs(ResultSet rs, int index, Class<LocalDateTime> fieldType) {
        Timestamp t = rs.getTimestamp(index);
        if (Objects.isNull(t)) {
            return null;
        } else {
            return t.toLocalDateTime();
        }
    }

    @Override
    public Timestamp transValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        ZonedDateTime zonedDateTime = ((LocalDateTime) value).atZone(ZoneId.systemDefault());
        return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
    }
}
