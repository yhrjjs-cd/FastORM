package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

public class OrExpressionGroup<T extends Entity, H extends ConditionHost<T>> extends AbstractOrExpressionGroup<T,H> {
    private final AndCondition<T, H> andCondition;

    public OrExpressionGroup(@NonNull ToSqlContext<T, H> context, AndCondition<T, H> andCondition) {
        super(context);

        this.andCondition = andCondition;
    }

    public <E extends Entity> OrExpressionGroup<T, H> orEq(PropFn<E, ?> propFn, Object value) {
        super.orEq(propFn, value);

        return this;
    }

    public <E extends Entity> OrExpressionGroup<T, H> orEq(String alias, PropFn<E, ?> propFn, Object value) {
        super.orEq(alias, propFn, value);

        return this;
    }

    public AndCondition<T, H> ret() {
        return andCondition;
    }
}
