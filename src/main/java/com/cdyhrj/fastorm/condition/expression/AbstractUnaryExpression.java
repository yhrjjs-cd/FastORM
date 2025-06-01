package com.cdyhrj.fastorm.condition.expression;

public abstract class AbstractUnaryExpression implements Expression {
    protected String alias;
    protected String field;
    protected Object value;

    public AbstractUnaryExpression(String alias, String field, Object value) {
        this.alias = alias;
        this.field = field;
        this.value = value;
    }
}
