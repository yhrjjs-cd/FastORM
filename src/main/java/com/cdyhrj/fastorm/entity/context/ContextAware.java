package com.cdyhrj.fastorm.entity.context;

import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;

public interface ContextAware<E extends Entity, H extends ConditionHost<E>> {
    /**
     * @return 上下文
     */
    ToSqlContext<E, H> getContext();
}
