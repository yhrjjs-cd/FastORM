package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.expression.AbstractAndExpressionGroup;
import com.cdyhrj.fastorm.condition.expression.OrExpressionGroup;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

public abstract class AndCondition<T extends Entity, H extends ConditionHost<T>> extends AbstractAndExpressionGroup<T, H> {
    public AndCondition(@NonNull ToSqlContext<T, H> context) {
        super(context);
    }

    public OrExpressionGroup<T, H> andOrGroup() {
        OrExpressionGroup<T, H> group = new OrExpressionGroup<>(context, this);
        addExpression(group);

        return group;
    }

    @Override
    public AndCondition<T, H> andEq(PropFn<T, ?> propFn, Object value) {
        super.andEq(propFn, value);

        return this;
    }

    @Override
    public AndCondition<T, H> andEq(String alias, PropFn<T, ?> propFn, Object value) {
        super.andEq(alias, propFn, value);

        return this;
    }

    public abstract H ret();
}
