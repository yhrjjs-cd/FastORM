package com.cdyhrj.fastorm.entity.enhance;

import com.cdyhrj.fastorm.FastORM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Slf4j
@Configuration
class FastORMDefiner {
    public FastORMDefiner() {
        if (log.isDebugEnabled()) {
            log.debug("Register Bean SqlClient Complete.");
        }
    }

    @Bean
    public FastORM getSqlClient(DataSource dataSource, TransactionTemplate transactionTemplate) {
        return new FastORM(dataSource, transactionTemplate);
    }
}
