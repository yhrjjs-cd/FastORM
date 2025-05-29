package com.cdyhrj.fastorm.queryable.join;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.LambdaColumn;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.meta.AliasEntity;

public class OnItem2<L extends Entity, R extends Entity> implements OnItem {
    public static <L extends Entity, R extends Entity> OnItem2<L, R> of(AliasEntity<L> leftEntity, AliasEntity<R> rightEntity) {
        return new OnItem2<>(leftEntity, rightEntity);
    }

    private final AliasEntity<L> leftEntity;
    private final AliasEntity<R> rightEntity;
    private String leftKey;
    private String rightKey;

    private OnItem2(AliasEntity<L> leftEntity, AliasEntity<R> rightEntity) {
        this.leftEntity = leftEntity;
        this.rightEntity = rightEntity;
    }

    public OnItem2<L, R> leftKey(PropFn<L, ?> leftKeyFn) {
        this.leftKey = LambdaColumn.resolve(leftKeyFn).getName();

        return this;
    }

    public OnItem2<L, R> rightKey(PropFn<R, ?> rightKeyFn) {
        this.rightKey = LambdaColumn.resolve(rightKeyFn).getName();

        return this;
    }

    @Override
    public String toSqlString() {
        return "%s.%s = %s.%s".formatted(leftEntity.getAlias(), leftKey, rightEntity.getAlias(), rightKey);
    }
}
