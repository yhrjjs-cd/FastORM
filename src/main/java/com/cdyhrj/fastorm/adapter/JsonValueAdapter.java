package com.cdyhrj.fastorm.adapter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Objects;

/**
 * JSON
 *
 * @author huangqi
 */

public class JsonValueAdapter implements ValueAdapter<JSONObject> {
    @Override
    @SneakyThrows
    public JSONObject readFromRs(ResultSet rs, int index, Class<JSONObject> fieldType) {
        return JSON.parseObject(rs.getString(index));
    }

    @Override
    public Object transValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return JSON.toJSONString(value);
    }
}
