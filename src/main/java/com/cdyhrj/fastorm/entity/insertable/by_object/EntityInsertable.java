/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fastorm.entity.insertable.by_object;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.api.enums.RelationType;
import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.insertable.by_class.EntityClassInsertable;
import com.cdyhrj.fastorm.entity.meta.ManyToManyMasterInfo;
import com.cdyhrj.fastorm.entity.meta.ManyToManyMeta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityInsertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;
    private final EntityProxy entityProxy;

    public EntityInsertable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, E entity) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entity = entity;
        this.entityProxy = Entity.getEntityProxy(entity.getClass());
    }

    /**
     * 关系
     */
    private List<String> relations;

    /**
     * 主键字段名，默认id
     */
    private String primaryKeyName = "id";

    /**
     * 插入关系
     *
     * @param relation 关系名
     * @return 当前对象
     */
    public EntityInsertable<E> withRelation(@NonNull PropFn<E, ? extends Entity> relation) {
        if (Objects.isNull(this.relations)) {
            this.relations = new ArrayList<>();
        }

        this.relations.add(LambdaQuery.resolve(relation).getName());

        return this;
    }

    /**
     * 设置主键字段名称， 默认为：<code>id</code>
     *
     * @param primaryKeyName 主键字段名
     * @return 当前对象
     */
    public EntityInsertable<E> primaryKeyName(@NonNull String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;

        return this;
    }


    /**
     * 执行插入操作
     */
    public int insert() {
        entityProxy.updateEntityWithDefaultValue(entity, OperationType.INSERT);

        if (Objects.isNull(relations)) {
            return insertWithNoRelation();
        } else {
            return insertWithRelations();
        }
    }

    public Long insertReturnId() {
        insert();

        return this.entityProxy.getIdValue(entity);
    }

    private int insertWithNoRelation() {
        Map<String, Object> paramMap = entityProxy.getValueMap(entity);
        String sqlText = SqlHelper.generateInsertSqlText(entityProxy, entity);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap), keyHolder, new String[]{primaryKeyName});

        Number key = keyHolder.getKey();
        if (Objects.nonNull(key)) {
            entityProxy.updateEntityId(entity, key.longValue());
        }

        return rows;
    }

    private Integer insertWithRelations() {
        return transactionTemplate.execute(status -> {
            Map<String, Object> paramMap = entityProxy.getValueMap(entity);
            String sqlText = SqlHelper.generateInsertSqlText(entityProxy, entity);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            int rows = this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap), keyHolder, new String[]{primaryKeyName});
            Number key = keyHolder.getKey();
            if (Objects.nonNull(key)) {
                entityProxy.updateEntityId(entity, key.longValue());
            }

            for (String relation : relations) {
                RelationType relationType = entityProxy.getRelationType(relation);

                switch (relationType) {
                    case OneToOne -> insertOneToOneRelation(entity, relation);
                    case OneToMany -> insertOneToManyRelation(entity, relation);
                    case ManyToMany -> insertManyToManyRelation(entity, relation);
                }
            }

            return rows;
        });
    }

    private void insertOneToOneRelation(Entity entity, String relation) {
        Entity relationEntity = entityProxy.getRelationOneToOneValue(entity, relation);

        if (Objects.nonNull(relationEntity)) {
            EntityProxy relationEntityProxy = Entity.getEntityProxy(relationEntity.getClass());
            relationEntityProxy.updateEntityOneToOneId(relationEntity, entityProxy.getIdValue(entity));

            new EntityInsertable<>(namedParameterJdbcOperations, transactionTemplate, relationEntity).insert();
        }
    }

    private void insertOneToManyRelation(Entity entity, String relation) {
        List<Entity> relationEntities = Objects.requireNonNull(entityProxy.getRelationOneToManyValue(entity, relation));

        long mainId = entityProxy.getIdValue(entity);
        if (!relationEntities.isEmpty()) {
            Class<? extends Entity> relationEntitieClass = relationEntities.get(0).getClass();
            EntityProxy relationEntityProxy = Entity.getEntityProxy(relationEntitieClass);

            for (Entity re : relationEntities) {
                relationEntityProxy.updateEntityOneToManyName(re, mainId);
                new EntityInsertable<>(namedParameterJdbcOperations, transactionTemplate, re).insert();
            }
        }
    }

    private void insertManyToManyRelation(Entity entity, String relation) {
        List<Entity> relationEntities = entityProxy.getRelationManyToManyValue(entity, relation);
        if (relationEntities.isEmpty()) {
            return;
        }

        ManyToManyMasterInfo manyToManyMasterInfo = entityProxy.getManyToManyRelationMasterInfoClass(relation);
        EntityProxy middleEntityProxy = Entity.getEntityProxy(manyToManyMasterInfo.relationClass());
        ManyToManyMeta manyToManyMeta = middleEntityProxy.getRelationManyToManyMeta();

        Long leftId = entityProxy.getIdValue(entity);

        for (Entity re : relationEntities) {
            Long id = new EntityInsertable<>(namedParameterJdbcOperations, transactionTemplate, re).insertReturnId();

            new EntityClassInsertable<>(namedParameterJdbcOperations, transactionTemplate, manyToManyMasterInfo.relationClass())
                    .set(manyToManyMeta.leftFieldName(), leftId)
                    .set(manyToManyMeta.rightFieldName(), id)
                    .insert();
        }
    }
}
