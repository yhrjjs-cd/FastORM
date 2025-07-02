package com.cdyhrj.fast.orm.entity.queryablex.join;

public class OnItem2 implements OnItem {
    public static OnItem2 of(
            String leftAlias,
            String leftField,
            String rightAlias,
            String rightField) {
        OnItem2 item = new OnItem2();
        item.leftAlias = leftAlias;
        item.leftField = leftField;
        item.rightAlias = rightAlias;
        item.rightField = rightField;

        return item;
    }


    private String leftAlias;
    private String leftField;
    private String rightAlias;
    private String rightField;

    @Override
    public String toSql() {
        return "%s.%s = %s.%s".formatted(leftAlias, leftField, rightAlias, rightField);
    }
}
