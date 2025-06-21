package com.cdyhrj.fastorm.entity.updatable.by_object;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class EntityUpdatable<E extends Entity> implements ConditionHost<E> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;
    /**
     * 查询上下文
     */
    @Getter
    private final ToSqlContext<E> context;

    public EntityUpdatable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, E entity) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entity = entity;
        //noinspection unchecked
        this.context = new ToSqlContext<>(this, (Class<E>) entity.getClass());
    }


    private Where<E> where;

    public Where<E> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    /**
     * 执行插入操作
     *
     * @return 插入的对象
     */
    public E exec() {
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());
        entityProxy.updateEntityWithDefaultValue(entity, OperationType.UPDATE);

        entity.beforeUpdate();

        Map<String, Object> paramMap = entityProxy.getValueMap(entity);

//        if (Objects.nonNull(this.condition)) {
//            ParamMap conditionParamMap = ParamMap.of();
//            this.condition.writeToParamMap(conditionParamMap);
//
//            paramMap.putAll(conditionParamMap.getParams());
//        }

        String sqlText = SqlHelper.generateUpdateSqlTextWithEntity(entityProxy, entity);

//        this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap));

        System.out.println(sqlText);
        return entity;
    }
}
