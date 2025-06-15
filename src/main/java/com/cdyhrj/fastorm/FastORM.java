package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.config.FastOrmConfig;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.insertable.EntitiesInsertable;
import com.cdyhrj.fastorm.entity.insertable.EntityClassInsertable;
import com.cdyhrj.fastorm.entity.insertable.EntityInsertable;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;


/**
 * FastORM 入口
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
@Component
@RequiredArgsConstructor
public class FastORM {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final FastOrmConfig fastOrmConfig;

    public <E extends Entity> EntityQueryable<E> queryable(Class<E> entityClazz) {
        return new EntityQueryable<>(entityClazz);
    }

    /**
     * 实体插入
     *
     * @param entity 实体
     * @param <E>    实体类
     * @return 插入器
     */
    public <E extends Entity> EntityInsertable<E> insertable(E entity) {
        return new EntityInsertable<>(namedParameterJdbcOperations, transactionTemplate);
    }

    /**
     * 实体列表插入
     *
     * @param entities 实例列表
     * @param <E>      实体类
     * @return 插入器
     */
    public <E extends Entity> EntitiesInsertable<E> insertable(List<E> entities) {
        return new EntitiesInsertable<>(namedParameterJdbcOperations, transactionTemplate, fastOrmConfig);
    }

    /**
     * 实体列表插入
     *
     * @param entityClass 实例类
     * @param <E>         实体类
     * @return 插入器
     */
    public <E extends Entity> EntityClassInsertable<E> insertable(Class<E> entityClass) {
        return new EntityClassInsertable<>(namedParameterJdbcOperations, transactionTemplate, fastOrmConfig);
    }
}
