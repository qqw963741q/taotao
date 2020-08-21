package com.itszt.taotao.common;

import java.io.Serializable;

public class EasyUITreeBean implements Serializable {

    private long id;

    //默认不给值是一个父节点
    private String state=STATE_CLOSED;

    private String text;

    //父节点
    public static final String STATE_CLOSED="closed";

    //最终子节点
    public static final String STATE_OPEN="open";


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
