package com.cdyhrj.fastorm.config;

import com.cdyhrj.fastorm.pager.IPagerProvider;
import com.cdyhrj.fastorm.pager.impl.MySql8PagerProvider;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局配置
 *
 * @author 黄奇
 */
@Data
@Configuration
public class FastOrmConfig {
    @Value("${yhrj.fastORM.batchSize}")
    private Integer batchSize;

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
