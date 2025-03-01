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
import com.example.wearchapp.data.repository.GreetingRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Greeting> content = new MutableLiveData<>(null);  //  Greeting オブジェクトを保持。初期値はnull
    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>(new ArrayList<>());  //  オブジェクトのリストを保持。初期値は空のリスト
    private CategoryRepository categoryRepository;  //  挨拶データのリポジトリを管理

    public MainViewModel() {
        categoryRepository = new CategoryRepository();
    }

    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }
    public LiveData<Greeting> getContent() {
        return content;
    }

    public void loadCategoryList() {
        //  カテゴリリストを設定
        if (categoryRepository == null) {   //  nullの場合はエラーログを出力して終了
            Log.e("error", "GreetingRepository in null");
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
}