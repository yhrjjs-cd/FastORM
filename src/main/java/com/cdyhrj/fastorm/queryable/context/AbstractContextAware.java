package com.cdyhrj.fastorm.queryable.context;

import com.cdyhrj.fastorm.meta.SqlNode;

public abstract class AbstractContextAware implements SqlNode, ContextAware {
    protected Context context;

    @Override
    public final void setContext(Context context) {
        this.context = context;
    }
}
