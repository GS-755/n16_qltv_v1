package com.n16.qltv.model;

public class Category {
    private int cateId;
    private String nameCate;

    public Category() { }
    public Category(String nameCate) {
        this.nameCate = nameCate;
    }
    public Category(int cateId, String nameCate) {
        this.cateId = cateId;
        this.nameCate = nameCate;
    }


    public int getCateId() { return this.cateId; }
    public String getNameCate() { return this.nameCate; }
    public void setCateId(int cateId) { this.cateId = cateId; }
    public void setNameCate(String nameCate) { this.nameCate = nameCate; }

    @Override
    public String toString() {
        return "Category{" +
                "cateId=" + cateId +
                ", nameCate='" + nameCate + '\'' +
                '}';
    }
}
