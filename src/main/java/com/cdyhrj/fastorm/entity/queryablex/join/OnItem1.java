package com.cdyhrj.fastorm.entity.queryablex.join;

public class OnItem1 implements OnItem {
    public static OnItem1 of(String alias,
                             String field,
                             Object value) {
        OnItem1 item = new OnItem1();

        item.alias = alias;
        item.field = field;
        item.value = value;

        return item;
    }

    private String alias;
    private String field;
    private Object value;

    @Override
    public String toSql() {

        if (value instanceof String) {
            return "%s.%s = %s".formatted(alias, field, this.value);
        } else {
            return "%s.%s = %s".formatted(alias, field, this.value);
        }

    }
}
