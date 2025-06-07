package com.cdyhrj.fastorm.parameter;

import com.cdyhrj.fastorm.condition.Condition;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 参数Map
 *
 * @author huangqi
 */
@Slf4j
@ToString
public class ParamMap {
    public static ParamMap of() {
        return new ParamMap();
    }

    /**
     * 根据条件生成参数Map
     *
     * @param condition 条件
     * @return 参数Map
     */
    public static ParamMap from(Condition<?> condition) {
        ParamMap paramMap = ParamMap.of();

        condition.writeToParamMap(paramMap);

        return paramMap;
    }


    @Getter
    private final Map<String, Object> params = new HashMap<>();

    /**
     * 添加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    public void add(String name, Object value) {
        if (Objects.isNull(value)) {
            this.params.put(name, null);
        } else {
            if (value instanceof Enum) {
                // 不做处理，查询出错。 :)黄奇
                this.params.put(name, String.valueOf(value));
            } else {
                this.params.put(name, value);
            }
        }
    }
}
