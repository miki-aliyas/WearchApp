package com.example.wearchapp.data.model;

//カテゴリ情報を保持するクラス
public class Category {
    private long id;
    private String categoryName;
    private String imageName;

//　getter,setter
    public Category(long id, String categoryName, String imageName) {
        this.id = id;
        this.categoryName = categoryName;
        this.imageName = imageName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
