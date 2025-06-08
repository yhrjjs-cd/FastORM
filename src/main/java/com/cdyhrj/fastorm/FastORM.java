package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import org.springframework.stereotype.Component;


/**
 * FastORM 入口
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
@Component
public class FastORM {
    public <E extends Entity> EntityQueryable<E> queryable(Class<E> entityClazz) {
        return new EntityQueryable<>(entityClazz);
    }
}
