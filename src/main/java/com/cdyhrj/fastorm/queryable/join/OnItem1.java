package com.cdyhrj.fastorm.queryable.join;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.LambdaColumn;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.meta.AliasEntity;

public class OnItem1<E extends Entity> implements OnItem {
    public static <E extends Entity> OnItem1<E> of(AliasEntity<E> aliasEntity) {
        return new OnItem1<>(aliasEntity);
    }

    private final AliasEntity<E> aliasEntity;
    private String key;
    private Object value;

    private OnItem1(AliasEntity<E> aliasEntity) {
        this.aliasEntity = aliasEntity;
    }

    public OnItem1<E> keyValue(PropFn<E, ?> keyFn, Object value) {
        this.key = LambdaColumn.resolve(keyFn).getName();
        this.value = value;

        return this;
    }

    @Override
    public String toSqlString() {
        return "%s.%s = %s".formatted(aliasEntity.getAlias(), this.key, this.value);
    }
}
