package com.cdyhrj.fastorm.exception;

public class TableAliasNameDuplicateException extends RuntimeException {
    public TableAliasNameDuplicateException(Class<?> entityClass) {
        super("duplicate entity alias name error: " + entityClass.getName());
    }
}
