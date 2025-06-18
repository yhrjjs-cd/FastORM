package com.cdyhrj.fastorm.entity.insertable;

import com.cdyhrj.fastorm.config.FastOrmConfig;
import com.cdyhrj.fastorm.entity.Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * 对象列表插入，支持批量
 *
 * @param <E> 插入对象类
 */
@Slf4j
@RequiredArgsConstructor
public class EntitiesInsertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final FastOrmConfig fastOrmConfig;
    private final List<E> entities;

    /**
     * 批量处理每次数量
     */
    private int batchSize = 200;//fastOrmConfig.getBatchSize();

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

    }
}
