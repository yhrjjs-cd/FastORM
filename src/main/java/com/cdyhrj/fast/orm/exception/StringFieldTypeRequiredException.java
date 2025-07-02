package com.cdyhrj.fast.orm.exception;

public class StringFieldTypeRequiredException extends RuntimeException {
    public StringFieldTypeRequiredException(String field, Class<?> clazz) {
        super("'String' is required for '%s' of class '%s'".formatted(field, clazz));
    }
}
