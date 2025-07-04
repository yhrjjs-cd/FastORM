package com.cdyhrj.fast.orm;

import com.cdyhrj.fast.orm.config.FastOrmConfig;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.deletable.by_class.EntityClassDeletable;
import com.cdyhrj.fast.orm.entity.deletable.by_object.EntityDeletable;
import com.cdyhrj.fast.orm.entity.fetchable.EntityByClassFetchable;
import com.cdyhrj.fast.orm.entity.insertable.by_class.EntityClassInsertable;
import com.cdyhrj.fast.orm.entity.insertable.by_list.EntitiesInsertable;
import com.cdyhrj.fast.orm.entity.insertable.by_object.EntityInsertable;
import com.cdyhrj.fast.orm.entity.queryable.EntityByClassQueryable;
import com.cdyhrj.fast.orm.entity.queryablex.EntityByClassQueryableX;
import com.cdyhrj.fast.orm.entity.updatable.by_class.EntityClassUpdatable;
import com.cdyhrj.fast.orm.entity.updatable.by_object.EntityUpdatable;
import com.cdyhrj.fast.orm.pager.IPagerProvider;
import com.cdyhrj.fast.orm.sql.SqlQuery;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;


/**
 * FastORM 入口
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class FastORM {
    private final TransactionTemplate transactionTemplate;
    private final NamedParameterJdbcOperations namedParamOps;
    private final FastOrmConfig fastOrmConfig;
    private final IPagerProvider pagerProvider;

    public FastORM(@NonNull DataSource dataSource,
                   @NonNull TransactionTemplate transactionTemplate,
                   @NonNull FastOrmConfig fastOrmConfig,
                   @NonNull IPagerProvider pagerProvider) {
        this.transactionTemplate = transactionTemplate;
        this.namedParamOps = new NamedParameterJdbcTemplate(dataSource);
        this.fastOrmConfig = fastOrmConfig;
        this.pagerProvider = pagerProvider;
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
        return new EntitiesInsertable<>(namedParamOps, transactionTemplate, fastOrmConfig, entities);
    }

    /**
     * 实体列表插入
     *
     * @param entityClass 实例类
     * @param <E>         实体类
     * @return 插入器
     */
    public <E extends Entity> EntityClassInsertable<E> insertable(Class<E> entityClass) {
        return new EntityClassInsertable<>(namedParamOps, transactionTemplate, entityClass);
    }

    public <E extends Entity> EntityUpdatable<E> updatable(E entity) {
        return new EntityUpdatable<>(namedParamOps, transactionTemplate, entity);
    }

    public <E extends Entity> EntityClassUpdatable<E> updatable(Class<E> entityClass) {
        return new EntityClassUpdatable<>(namedParamOps, transactionTemplate, entityClass);
    }

    public <E extends Entity> EntityDeletable<E> deletable(E entity) {
        return new EntityDeletable<>(namedParamOps, transactionTemplate, entity);
    }

    public <E extends Entity> EntityClassDeletable<E> deletable(Class<E> entityClass) {
        return new EntityClassDeletable<>(namedParamOps, transactionTemplate, entityClass);
    }

    public <E extends Entity> EntityByClassFetchable<E> fetchable(Class<E> entityClass) {
        return new EntityByClassFetchable<>(namedParamOps, pagerProvider, entityClass);
    }

    public <E extends Entity> EntityByClassQueryable<E> queryable(Class<E> entityClass) {
        return new EntityByClassQueryable<>(namedParamOps, pagerProvider, entityClass);
    }

    public <E extends Entity> EntityByClassQueryableX<E> queryableX(Class<E> entityClass) {
        return new EntityByClassQueryableX<>(namedParamOps, pagerProvider, entityClass);
    }

    public SqlQuery sqlQuery() {
        return SqlQuery.of(namedParamOps, pagerProvider);
    }
}
