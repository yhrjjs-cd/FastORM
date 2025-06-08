package com.cdyhrj.fastorm.entity.queryable.context;

import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.entity.Entity;

public abstract class AbstractSqlSegmentContextAware<E extends Entity> implements SqlSegment, ContextAware<E> {
    private final ToSqlContext<E> context;

    public AbstractSqlSegmentContextAware(ToSqlContext<E> context) {
        this.context = context;
    }

    @Override
    public ToSqlContext<E> getContext() {
        return context;
    }
}
