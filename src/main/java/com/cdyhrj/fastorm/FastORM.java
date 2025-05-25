package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.entity.Base2Entity;
import com.cdyhrj.fastorm.entity.BaseEntity;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.queryable.On;
import com.cdyhrj.fastorm.queryable.Queryable;
import org.springframework.stereotype.Component;

/**
 * FastORM 入口
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
@Component
public class FastORM {
    public <E extends Entity> Queryable<E> queryable(Class<E> entityClazz) {
        return new Queryable<>(entityClazz);
    }

    public static void main(String[] args) {
        FastORM orm = new FastORM();
        orm.queryable(BaseEntity.class)
                .join(Base2Entity.class,
                        On.of(BaseEntity.class, Base2Entity.class));
    }
}
