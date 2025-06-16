package com.cdyhrj.fastorm.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有增强的实体类Map
 *
 * @author huangqi
 */
@Slf4j
public class EnhancedEntityClassMap {
    private EnhancedEntityClassMap() {

    }

    private static final Map<String, Class<? extends Entity>> entityClassMap = new HashMap<>();

    public static void registerEntity(Class<? extends Entity> clazz) {
        entityClassMap.put(clazz.getSimpleName(), clazz);
    }

    public static Class<? extends Entity> getClassByName(String name) {
        return entityClassMap.get(name);
    }
}
