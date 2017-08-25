package com.hentica.app.module.entity.index;

/**
 * Created by YangChen on 2017/4/10 11:50.
 * E-mail:656762935@qq.com
 */

public class IndexPageData {

    /**
     * total : 2
     * pageNumber : 1
     * pageSize : 2
     */

    private int total;
    private int pageNumber;
    private int pageSize;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
