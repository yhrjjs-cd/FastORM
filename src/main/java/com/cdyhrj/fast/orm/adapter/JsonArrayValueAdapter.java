package com.cdyhrj.fast.orm.adapter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Objects;

/**
 * JSONArray
 *
 * @author <a href="huangqi@cdyhrj.com">黄奇</a>
 */
public class JsonArrayValueAdapter implements ValueAdapter<JSONArray> {
    @Override
    @SneakyThrows
    public JSONArray readFromRs(ResultSet rs, int index, Class<JSONArray> fieldType) {
        return JSON.parseArray(rs.getString(index));
    }

    @Override
    public Object transValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return JSON.toJSONString(value);
    }
}
