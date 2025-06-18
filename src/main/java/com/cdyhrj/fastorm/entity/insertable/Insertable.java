/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fastorm.entity.insertable;

import com.cdyhrj.fastorm.api.chain.Chain;
import com.cdyhrj.fastorm.api.lambda.LambdaColumn;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.config.FastOrmConfig;
import com.cdyhrj.fastorm.entity.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class Insertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final FastOrmConfig fastOrmConfig;

    protected Chain<E> paramChain;

    /**
     * 关系
     */
    private List<String> relations;

    /**
     * 插入列
     */
    @Getter
    private List<String> withColumns;

    /**
     * 忽略列
     */
    @Getter
    private List<String> ignoreColumns;

    /**
     * 批量处理每次数量
     */
//    private int batchSize = fastOrmConfig.getBatchSize();

    /**
     * 插入列，不包含的列不插入
     *
     * @param columns 多列
     * @return 当前对象
     */
    @SafeVarargs
    public final Insertable<E> withColumns(@NonNull PropFn<E, ?>... columns) {
        if (Objects.isNull(this.withColumns)) {
            this.withColumns = new ArrayList<>();
        }

        for (final PropFn<E, ?> column : columns) {
            this.withColumns.add(LambdaColumn.resolve(column).getName());
        }

        return this;
    }

    /**
     * 忽略列, 不插入
     *
     * @param columns 多列
     * @return 当前对象
     */
    @SafeVarargs
    public final Insertable<E> ignoreColumns(@NonNull PropFn<E, ?>... columns) {
        if (Objects.isNull(this.ignoreColumns)) {
            this.ignoreColumns = new ArrayList<>();
        }

        for (final PropFn<E, ?> column : columns) {
            this.ignoreColumns.add(LambdaColumn.resolve(column).getName());
        }

        return this;
    }

    /**
     * 插入关系 <font color="red">请注意：1次只能插入1个关系</font>
     *
     * @param relation 关系名
     * @return 当前对象
     */
    public Insertable<E> withRelation(@NonNull PropFn<E, ?> relation) {
        if (Objects.isNull(this.relations)) {
            this.relations = new ArrayList<>();
        }

        this.relations.add(LambdaColumn.resolve(relation).getName());

        return this;
    }

    /**
     * 设置批量处理大小
     *
     * @param batchSize 批量大小
     * @return 当前对象
     */
    public Insertable<E> batchSize(int batchSize) {
//        this.batchSize = batchSize;

        return this;
    }

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    public Insertable<E> set(@NonNull PropFn<E, ?> keyFun, Object value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        return this;
    }

//    /**
//     * 插入实体
//     *
//     * @param entity 实体对象
//     * @return 插入的实体，一些字段会填充
//     */
//    public <T extends Entity> T insert(T entity) {
//        PeerEntity peerEntity = Entity.getPeerEntity(entity.getClass());
//        peerEntity.updateEntityWithDefaultValue(entity, OperationType.INSERT);
//
//        Map<String, Object> paramMap = peerEntity.getValueMap(entity);
//        String sqlText = InsertSqlTextHelper.generateInsertSqlTextWithEntity(peerEntity, this);
//
//        if (Objects.isNull(this.relations)) {
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap), keyHolder);
//
//            Number key = keyHolder.getKey();
//            if (Objects.nonNull(key)) {
//                peerEntity.updateEntityId(entity, key.longValue());
//            }
//
//            return entity;
//        } else {
//            return insertWithRelation(entity, sqlText, paramMap, peerEntity);
//        }
//    }
//
//    /**
//     * 带关系插入
//     */
//    private <T extends Entity> T insertWithRelation(T entity, String sqlText, Map<String, Object> paramMap, PeerEntity peerEntity) {
//        return transactionTemplate.execute(status -> {
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap), keyHolder);
//
//            for (String relation : relations) {
//                RelationType relationType = peerEntity.getRelationType(relation);
//
//                Number key = keyHolder.getKey();
//                if (Objects.nonNull(key)) {
//                    peerEntity.updateEntityId(entity, key.longValue());
//                }
//
//                switch (relationType) {
//                    case OneToOne -> insertOneToOneRelation(peerEntity, entity, relation);
//                    case OneToMany -> insertOneToManyRelation(peerEntity, entity, relation);
//                    case ManyToMany -> insertManyToManyRelation(peerEntity, entity, relation);
//                }
//            }
//
//            return entity;
//        });
//    }
//
//    /**
//     * 根据实体类插入，<b style=color:red>插入前需要通过Param设置参数</b>
//     */
//    public void insert(Class<?> classOfEntity) {
//        PeerEntity peerEntity = Entity.getPeerEntity(classOfEntity);
//
//        Map<String, Object> paramMap = peerEntity.getDefaultValueMap(OperationType.INSERT);
//        paramMap.putAll(this.paramChainToMap());
//
//        String sqlText = InsertSqlTextHelper.generateInsertSqlTextWithClass(peerEntity, paramMap);
//
//        this.namedParameterJdbcOperations.update(sqlText, paramMap);
//    }
//
//    /**
//     * 批量插入
//     *
//     * @param entityList 实体列表
//     */
//    public void batchInsert(List<? extends Entity> entityList) {
//        Assert.isTrue(!entityList.isEmpty(), "Batch insert size is 0");
//
//        PeerEntity peerEntity = Entity.getPeerEntity(entityList.get(0).getClass());
//        String sqlText = InsertSqlTextHelper.generateInsertSqlTextWithEntity(peerEntity, this);
//
//        List<? extends List<? extends Entity>> batchList = Lists.partition(entityList, batchSize);
//        for (List<? extends Entity> batch : batchList) {
//            SqlParameterSource[] values = new SqlParameterSource[batch.size()];
//            for (int i = 0, size = batch.size(); i < size; i++) {
//                Entity entity = batch.get(i);
//
//                peerEntity.updateEntityWithDefaultValue(entity, OperationType.INSERT);
//                values[i] = new MapSqlParameterSource(peerEntity.getValueMap(entity));
//            }
//
//            this.namedParameterJdbcOperations.batchUpdate(sqlText, values);
//        }
//    }
//
//
//    private void insertOneToOneRelation(PeerEntity peerEntity, Entity entity, String relation) {
//        Entity relationEntity = peerEntity.getRelationOneToOneValue(entity, relation);
//
//        if (Objects.nonNull(relationEntity)) {
//            PeerEntity peerRelationEntity = Entity.getPeerEntity(relationEntity.getClass());
//            peerRelationEntity.updateEntityOneToOneId(relationEntity, peerEntity.getIdFieldValue(entity));
//
//            Class<? extends Entity> clazzOfRelationEntity = relationEntity.getClass();
//            InsertStatementSpec.of(namedParameterJdbcOperations, transactionTemplate)
//                    .insert(relationEntity);
//        }
//    }
//
//    private void insertOneToManyRelation(PeerEntity peerEntity, Entity entity, String relation) {
//        List<Entity> relationEntities = peerEntity.getRelationOneToManyValue(entity, relation);
//        if (Objects.nonNull(relationEntities) && !relationEntities.isEmpty()) {
//            Class<? extends Entity> relationEntitieClass = relationEntities.get(0).getClass();
//            PeerEntity peerRelationEntity = Entity.getPeerEntity(relationEntitieClass);
//            long mainId = peerEntity.getIdFieldValue(entity);
//
//            for (Entity re : relationEntities) {
//                peerRelationEntity.updateEntityOneToManyName(re, mainId);
//
//                InsertStatementSpec.of(namedParameterJdbcOperations, transactionTemplate)
//                        .insert(re);
//            }
//        }
//    }
//
//    private void insertManyToManyRelation(PeerEntity peerEntity, Entity entity, String relation) {
//        List<Entity> relationEntities = peerEntity.getRelationManyToManyValue(entity, relation);
//        if (relationEntities.isEmpty()) {
//            return;
//        }
//
//        ManyToManyMasterInfo manyToManyMasterInfo = peerEntity.getManyToManyRelationMasterInfoClass(relation);
//        PeerEntity targetPeerEntity = Entity.getPeerEntity(relationEntities.get(0).getClass());
//        PeerEntity middlePeerEntity = Entity.getPeerEntity(manyToManyMasterInfo.relationClass());
//        ManyToManyMeta manyToManyMeta = middlePeerEntity.getRelationManyToManyMeta();
//
//        long leftId = peerEntity.getIdFieldValue(entity);
//
//        for (Entity re : relationEntities) {
//            Entity r = InsertStatementSpec.of(namedParameterJdbcOperations, transactionTemplate)
//                    .insert(re);
//
//            InsertStatementSpec.of(namedParameterJdbcOperations, transactionTemplate)
//                    .param(manyToManyMeta.leftFieldName(), leftId)
//                    .param(manyToManyMeta.rightFieldName(), targetPeerEntity.getIdFieldValue(r))
//                    .insert(manyToManyMasterInfo.relationClass());
//        }
//    }
}
