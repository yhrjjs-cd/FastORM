package com.cdyhrj.fastorm.entity.queryable.join;

public class StringOnItem implements OnItem {
    public static StringOnItem of(String itemString) {
        StringOnItem item = new StringOnItem();
        item.itemString = itemString;

        return item;
    }

    private String itemString;

    @Override
    public String toQuerySql() {
        return itemString;
    }
}
