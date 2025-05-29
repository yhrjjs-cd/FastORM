package com.cdyhrj.fastorm.queryable.join;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.meta.AliasEntity;
import com.cdyhrj.fastorm.meta.Node;
import com.cdyhrj.fastorm.queryable.Queryable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Join<L extends Entity, R extends Entity> implements Node {
    public static <L extends Entity, R extends Entity> Join<L, R> of(
            Queryable<L> queryable,
            JoinType joinType,
            AliasEntity<L> leftEntity,
            AliasEntity<R> rightEntity) {
        Join<L, R> newInstance = new Join<>();

        newInstance.queryable = queryable;
        newInstance.joinType = joinType;
        newInstance.leftEntity = leftEntity;
        newInstance.rightEntity = rightEntity;

        return newInstance;
    }

    private Queryable<L> queryable;
    private JoinType joinType;
    private AliasEntity<L> leftEntity;
    private AliasEntity<R> rightEntity;
    private final List<OnItem> items = new ArrayList<>();

    public Queryable<L> ret() {
        return this.queryable;
    }

    public Join<L, R> andEqual(PropFn<L, ?> leftKey, PropFn<R, ?> rightKey) {
        OnItem item = OnItem2.of(leftEntity, rightEntity)
                .leftKey(leftKey)
                .rightKey(rightKey);

        this.items.add(item);

        return this;
    }

    public Join<L, R> andLeftEqual(PropFn<L, ?> leftKey, Object value) {
        OnItem item = OnItem1.of(leftEntity).keyValue(leftKey, value);
        this.items.add(item);

        return this;
    }

    public Join<L, R> andRightEqual(PropFn<R, ?> rightKey, Object value) {
        OnItem item = OnItem1.of(rightEntity).keyValue(rightKey, value);
        this.items.add(item);

        return this;
    }

    @Override
    public String toSqlString() {
        return "%s %s ON %s".formatted(joinType.getSqlString(), rightEntity.toSqlString(), getItemsString());
    }

    private String getItemsString() {
        return items.stream().map(OnItem::toSqlString)
                .collect(Collectors.joining(" AND "));
    }
}
