package com.example.wearchapp.ui.main;
//挨拶データ とカテゴリリスト を保持し、LiveData を通じてUIにデータを提供
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearchapp.data.model.Category;
import com.example.wearchapp.data.model.ClothesItem;
import com.example.wearchapp.data.model.Greeting;
import com.example.wearchapp.data.repository.CategoryRepository;
import com.example.wearchapp.data.repository.ClothesItemRepository;
import com.example.wearchapp.data.repository.GreetingRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>(new ArrayList<>());  //  カテゴリリストを保持。初期値は空リスト
    private final MutableLiveData<List<ClothesItem>> clothesItemList = new MutableLiveData<>(new ArrayList<>());
    private CategoryRepository categoryRepository;  //  カテゴリリストを取得するためのリポジトリ
    private ClothesItemRepository clothesItemRepository;  //  服アイテム情報を取得するためのリポジトリ

    public MainViewModel() {
        categoryRepository = new CategoryRepository();
        clothesItemRepository = new ClothesItemRepository();
    }

    //  カテゴリリストを取得するためのAPIリクエストを実行
    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }
    //  服アイテムリストを取得するためのAPIリクエストを実行
    public LiveData<List<ClothesItem>> getClothesItemList() {return clothesItemList;}

    public void loadCategoryList() {
        if (categoryRepository == null) {   //  nullの場合はエラーログを出力して終了
            Log.e("error", "CategoryRepository in null");
            return;
        }
        categoryRepository.getCategoryListAll(new CategoryRepository.CategoryRepositoryCallback() {
            @Override
            public void onSuccess(List<Category> categories) {
                Log.d("success", "onSuccess=" + categories.get(0).getImageName());
                categoryList.setValue(categories);
            }
            @Override
            public void onError(String error) {
                Log.e("error",error);
            }
        });
    }
    public void loadClothesItemList() {
        if (clothesItemRepository == null) {   //  nullの場合はエラーログを出力して終了
            Log.e("error", "ClothesItemRepository in null");
            return;
        }
        clothesItemRepository.getClothesItemListAll(new ClothesItemRepository.ClothesItemRepositoryCallback() {
            @Override
            public void onSuccess(List<ClothesItem> clothesItems) {
                Log.d("success", "onSuccess=" + clothesItems.get(0).getImageName());
                clothesItemList.setValue(clothesItems);
            }
            @Override
            public void onError(String error) {
                Log.e("error",error);
            }
        });
    }
}