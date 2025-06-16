package com.cdyhrj.fastorm.entity;

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
            EntityProxy peerEntity = CACHED_ENTITY.get(entityClass);

            if (Objects.isNull(peerEntity)) {
                Class<?> clazz = Class.forName("%sProxy".formatted(entityClass.getName()));

                return (EntityProxy) clazz.getDeclaredConstructor().newInstance();
            }

            return peerEntity;
        } catch (Exception ex) {
            throw new RuntimeException("获取对等类出错.");
        }
    }
}
