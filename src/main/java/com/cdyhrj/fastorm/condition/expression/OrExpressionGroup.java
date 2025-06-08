package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

public class OrExpressionGroup<T extends Entity> extends AbstractOrExpressionGroup<T> {
    private final AndCondition<T> andCondition;

    public OrExpressionGroup(@NonNull ToSqlContext<T> context, AndCondition<T> andCondition) {
        super(context);

        this.andCondition = andCondition;
    }

    public <E extends Entity> OrExpressionGroup<T> orEq(PropFn<E, ?> propFn, Object value) {
        super.orEq(propFn, value);

        return this;
    }

    public <E extends Entity> OrExpressionGroup<T> orEq(String alias, PropFn<E, ?> propFn, Object value) {
        super.orEq(alias, propFn, value);

        return this;
    }

    public AndCondition<T> ret() {
        return andCondition;
    }
}
