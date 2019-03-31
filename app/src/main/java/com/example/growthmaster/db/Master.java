package com.example.growthmaster.db;

import org.litepal.crud.LitePalSupport;

public class Master extends LitePalSupport {

    private int id;

    private String name;

    private String title;

    private String field;

    private String price;

    private int imageId;

    public Master(String name, String title, String field, String price, int imageId) {
        this.name = name;
        this.title = title;
        this.field = field;
        this.price = price;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
