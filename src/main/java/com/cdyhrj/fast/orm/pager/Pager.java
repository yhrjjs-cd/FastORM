package com.cdyhrj.fast.orm.pager;

import lombok.Data;
import org.springframework.util.Assert;

/**
 * 分页信息
 *
 * @author huangqi
 */
@Data
public class Pager {
    /**
     * 分页
     *
     * @param pageIndex 页码
     * @param pageSize  每页记录数
     * @return 分页信息
     */
    public static Pager of(int pageIndex, int pageSize) {
        return new Pager().setPageIndex(pageIndex).setPageSize(pageSize);
    }

    /**
     * 默认获取页面大小
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 默认分页
     */
    public static final Pager DEFAULT = of(1, DEFAULT_PAGE_SIZE);

    /**
     * 页码
     */
    private int pageIndex;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 页数
     */
    private int pageCount;

    /**
     * 记录数
     */
    private int recordCount;

    public Pager resetPageCount() {
        pageCount = -1;

        return this;
    }


    public Pager setPageIndex(int pageIndex) {
        Assert.isTrue(pageIndex >= 1, "PageIndex must >= 1");

        this.pageIndex = pageIndex;
        return this;
    }

    public Pager setPageSize(int pageSize) {
        this.pageSize = (pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE);

        return resetPageCount();
    }

    public int getPageCount() {
        if (pageCount < 0) {
            pageCount = (int) Math.ceil((double) recordCount / pageSize);
        }

        return pageCount;
    }

    public Pager setRecordCount(int recordCount) {
        this.recordCount = Math.max(recordCount, 0);
        this.pageCount = (int) Math.ceil((double) recordCount / pageSize);

        return this;
    }

    public int getOffset() {
        return pageSize * (pageIndex - 1);
    }

    /**
     * PageIndex 增加 1， 在遍历的情况下使用
     */
    public void incPageIndex() {
        this.pageIndex++;
    }

    @Override
    public String toString() {
        return String.format("size: %d, total: %d, page: %d/%d", pageSize, recordCount, pageIndex, this.getPageCount());
    }

    public boolean isFirst() {
        return pageIndex == 1;
    }

    public boolean isLast() {
        if (pageCount == 0) {
            return true;
        }
        return pageIndex == pageCount;
    }

    public boolean hasNext() {
        return !isLast();
    }

    public boolean hasPrevious() {
        return !isFirst();
    }
}
