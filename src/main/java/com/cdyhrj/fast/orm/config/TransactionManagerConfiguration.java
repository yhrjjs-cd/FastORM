package com.cdyhrj.fast.orm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * (iTek-china 2021)
 * 事务配置管理器
 *
 * @author <a href="huangqi@itek-china.com">黄奇</a>
 * @version 7.0
 * <pre>
 *   2021-10-12 * 黄奇创建
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
public class TransactionManagerConfiguration {
    private final DataSource dataSource;

    /**
     * 获取事务管理器
     *
     * @return 事务管理器
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
