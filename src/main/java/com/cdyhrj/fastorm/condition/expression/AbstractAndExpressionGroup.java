package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.condition.Exps;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAndExpressionGroup<T extends Entity, H extends ConditionHost<T>> implements SqlSegment, Expression {
    protected final ToSqlContext<T, H> context;

    protected List<Expression> expressionList = new ArrayList<>();

    protected void addExpression(final Expression expression) {
        this.expressionList.add(expression);
    }

    public AbstractAndExpressionGroup(@NonNull ToSqlContext<T, H> context) {
        this.context = context;
    }

    public AbstractAndExpressionGroup<T, H> andEq(PropFn<T, ?> propFn, Object value) {
        return andEq(null, propFn, value);
    }

    public AbstractAndExpressionGroup<T, H> andEq(String alias, PropFn<T, ?> propFn, Object value) {
        this.expressionList.add(Exps.eq(context, alias, propFn, value));

        return this;
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        this.expressionList.forEach(expression -> expression.writeToParamMap(paramMap));
    }

    @Override
    public String toSql() {
        return "(" + this.expressionList.stream().map(SqlSegment::toSql)
                .collect(Collectors.joining(" AND ")) + ")";
    }
}
