package com.example.growthmaster.db;

import java.io.Serializable;
import java.util.Date;

public class CommentForResource implements Serializable {

    private int id;

    private String content;

    private Date publishTime;

    private int userId;

    private int appreicateCount;

    public CommentForResource(){}

    public CommentForResource(String content, Date publishTime, int userId, int appreicateCount) {
        this.content = content;
        this.publishTime = publishTime;
        this.userId = userId;
        this.appreicateCount = appreicateCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAppreicateCount() {
        return appreicateCount;
    }

    public void setAppreicateCount(int appreicateCount) {
        this.appreicateCount = appreicateCount;
    }
}
