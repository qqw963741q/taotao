package com.itszt.taotao.common;


import java.io.Serializable;
import java.util.List;

public class EasyUIPageDatasBean<T> implements Serializable {

    //总数,
    private Long total;

    //当前数据对象集合
    private List<T> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
