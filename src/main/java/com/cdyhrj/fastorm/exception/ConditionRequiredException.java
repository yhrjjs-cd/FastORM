package com.cdyhrj.fastorm.exception;

public class ConditionRequiredException extends RuntimeException {
    public ConditionRequiredException() {
        super("Condition is required, id, name or where must not be null");
    }
}
