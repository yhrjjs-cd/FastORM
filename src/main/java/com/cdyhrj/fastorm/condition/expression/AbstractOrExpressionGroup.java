package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.Exps;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractOrExpressionGroup<T extends Entity> implements SqlSegment, Expression {
    private final ToSqlContext<T> context;

    protected List<Expression> expressionList = new ArrayList<>();

    public AbstractOrExpressionGroup(@NonNull ToSqlContext<T> context) {
        this.context = context;
    }

    public <E extends Entity> AbstractOrExpressionGroup<T> orEq(PropFn<E, ?> propFn, Object value) {
        return orEq(null, propFn, value);
    }

    public <E extends Entity> AbstractOrExpressionGroup<T> orEq(String alias, PropFn<E, ?> propFn, Object value) {
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
                .collect(Collectors.joining(" OR ")) + ")";
    }
}
