package com.itszt.taotao.common;

import java.io.Serializable;

public class EasyUIAddContentBean implements Serializable {

    private int status;
    private String messager;

    public final static int OK = 200;
    public final static int NOK = 404;
    public final static int ERROR = 500;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessager() {
        return messager;
    }

    public void setMessager(String messager) {
        this.messager = messager;
    }
}
