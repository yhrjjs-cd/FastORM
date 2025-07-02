package com.cdyhrj.fast.orm.condition.expression;

import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.condition.expression.helper.ParamNameGenerator;
import org.springframework.lang.NonNull;

public abstract class AbstractUnaryExpression<T> implements Expression {
    protected String alias;
    protected String field;
    protected T value;
    /**
     * 参数名，用于SQL生成中占位，一个SQL中哟唯一性
     */
    protected String paramName = ParamNameGenerator.getNewParamName();

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        paramMap.add(paramName, value);
    }

    public AbstractUnaryExpression(String alias, String field, T value) {
        this.alias = alias;
        this.field = field;
        this.value = value;
    }
}
