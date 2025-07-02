package com.cdyhrj.fast.orm.exception;

public class OnlyOneIdAnnotationRequiredException extends RuntimeException {
    public OnlyOneIdAnnotationRequiredException(Class<?> clazz) {
        super("One and only one '@Id' is required for class: " + clazz);
    }
}
