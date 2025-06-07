package com.cdyhrj.fastorm.queryable.context;

import com.cdyhrj.fastorm.meta.SqlSegment;

public abstract class AbstractContextAware implements SqlSegment, ContextAware {
    protected Context context;

    @Override
    public final void setContext(Context context) {
        this.context = context;
    }
}
