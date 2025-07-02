package com.cdyhrj.fast.orm.condition.expression;


import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.condition.expression.helper.ParamNameGenerator;
import org.springframework.lang.NonNull;

/**
 * 2参抽象条件
 *
 * @author huangqi
 */

public abstract class AbstractBinaryExpression<T> implements Expression {
    /**
     * 表别名
     */
    protected String alias;

    /**
     * 对应的字段
     */
    protected String field;
    /**
     * 参数1
     */
    protected T value1;

    /**
     * 参数2
     */
    protected T value2;

    /**
     * 参数名1，用于SQL生成中占位，一个SQL中哟唯一性
     */
    protected String paramName1 = ParamNameGenerator.getNewParamName();

    /**
     * 参数名2，用于SQL生成中占位，一个SQL中哟唯一性
     */
    protected String paramName2 = ParamNameGenerator.getNewParamName();

    protected AbstractBinaryExpression(String alias, String field, T value1, T value2) {
        this.alias = alias;
        this.field = field;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        paramMap.add(paramName1, value1);
        paramMap.add(paramName2, value2);
    }
}
