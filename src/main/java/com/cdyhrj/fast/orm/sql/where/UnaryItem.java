package com.cdyhrj.fast.orm.sql.where;

import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.condition.enums.Operator;
import com.cdyhrj.fast.orm.condition.expression.helper.ParamNameGenerator;
import lombok.Data;

@Data
public class UnaryItem implements Item {
    public static UnaryItem of(String key, Operator operator, Object value) {
        return new UnaryItem(key, operator, value);
    }

    private String key;
    private Operator operator;
    private Object value;
    private String paramName = ParamNameGenerator.getNewParamName();

    public UnaryItem(String key, Operator operator, Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public String toSql() {
        return "%s %s :%s".formatted(key, operator, paramName);
    }

    @Override
    public void writeToParamMap(ParamMap paramMap) {
        paramMap.add(paramName, value);
    }
}
