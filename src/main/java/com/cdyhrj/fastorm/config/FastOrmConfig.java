package com.cdyhrj.fastorm.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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
}
