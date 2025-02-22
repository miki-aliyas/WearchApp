package com.example.wearchapp.data.model;
//洋服情報を保持するクラス
public class ClothesItem {
    //　ID
    private long id;
    //　画像名
    private String imageName;

    //　洋服名(String)
    private String clothesName;
    //　カテゴリー名
    private String categoryName;
    //　サイズ(String)
    private String sizeName;
    //　着丈(int)
    private int length;
    //　肩幅(int)
    private int shoulderWidth;
    //　袖丈(int)
    private int sleeveLength;
    //　袖幅(int)
    private int sleeveWidth;
    //　首幅(int)
    private int neckWidth ;
    //　素材(String)
    private String material;

//　getter,setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClothesName() {
        return clothesName;
    }

    public void setImageName(String imageName) { this.imageName = imageName;}

    public String getImageName() {
        return imageName;
    }

    public void setClothesName(String clothesName) {
        this.clothesName = clothesName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getShoulderWidth() {
        return shoulderWidth;
    }

    public void setShoulderWidth(int shoulderWidth) {
        this.shoulderWidth = shoulderWidth;
    }

    public int getSleeveLength() {
        return sleeveLength;
    }

    public void setSleeveLength(int sleeveLength) {
        this.sleeveLength = sleeveLength;
    }

    public int getSleeveWidth() {
        return sleeveWidth;
    }

    public void setSleeveWidth(int sleeveWidth) {
        this.sleeveWidth = sleeveWidth;
    }

    public int getNeckWidth() {
        return neckWidth;
    }

    public void setNeckWidth(int neckWidth) {
        this.neckWidth = neckWidth;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
//　コンストラクタ
    public ClothesItem(long id, String imageName, String clothesName, String categoryName, String sizeName, int length, int shoulderWidth, int sleeveLength, int sleeveWidth, int neckWidth, String material) {
        this.id = id;
        this.imageName = imageName;
        this.clothesName = clothesName;
        this.categoryName = categoryName;
        this.sizeName = sizeName;
        this.length = length;
        this.shoulderWidth = shoulderWidth;
        this.sleeveLength = sleeveLength;
        this.sleeveWidth = sleeveWidth;
        this.neckWidth = neckWidth;
        this.material = material;
    }
}
