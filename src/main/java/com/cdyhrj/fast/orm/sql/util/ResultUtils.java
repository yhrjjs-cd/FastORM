package com.cdyhrj.fast.orm.sql.util;

import com.cdyhrj.fast.orm.util.NameUtils;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResultUtils {
    /**
     * Key由UnderScoreCase转换为Camel
     *
     * @param values 数据
     * @return Camel Key Map
     */
    public static List<Map<String, Object>> valuesToCamelKey(List<Map<String, Object>> values) {
        if (Objects.nonNull(values)) {
            List<Map<String, Object>> result = new ArrayList<>();

            if (!values.isEmpty()) {
                Map<String, String> keyMap = values.get(0)
                        .keySet()
                        .stream()
                        .collect(Collectors.toMap(Function.identity(),
                                NameUtils::toCamel, (o1, o2) -> o1));
                for (Map<String, Object> item : values) {
                    Map<String, Object> itemMap = Maps.newHashMap();

                    for (Map.Entry<String, Object> entry : item.entrySet()) {
                        itemMap.put(keyMap.get(entry.getKey()), entry.getValue());
                    }

                    result.add(itemMap);
                }
            }

            return result;
        }

        return Collections.emptyList();
    }

    /**
     * Key由UnderScoreCase转换为Camel
     *
     * @param value 数据
     * @return Camel Key Map
     */
    public static Map<String, Object> valuesToCamelKey(Map<String, Object> value) {
        if (Objects.nonNull(value)) {
            Map<String, Object> result = new HashMap<>();

            Map<String, String> keyMap = value
                    .keySet()
                    .stream()
                    .collect(Collectors.toMap(Function.identity(),
                            NameUtils::toCamel, (o1, o2) -> o1));

            Map<String, Object> itemMap = Maps.newHashMap();

            for (Map.Entry<String, Object> entry : value.entrySet()) {
                itemMap.put(keyMap.get(entry.getKey()), entry.getValue());
            }

            return itemMap;
        }

        return Collections.emptyMap();
    }
}
