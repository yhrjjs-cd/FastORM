package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.config.FastOrmConfig;
import com.cdyhrj.fastorm.enhance.EntityRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Entity Scan
 *
 * @author huangqi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({EntityRegistrar.class, FastORMDefiner.class, FastOrmConfig.class})
public @interface EnableFastORM {
    /**
     * 扫描类所在包
     *
     * @return 类
     */
    Class<?>[] scanPackageClasses() default {};
}
