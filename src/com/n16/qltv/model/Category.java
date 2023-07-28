package com.n16.qltv.model;

public class Category {
    private int cateId;
    private String nameCate;

    public Category() { }
    public Category(String nameCate) {
        this.nameCate = nameCate;
    }

    public int getCateId() { return this.cateId; }
    public String getNameCate() { return this.nameCate; }
    public void setNameCate(String nameCate) { this.nameCate = nameCate; }
}
