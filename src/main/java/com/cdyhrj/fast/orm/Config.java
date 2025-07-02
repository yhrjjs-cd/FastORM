package com.cdyhrj.fast.orm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableFastORM(scanPackageClasses = {
        Config.class
})
public class Config {
    public Config() {
        log.info("init fastorm config");
    }
}
