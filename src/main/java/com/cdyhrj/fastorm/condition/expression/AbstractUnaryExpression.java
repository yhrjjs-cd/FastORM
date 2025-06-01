package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.condition.expression.helper.ParamNameGenerator;
import com.cdyhrj.fastorm.parameter.ParamMap;
import org.springframework.lang.NonNull;

public abstract class AbstractUnaryExpression implements Expression {
    protected String alias;
    protected String field;
    protected Object value;
    /**
     * 参数名，用于SQL生成中占位，一个SQL中哟唯一性
     */
    protected String paramName = ParamNameGenerator.getNewParamName();

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        paramMap.add(paramName, value);
    }

    public AbstractUnaryExpression(String alias, String field, Object value) {
        this.alias = alias;
        this.field = field;
        this.value = value;
    }
}
