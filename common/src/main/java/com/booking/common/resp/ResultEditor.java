package com.booking.common.resp;

import com.opdar.platform.core.base.Editor;

/**
 * Created by shiju on 2017/10/16.
 */
public class ResultEditor implements Editor<Object,Result> {
    @Override
    public Result editor(Object o) {
        Result result = null;
        if(o instanceof Page){
            PageResult pageResult = new PageResult();
            pageResult.setPageNo(((Page) o).getPageNo());
            pageResult.setPageSize(((Page) o).getPageSize());
            pageResult.setCountSize(((Page) o).getCountSize());
            pageResult.setHasNext(((Page) o).isHasNext());
            pageResult.setData(((Page) o).getResult());
            result = pageResult;
        }else{
            result = new Result();
            result.setData(o);
        }
        result.setCode(0);
        result.setMsg("操作成功");
        return result;
    }
}
