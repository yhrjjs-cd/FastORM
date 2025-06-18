package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.entity.enhance.EntityClassEnhancer;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * Entity
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public interface Entity {
    /**
     * 所有代理类缓存
     */
    Map<Class<?>, EntityProxy> CACHED_ENTITY = Maps.newConcurrentMap();

    /**
     * 获取实体代理对象
     *
     * @param entityClass 实体类
     * @return 对象
     */
    static EntityProxy getEntityProxy(Class<?> entityClass) {
        try {
            EntityProxy entityProxy = CACHED_ENTITY.get(entityClass);

            if (Objects.isNull(entityProxy)) {
                Class<?> clazz = Class.forName(EntityClassEnhancer.PROXY_CLASS_NAME_TEMPLATE.formatted(entityClass.getName()));

                return (EntityProxy) clazz.getDeclaredConstructor().newInstance();
            }

            return entityProxy;
        } catch (Exception ex) {
            throw new RuntimeException("获取对等类出错.");
        }
    }

    default void beforeInsert() {
    }

    default void afterInsert() {
    }

    default void beforeUpdate() {
    }

    default void afterUpdate() {

    }

    default void beforeDelete() {
    }

    default void afterDelete() {
    }
}
