package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.config.FastOrmConfig;
import com.cdyhrj.fastorm.pager.IPagerProvider;
import com.cdyhrj.fastorm.pager.impl.MySql8PagerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
    public FastORM getFastORM(DataSource dataSource,
                              TransactionTemplate transactionTemplate,
                              FastOrmConfig fastOrmConfig,
                              IPagerProvider pagerProvider) {
        return new FastORM(dataSource, transactionTemplate, fastOrmConfig, pagerProvider);
    }

    /**
     * 分页提供， 默认使用MySql，如果需要定制，业务中使用自定义Bean
     *
     * @return 分页提供
     */
    @Bean
    @ConditionalOnMissingBean(IPagerProvider.class)
    public IPagerProvider pagerProvider() {
        return new MySql8PagerProvider();
    }
}
