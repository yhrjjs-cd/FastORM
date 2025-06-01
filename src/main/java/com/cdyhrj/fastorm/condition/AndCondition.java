package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.condition.expression.Expression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.meta.SqlNode;
import com.cdyhrj.fastorm.parameter.ParamMap;
import com.cdyhrj.fastorm.queryable.Queryable;
import com.cdyhrj.fastorm.queryable.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AndCondition<T extends Entity> implements Condition<T> {
    private final Context<T> context;
    private final Queryable<T> queryable;

    protected List<Expression> expessionList = new ArrayList<>();

    public AndCondition(Context<T> context, Queryable<T> queryable) {
        this.context = context;
        this.queryable = queryable;
    }

    public Queryable<T> ret() {
        return queryable;
    }

    public <E extends Entity> AndCondition<T> andEq(PropFn<E, ?> propFn, Object value) {
        return andEq(null, propFn, value);
    }

    public <E extends Entity> AndCondition<T> andEq(String alias, PropFn<E, ?> propFn, Object value) {
        this.expessionList.add(Exps.eq(context, alias, propFn, value));

        return this;
    }

    @Override
    public void writeToParamMap(ParamMap paramMap) {
        this.expessionList.forEach(expression -> expression.writeToParamMap(paramMap));
    }

    @Override
    public String toSql() {
        return this.expessionList.stream().map(SqlNode::toSql)
                .collect(Collectors.joining(" AND "));
    }
}
