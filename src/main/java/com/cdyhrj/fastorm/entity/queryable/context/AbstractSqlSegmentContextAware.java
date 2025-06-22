package com.cdyhrj.fastorm.entity.queryable.context;

import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;

public abstract class AbstractSqlSegmentContextAware<E extends Entity, H extends ConditionHost<E>> implements SqlSegment, ContextAware<E, H> {
    private final ToSqlContext<E, H> context;

    public AbstractSqlSegmentContextAware(ToSqlContext<E, H> context) {
        this.context = context;
    }

    @Override
    public ToSqlContext<E, H> getContext() {
        return context;
    }
}
