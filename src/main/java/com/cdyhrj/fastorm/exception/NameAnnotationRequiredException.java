package com.cdyhrj.fastorm.exception;

public class NameAnnotationRequiredException extends RuntimeException {
    public NameAnnotationRequiredException(Class<?> clazz) {
        super("'@Name' is required for class: " + clazz);
    }
}
