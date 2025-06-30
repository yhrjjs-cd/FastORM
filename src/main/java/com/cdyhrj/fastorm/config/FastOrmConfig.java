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
    @Value("${yhrj.fastORM.batchSize:200}")
    private Integer batchSize;
}
