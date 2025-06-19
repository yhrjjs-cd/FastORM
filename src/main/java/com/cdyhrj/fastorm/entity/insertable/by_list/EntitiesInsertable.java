package com.cdyhrj.fastorm.entity.insertable.by_list;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.config.FastOrmConfig;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对象列表插入，支持批量
 *
 * @param <E> 插入对象类
 */
@Slf4j
public class EntitiesInsertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final List<E> entities;

    /**
     * 批量处理每次数量
     */
    private int batchSize;

    public EntitiesInsertable(NamedParameterJdbcOperations namedParameterJdbcOperations,
                              TransactionTemplate transactionTemplate,
                              FastOrmConfig fastOrmConfig,
                              List<E> entities) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entities = entities;

        batchSize = fastOrmConfig.getBatchSize();
    }

    /**
     * 设置批量处理大小
     *
     * @param batchSize 批量大小
     * @return 当前对象
     */
    public EntitiesInsertable<E> batchSize(int batchSize) {
        this.batchSize = batchSize;

        return this;
    }

    public void exec() {
        if (entities.isEmpty()) {
            return;
        }

        Entity entity = entities.get(0);
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());
        String sqlText = SqlHelper.generateInsertSqlText(entityProxy, entity);

        for (E e : entities) {
            e.beforeInsert();
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                List<Map<String, Object>> paramMapList = new ArrayList<>();
                for (int i = 0; i < entities.size(); i++) {
                    E e = entities.get(i);
                    entityProxy.updateEntityWithDefaultValue(e, OperationType.INSERT);
                    paramMapList.add(entityProxy.getValueMap(e));

                    if (i % batchSize == 0) {
                        namedParameterJdbcOperations.batchUpdate(sqlText, paramMapList.<Map<String, Object>>toArray(Map[]::new));

                        paramMapList.clear();
                    }
                }

                if (!paramMapList.isEmpty()) {
                    namedParameterJdbcOperations.batchUpdate(sqlText, paramMapList.<Map<String, Object>>toArray(Map[]::new));
                }
            }
        });

        for (E e : entities) {
            e.afterInsert();
        }
    }
}
