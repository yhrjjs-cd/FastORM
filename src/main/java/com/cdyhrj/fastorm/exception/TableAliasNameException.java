package com.cdyhrj.fastorm.exception;

public class TableAliasNameException extends RuntimeException {
    public TableAliasNameException() {
        super("Entity Alias Name Error");
    }
}
