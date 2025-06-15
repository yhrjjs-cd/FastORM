package com.cdyhrj.fastorm.entity.base;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;

@RequiredArgsConstructor
public class AbstractStatementSpec {
    protected final NamedParameterJdbcOperations namedParameterJdbcOperations;
    protected final TransactionTemplate transactionTemplate;
//    protected Chain paramChain;

    /**
     * 字段清单
     */
//    public AbstractStatementSpec(
//            NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate) {
//        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
//        this.transactionTemplate = transactionTemplate;
//    }
//    protected Map<String, Object> paramChainToMap() {
//        if (Objects.isNull(this.paramChain)) {
//            return Collections.emptyMap();
//        }
//
//        return this.paramChain.toParamMap();
//    }
}
