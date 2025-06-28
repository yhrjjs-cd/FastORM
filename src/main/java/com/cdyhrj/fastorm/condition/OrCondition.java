package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.NoAliasSqlSegment;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.expression.Expression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrCondition<T extends Entity, H extends ConditionHost<T>> implements SqlSegment, NoAliasSqlSegment, Expression {
    private final ToSqlContext<T, H> context;
    private AndCondition<T, H> belongTo;

    private final List<Expression> expressionList = new ArrayList<>();

    public OrCondition(@NonNull ToSqlContext<T, H> context) {
        this.context = context;
    }

    public OrCondition(@NonNull ToSqlContext<T, H> context, AndCondition<T, H> belongTo) {
        this.context = context;
        this.belongTo = belongTo;
    }

    public AndCondition<T, H> orAndGroup() {
        AndCondition<T, H> group = new AndCondition<>(context, this);
        addExpression(group);

        return group;
    }

    private void addExpression(final Expression expression) {
        this.expressionList.add(expression);
    }

    public OrCondition<T, H> orEq(PropFn<T, ?> propFn, Object value) {
        return orEq(null, propFn, value);
    }

    public OrCondition<T, H> orEq(String alias, PropFn<T, ?> propFn, Object value) {
        addExpression(Exps.eq(context, alias, propFn, value));

        return this;
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        this.expressionList.forEach(expression -> expression.writeToParamMap(paramMap));
    }

    @Override
    public String toSql() {
        Assert.isTrue(!this.expressionList.isEmpty(), "expression list is empty");

        return "(" + this.expressionList.stream().map(SqlSegment::toSql)
                .collect(Collectors.joining(" OR ")) + ")";
    }


    @Override
    public String toNoAliasSql() {
        Assert.isTrue(!this.expressionList.isEmpty(), "expression list is empty");

        return "(" + this.expressionList.stream().map(NoAliasSqlSegment::toNoAliasSql)
                .collect(Collectors.joining(" OR ")) + ")";
    }

    /**
     * 是否为空，没有包含任何条件
     *
     * @return 如果为空, 返回<code>true</code>,否则<code>false</code>
     */
    public boolean isEmpty() {
        return this.expressionList.isEmpty();
    }

    public AndCondition<T, H> end() {
        return this.belongTo;
    }
}
