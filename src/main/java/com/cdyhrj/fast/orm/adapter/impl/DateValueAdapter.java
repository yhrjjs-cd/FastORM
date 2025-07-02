package com.cdyhrj.fast.orm.adapter.impl;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Date ValueAdapter
 *
 * @author huangqi
 */

public class DateValueAdapter implements ValueAdapter<Date> {

    @SneakyThrows
    @Override
    public Date readFromRs(ResultSet rs, int index, Class<Date> fieldType) {
        // 也用getTimestamp返回，否则时间部分为0
        return rs.getTimestamp(index);
    }

    @Override
    public Date transValue(Object value) {
        return (Date) value;
    }
}
