package com.example.growthmaster.db;

import java.io.Serializable;

public class User implements Serializable {
    private String cid;
    private String msg;

    public User() {
    }

    public User(String cid, String msg) {
        this.cid = cid;
        this.msg = msg;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
