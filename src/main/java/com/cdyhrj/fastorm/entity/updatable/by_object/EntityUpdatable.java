package com.cdyhrj.fastorm.entity.updatable.by_object;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class EntityUpdatable<E extends Entity> implements ConditionHost<E> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;
    private Where<E> where;

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
