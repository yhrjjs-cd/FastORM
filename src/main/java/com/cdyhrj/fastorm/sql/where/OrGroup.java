package com.cdyhrj.fastorm.sql.where;

import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.enums.Operator;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrGroup implements Item {
    private final List<Item> items = new ArrayList<>();

    @Setter
    private AndGroup belongTo;

    @Override
    public String toSql() {
        return items.stream().map(Item::toSql).collect(Collectors.joining(" OR "));
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        items.forEach(item -> item.writeToParamMap(paramMap));
    }

    private OrGroup add(Item item) {
        this.items.add(item);

        return this;
    }

    public AndGroup orAndGroup(AndGroup andGroup) {
        andGroup.setBelongTo(this);
        this.add(andGroup);

        return andGroup;
    }

    public AndGroup end() {
        return belongTo;
    }

    public OrGroup orEq(String key, Object value) {
        return add(UnaryItem.of(key, Operator.EQ, value));
    }

    public OrGroup orGt(String key, Number value) {
        return add(UnaryItem.of(key, Operator.GT, value));
    }

    public OrGroup orGt(String key, Temporal value) {
        return add(UnaryItem.of(key, Operator.GT, value));
    }

    public OrGroup orGte(String key, Number value) {
        return add(UnaryItem.of(key, Operator.GTE, value));
    }

    public OrGroup orGte(String key, Temporal value) {
        return add(UnaryItem.of(key, Operator.GTE, value));
    }

    public OrGroup orLt(String key, Number value) {
        return add(UnaryItem.of(key, Operator.LT, value));
    }

    public OrGroup orLt(String key, Temporal value) {
        return add(UnaryItem.of(key, Operator.LT, value));
    }

    public OrGroup orLte(String key, Number value) {
        return add(UnaryItem.of(key, Operator.LTE, value));
    }

    public OrGroup orLte(String key, Temporal value) {
        return add(UnaryItem.of(key, Operator.LTE, value));
    }

    public OrGroup orStartWith(String key, String value) {
        return add(UnaryItem.of(key, Operator.LIKE, value + "%s"));
    }

    public OrGroup orEndWith(String key, String value) {
        return add(UnaryItem.of(key, Operator.EQ, value + "%s"));
    }

    public OrGroup orLike(String key, String value) {
        return add(UnaryItem.of(key, Operator.EQ, "%s" + value + "%s"));
    }

    public OrGroup orIsNull(String key) {
        return add(ZeroItem.of(key, Operator.IS_NULL));
    }

    public OrGroup orBetween(String key, Number value1, Number value2) {
        return add(BinaryItem.of(key, Operator.BETWEEN, value1, value2));
    }

    public OrGroup orBetween(String key, Temporal value1, Temporal value2) {
        return add(BinaryItem.of(key, Operator.BETWEEN, value1, value2));
    }
}
