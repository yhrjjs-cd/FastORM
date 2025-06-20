package com.cdyhrj.fastorm.entity.enhance;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.config.FastOrmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Slf4j
@Configuration
class FastORMDefiner {
    public FastORMDefiner() {
        if (log.isInfoEnabled()) {
            log.info("Register Bean FastORM Complete.");
        }
    }

    @Bean
    public FastORM getFastORM(DataSource dataSource, TransactionTemplate transactionTemplate, FastOrmConfig fastOrmConfig) {
        return new FastORM(dataSource, transactionTemplate, fastOrmConfig);
    }
}
