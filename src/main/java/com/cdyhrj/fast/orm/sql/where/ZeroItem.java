package com.cdyhrj.fast.orm.sql.where;


import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.condition.enums.Operator;
import lombok.Data;

@Data
public class ZeroItem implements Item {
    public static ZeroItem of(String key, Operator operator) {
        return new ZeroItem(key, operator);
    }

    private String key;
    private Operator operator;

    public ZeroItem(String key, Operator operator) {
        this.key = key;
        this.operator = operator;
    }

    @Override
    public String toSql() {
        return "%s %s".formatted(key, operator);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void writeToParamMap(ParamMap paramMap) {
        // No need
    }
}
