package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.NoAliasSqlSegment;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.expression.Expression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrConditionX<T extends Entity, H extends ConditionHost<T>> implements SqlSegment, NoAliasSqlSegment, Expression {
    private final ToSqlContext<T, H> context;
    private AndConditionX<T, H> belongTo;

    private final List<Expression> expressionList = new ArrayList<>();

    public OrConditionX(@NonNull ToSqlContext<T, H> context) {
        this.context = context;
    }

    public OrConditionX(@NonNull ToSqlContext<T, H> context, AndConditionX<T, H> belongTo) {
        this.context = context;
        this.belongTo = belongTo;
    }

    public AndConditionX<T, H> orAndGroup() {
        AndConditionX<T, H> group = new AndConditionX<>(context, this);
        addExpression(group);

        return group;
    }

    private void addExpression(final Expression expression) {
        this.expressionList.add(expression);
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

    public AndConditionX<T, H> end() {
        return this.belongTo;
    }

    public OrConditionX<T, H> orEq(PropFn<T, ?> propFn, Object value) {
        return orEq(null, propFn, value);
    }

    public OrConditionX<T, H> orEq(String alias, PropFn<T, ?> propFn, Object value) {
        addExpression(Exps.eq(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> OrConditionX<T, H> orLt(PropFn<T, N> propFn, N value) {
        return orEq(null, propFn, value);
    }

    public <N extends Number> OrConditionX<T, H> orLt(String alias, PropFn<? extends Entity, N> propFn, N value) {
        addExpression(Exps.lt(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> OrConditionX<T, H> orLte(PropFn<T, N> propFn, N value) {
        return orLte(null, propFn, value);
    }

    public <N extends Number> OrConditionX<T, H> orLte(String alias, PropFn<? extends Entity, N> propFn, N value) {
        addExpression(Exps.lte(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> OrConditionX<T, H> orGt(PropFn<T, N> propFn, N value) {
        return orGt(null, propFn, value);
    }

    public <N extends Number> OrConditionX<T, H> orGt(String alias, PropFn<? extends Entity, N> propFn, N value) {
        addExpression(Exps.gt(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> OrConditionX<T, H> orGte(PropFn<T, N> propFn, N value) {
        return orGte(null, propFn, value);
    }

    public <N extends Number> OrConditionX<T, H> orGte(String alias, PropFn<? extends Entity, N> propFn, N value) {
        addExpression(Exps.gte(context, alias, propFn, value));

        return this;
    }

    public OrConditionX<T, H> orIsNull(PropFn<T, ?> propFn) {
        return orIsNull(null, propFn);
    }

    public OrConditionX<T, H> orIsNull(String alias, PropFn<? extends Entity, ?> propFn) {
        addExpression(Exps.isNull(context, alias, propFn));

        return this;
    }

    public OrConditionX<T, H> orLike(PropFn<T, String> propFn, String value) {
        return orLike(null, propFn, value);
    }

    public OrConditionX<T, H> orLike(String alias, PropFn<? extends Entity, String> propFn, String value) {
        addExpression(Exps.like(context, alias, propFn, value));

        return this;
    }

    public OrConditionX<T, H> orBetween(PropFn<T, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<T, H> orBetween(String alias, PropFn<? extends Entity, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public OrConditionX<T, H> orBetween(PropFn<T, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<T, H> orBetween(String alias, PropFn<? extends Entity, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public OrConditionX<T, H> orBetween(PropFn<T, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<T, H> orBetween(String alias, PropFn<? extends Entity, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public OrConditionX<T, H> orBetween(PropFn<T, Year> propFn, Year value1, Year value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<T, H> orBetween(String alias, PropFn<? extends Entity, Year> propFn, Year value1, Year value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public OrConditionX<T, H> orBetween(PropFn<T, Number> propFn, Number value1, Number value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<T, H> orBetween(String alias, PropFn<? extends Entity, Number> propFn, Number value1, Number value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }
}
