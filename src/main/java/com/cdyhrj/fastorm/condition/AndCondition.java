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

public class AndCondition<T extends Entity, H extends ConditionHost<T>> implements SqlSegment, NoAliasSqlSegment, Expression {
    private final ToSqlContext<T, H> context;
    private final List<Expression> expressionList = new ArrayList<>();
    private OrCondition<T, H> belongTo;

    public AndCondition(@NonNull ToSqlContext<T, H> context) {
        this.context = context;
    }

    public AndCondition(@NonNull ToSqlContext<T, H> context, OrCondition<T, H> belongTo) {
        this.context = context;
        this.belongTo = belongTo;
    }

    public OrCondition<T, H> andOrGroup() {
        OrCondition<T, H> group = new OrCondition<>(context, this);
        addExpression(group);

        return group;
    }

    private void addExpression(final Expression expression) {
        this.expressionList.add(expression);
    }

    public AndCondition<T, H> andEq(PropFn<T, ?> propFn, Object value) {
        return andEq(null, propFn, value);
    }

    public AndCondition<T, H> andEq(String alias, PropFn<T, ?> propFn, Object value) {
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
                .collect(Collectors.joining(" AND ")) + ")";
    }


    @Override
    public String toNoAliasSql() {
        Assert.isTrue(!this.expressionList.isEmpty(), "expression list is empty");

        return "(" + this.expressionList.stream().map(NoAliasSqlSegment::toNoAliasSql)
                .collect(Collectors.joining(" AND ")) + ")";
    }

    /**
     * 是否为空，没有包含任何条件
     *
     * @return 如果为空, 返回<code>true</code>,否则<code>false</code>
     */
    public boolean isEmpty() {
        return this.expressionList.isEmpty();
    }

    public H ret() {
        // let subclass implement
        return null;
    }

    public OrCondition<T, H> end() {
        return this.belongTo;
    }
}
