/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fastorm.entity.insertable.by_object;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class EntityInsertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;

    public void exec() {
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());
        Map<String, Object> paramMap = entityProxy.getValueMap(entity);

        System.out.println("dddddddddddd");
    }

    public Long execReturnId() {
        return 0L;
    }

    public String execReturnName() {
        return "";
    }
}
