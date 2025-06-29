package com.cdyhrj.fastorm.sql.where;


import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.condition.expression.helper.ParamNameGenerator;
import lombok.Data;

@Data
public class BinaryItem implements Item {
    public static BinaryItem of(String key, Operator operator, Object value1, Object value2) {
        return new BinaryItem(key, operator, value1, value2);
    }

    private String key;
    private Operator operator;
    private Object value1;
    private Object value2;
    private String paramName1 = ParamNameGenerator.getNewParamName();
    private String paramName2 = ParamNameGenerator.getNewParamName();

    public BinaryItem(String key, Operator operator, Object value1, Object value2) {
        this.key = key;
        this.operator = operator;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public String toSql() {
        return "%s %s :%s AND :%s".formatted(key, operator, paramName1, paramName2);
    }

    @Override
    public void writeToParamMap(ParamMap paramMap) {
        paramMap.add(paramName1, value1);
        paramMap.add(paramName2, value2);
    }
}
