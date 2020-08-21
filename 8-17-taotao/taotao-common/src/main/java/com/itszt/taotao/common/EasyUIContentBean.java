package com.itszt.taotao.common;

import java.io.Serializable;

public class EasyUIContentBean implements Serializable {

    private String target;
    private String id;
    private int status;

    public static final int OK=200;
    public static final int NOK=400;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
