package com.booking.common.resp;

/**
 * Created by shiju on 2017/10/16.
 */
public class PageResult extends Result{
    private int pageNo;
    private int pageSize;
    private int countSize = 0;
    private boolean hasNext = false;

    public int getCountSize() {
        return countSize;
    }

    public void setCountSize(int countSize) {
        this.countSize = countSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

}
