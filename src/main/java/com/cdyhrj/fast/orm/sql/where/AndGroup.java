package com.cdyhrj.fast.orm.sql.where;

import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.condition.enums.Operator;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AndGroup implements Item {
    private final List<Item> items = new ArrayList<>();

    @Setter
    private OrGroup belongTo;


    @Override
    public String toSql() {
        return items.stream().map(Item::toSql).collect(Collectors.joining(" AND "));
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        items.forEach(item -> item.writeToParamMap(paramMap));
    }

    private AndGroup add(Item item) {
        this.items.add(item);

        return this;
    }

    public OrGroup andOrGroup(OrGroup orGroup) {
        orGroup.setBelongTo(this);
        this.add(orGroup);

        return orGroup;
    }

    public OrGroup end() {
        return belongTo;
    }

    public AndGroup andEq(String key, Object value) {
        return add(UnaryItem.of(key, Operator.EQ, value));
    }

    public AndGroup andGt(String key, Number value) {
        return add(UnaryItem.of(key, Operator.GT, value));
    }

    public AndGroup andGt(String key, Temporal value) {
        return add(UnaryItem.of(key, Operator.GT, value));
    }

    public AndGroup andGte(String key, Number value) {
        return add(UnaryItem.of(key, Operator.GTE, value));
    }

    public AndGroup andGte(String key, Temporal value) {
        return add(UnaryItem.of(key, Operator.GTE, value));
    }

    public AndGroup andLt(String key, Number value) {
        return add(UnaryItem.of(key, Operator.LT, value));
    }

    public AndGroup andLt(String key, Temporal value) {
        return add(UnaryItem.of(key, Operator.LT, value));
    }

    public AndGroup andLte(String key, Number value) {
        return add(UnaryItem.of(key, Operator.LTE, value));
    }

    public AndGroup andLte(String key, Temporal value) {
        return add(UnaryItem.of(key, Operator.LTE, value));
    }

    public AndGroup andStartWith(String key, String value) {
        return add(UnaryItem.of(key, Operator.LIKE, value + "%s"));
    }

    public AndGroup andEndWith(String key, String value) {
        return add(UnaryItem.of(key, Operator.EQ, value + "%s"));
    }

    public AndGroup andLike(String key, String value) {
        return add(UnaryItem.of(key, Operator.EQ, "%s" + value + "%s"));
    }

    public AndGroup andIsNull(String key) {
        return add(ZeroItem.of(key, Operator.IS_NULL));
    }

    public AndGroup andBetween(String key, Number value1, Number value2) {
        return add(BinaryItem.of(key, Operator.BETWEEN, value1, value2));
    }

    public AndGroup andBetween(String key, Temporal value1, Temporal value2) {
        return add(BinaryItem.of(key, Operator.BETWEEN, value1, value2));
    }
}
