package com.cdyhrj.fastorm.sql.where;

import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.condition.expression.helper.ParamNameGenerator;
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
