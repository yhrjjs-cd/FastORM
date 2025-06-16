package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.insertable.EntitiesInsertable;
import com.cdyhrj.fastorm.entity.insertable.EntityClassInsertable;
import com.cdyhrj.fastorm.entity.insertable.EntityInsertable;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;


/**
 * FastORM 入口
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
@Component
public class FastORM {
    private final TransactionTemplate transactionTemplate;
    private final NamedParameterJdbcOperations namedParamOps;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public FastORM(DataSource dataSource, TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
        this.namedParamOps = new NamedParameterJdbcTemplate(dataSource);
    }

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
        return new EntityInsertable<>(namedParamOps, transactionTemplate, entity);
    }

    /**
     * 实体列表插入
     *
     * @param entities 实例列表
     * @param <E>      实体类
     * @return 插入器
     */
    public <E extends Entity> EntitiesInsertable<E> insertable(List<E> entities) {
        return new EntitiesInsertable<>(namedParameterJdbcOperations, transactionTemplate, fastOrmConfig, entities);
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
