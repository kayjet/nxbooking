package com.booking.common.resp;

/**
 * Created by shiju on 2017/12/12.
 */
public class Page<T> {
    private int pageSize = 0;
    private int pageNo = 0;
    private int countSize = 0;
    private boolean hasNext = false;
    private T result;

    public Page(int pageSize, int pageNo, int countSize) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.countSize = countSize;
        this.hasNext = this.countSize - pageSize * pageNo > 0;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getCountSize() {
        return countSize;
    }

    public void setCountSize(int countSize) {
        this.countSize = countSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public T getResult() {
        return result;
    }

    public Page<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
