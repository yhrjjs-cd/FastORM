package com.cdyhrj.fastorm.adapter.impl;

import com.cdyhrj.fastorm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Timestamp ValueAdapter
 *
 * @author huangqi
 */

public class TimestampValueAdapter implements ValueAdapter<Timestamp> {

    @SneakyThrows
    @Override
    public Timestamp readFromRs(ResultSet rs, int index, Class<Timestamp> fieldType) {
        return rs.getTimestamp(index);
    }

    @Override
    public Timestamp transValue(Object value) {
        return (Timestamp) value;
    }
}
