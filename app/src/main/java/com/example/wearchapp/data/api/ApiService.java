package com.example.wearchapp.data.api;

import com.example.wearchapp.data.model.Category;
import com.example.wearchapp.data.model.ClothesItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/api/v1/category/list")
    Call<List<Category>> getCategoryListAll();

    @GET("/api/v1/clothesitem/list")
    Call<List<ClothesItem>> getClothesItemListAll();

}
