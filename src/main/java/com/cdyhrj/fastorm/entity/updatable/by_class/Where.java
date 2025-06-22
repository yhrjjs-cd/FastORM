package com.cdyhrj.fastorm.entity.updatable.by_class;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

public class Where<T extends Entity> extends AndCondition<T, EntityClassUpdatable<T>> {
    private final EntityClassUpdatable<T> host;

    public Where(ToSqlContext<T, EntityClassUpdatable<T>> context, EntityClassUpdatable<T> entityClassUpdatable) {
        super(context);

        this.host = entityClassUpdatable;
    }

    @Override
    public EntityClassUpdatable<T> ret() {
        return this.host;
    }
}
