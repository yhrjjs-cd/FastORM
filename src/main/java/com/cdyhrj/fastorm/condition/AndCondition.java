package com.cdyhrj.fastorm.condition;

public class AndCondition implements Condition {
    public static AndCondition of() {
        return new AndCondition();
    }

    @Override
    public String toSqlString() {
        return "WHERE";
    }
}
