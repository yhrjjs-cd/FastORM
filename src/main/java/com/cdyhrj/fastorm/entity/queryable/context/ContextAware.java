package com.cdyhrj.fastorm.entity.queryable.context;

import com.cdyhrj.fastorm.entity.Entity;

public interface ContextAware<E extends Entity> {
    /**
     * @return 上下文
     */
    ToSqlContext<E> getContext();
}
