package com.cdyhrj.fast.orm.entity.context;

import com.cdyhrj.fast.orm.condition.ConditionHost;
import com.cdyhrj.fast.orm.entity.Entity;

public interface ContextAware<E extends Entity, H extends ConditionHost<E>> {
    /**
     * @return 上下文
     */
    ToSqlContext<E, H> getContext();
}
