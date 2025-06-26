package com.cdyhrj.fastorm.entity.deletable.by_object;

import com.cdyhrj.fastorm.entity.EntityProxy;

import java.util.StringJoiner;

public class SqlHelper {
    public static String generateDeleteSqlTextById(EntityProxy entityProxy) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("DELETE FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(entityProxy.getIdFieldName())
                .add("= :id");

        return joiner.toString();
    }

    public static String generateDeleteSqlTextByName(EntityProxy entityProxy) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("DELETE FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(entityProxy.getNameFieldName())
                .add("= :name");

        return joiner.toString();
    }
}
