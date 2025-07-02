package com.cdyhrj.fast.orm.entity.queryablex.join;

public class StringOnItem implements OnItem {
    public static StringOnItem of(String itemString) {
        StringOnItem item = new StringOnItem();
        item.itemString = itemString;

        return item;
    }

    private String itemString;

    @Override
    public String toSql() {
        return itemString;
    }
}
