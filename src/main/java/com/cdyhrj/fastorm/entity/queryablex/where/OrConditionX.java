package com.cdyhrj.fastorm.entity.queryablex.where;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.NoAliasSqlSegment;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.Exps;
import com.cdyhrj.fastorm.condition.expression.Expression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.queryablex.EntityByClassQueryableX;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrConditionX<E extends Entity, H extends EntityByClassQueryableX<E>> implements SqlSegment, NoAliasSqlSegment, Expression {
    private final ToSqlContext<E, H> context;
    private AndConditionX<E, H> belongTo;

    private final List<Expression> expressionList = new ArrayList<>();

    public OrConditionX(@NonNull ToSqlContext<E, H> context) {
        this.context = context;
    }

    public OrConditionX(@NonNull ToSqlContext<E, H> context, AndConditionX<E, H> belongTo) {
        this.context = context;
        this.belongTo = belongTo;
    }

    public AndConditionX<E, H> orAndGroup() {
        AndConditionX<E, H> group = new AndConditionX<>(context, this);
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

    public AndConditionX<E, H> end() {
        return this.belongTo;
    }

    public OrConditionX<E, H> orEq(PropFn<E, ?> propFn, Object value) {
        return orEq(null, propFn, value);
    }

    public <T extends Entity> OrConditionX<E, H> orEq(String alias, PropFn<T, ?> propFn, Object value) {
        addExpression(Exps.eq(context, alias, propFn, value));

        return this;
    }

    public <T extends Entity> OrConditionX<E, H> orEqX(PropFn<T, ?> propFn, Object value) {
        return orEq(null, propFn, value);
    }

    public <N extends Number> OrConditionX<E, H> orLt(PropFn<E, N> propFn, N value) {
        return orEq(null, propFn, value);
    }

    public <N extends Number, T extends Entity> OrConditionX<E, H> orLt(String alias, PropFn<T, N> propFn, N value) {
        addExpression(Exps.lt(context, alias, propFn, value));

        return this;
    }

    public <N extends Number, T extends Entity> OrConditionX<E, H> orLtX(PropFn<T, N> propFn, N value) {
        return orEq(null, propFn, value);
    }

    public <N extends Number> OrConditionX<E, H> orLte(PropFn<E, N> propFn, N value) {
        return orLte(null, propFn, value);
    }

    public <N extends Number, T extends Entity> OrConditionX<E, H> orLte(String alias, PropFn<T, N> propFn, N value) {
        addExpression(Exps.lte(context, alias, propFn, value));

        return this;
    }

    public <N extends Number, T extends Entity> OrConditionX<E, H> orLteX(PropFn<T, N> propFn, N value) {
        return orLte(null, propFn, value);
    }

    public <N extends Number> OrConditionX<E, H> orGt(PropFn<E, N> propFn, N value) {
        return orGt(null, propFn, value);
    }

    public <N extends Number, T extends Entity> OrConditionX<E, H> orGt(String alias, PropFn<T, N> propFn, N value) {
        addExpression(Exps.gt(context, alias, propFn, value));

        return this;
    }

    public <N extends Number, T extends Entity> OrConditionX<E, H> orGtX(PropFn<T, N> propFn, N value) {
        return orGt(null, propFn, value);
    }

    public <N extends Number> OrConditionX<E, H> orGte(PropFn<E, N> propFn, N value) {
        return orGte(null, propFn, value);
    }

    public <N extends Number, T extends Entity> OrConditionX<E, H> orGte(String alias, PropFn<T, N> propFn, N value) {
        addExpression(Exps.gte(context, alias, propFn, value));

        return this;
    }

    public <N extends Number, T extends Entity> OrConditionX<E, H> orGteX(PropFn<T, N> propFn, N value) {
        return orGte(null, propFn, value);
    }

    public OrConditionX<E, H> orIsNull(PropFn<E, ?> propFn) {
        return orIsNull(null, propFn);
    }

    public <T extends Entity> OrConditionX<E, H> orIsNull(String alias, PropFn<T, ?> propFn) {
        addExpression(Exps.isNull(context, alias, propFn));

        return this;
    }

    public <T extends Entity> OrConditionX<E, H> orIsNullX(PropFn<T, ?> propFn) {
        return orIsNull(null, propFn);
    }

    public OrConditionX<E, H> orLike(PropFn<E, String> propFn, String value) {
        return orLike(null, propFn, value);
    }

    public <T extends Entity> OrConditionX<E, H> orLike(String alias, PropFn<T, String> propFn, String value) {
        addExpression(Exps.like(context, alias, propFn, value));

        return this;
    }

    public <T extends Entity> OrConditionX<E, H> orLikeX(PropFn<T, String> propFn, String value) {
        return orLike(null, propFn, value);
    }

    public OrConditionX<E, H> orBetween(PropFn<E, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> OrConditionX<E, H> orBetween(String alias, PropFn<T, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public <T extends Entity> OrConditionX<E, H> orBetweenX(PropFn<T, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<E, H> orBetween(PropFn<E, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> OrConditionX<E, H> orBetween(String alias, PropFn<T, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public <T extends Entity> OrConditionX<E, H> orBetweenX(PropFn<T, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<E, H> orBetween(PropFn<E, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> OrConditionX<E, H> orBetween(String alias, PropFn<T, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public <T extends Entity> OrConditionX<E, H> orBetweenX(PropFn<T, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<E, H> orBetween(PropFn<E, Year> propFn, Year value1, Year value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> OrConditionX<E, H> orBetween(String alias, PropFn<T, Year> propFn, Year value1, Year value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public <T extends Entity> OrConditionX<E, H> orBetweenX(PropFn<T, Year> propFn, Year value1, Year value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public OrConditionX<E, H> orBetween(PropFn<E, Number> propFn, Number value1, Number value2) {
        return orBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> OrConditionX<E, H> orBetween(String alias, PropFn<T, Number> propFn, Number value1, Number value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public <T extends Entity> OrConditionX<E, H> orBetweenX(PropFn<T, Number> propFn, Number value1, Number value2) {
        return orBetween(null, propFn, value1, value2);
    }
}
