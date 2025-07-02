package com.cdyhrj.fast.orm.exception;

import com.cdyhrj.fast.orm.entity.Entity;

public class KeyValueRequiredException extends RuntimeException {
    public KeyValueRequiredException(Class<? extends Entity> clazz) {
        super("Key(@Id or @Name) value is required for update by entity, class: " + clazz.getName());
    }
}
