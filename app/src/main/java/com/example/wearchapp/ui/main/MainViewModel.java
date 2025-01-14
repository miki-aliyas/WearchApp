package com.example.wearchapp.ui.main;
//挨拶データ とカテゴリリスト を保持し、LiveData を通じてUIにデータを提供
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearchapp.date.model.Category;
import com.example.wearchapp.date.model.Greeting;
import com.example.wearchapp.date.repository.GreetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Greeting> content = new MutableLiveData<>(null);  //  Greeting オブジェクトを保持。初期値はnull
    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>(new ArrayList<>());  //  オブジェクトのリストを保持。初期値は空のリスト
    private GreetingRepository greetingRepository;  //  挨拶データのリポジトリを管理

    public MainViewModel() {
        greetingRepository = new GreetingRepository();
    }

    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }
    public LiveData<Greeting> getContent() {
        return content;
    }
    public void loadGreeting() {
        if (greetingRepository == null) {   //  nullの場合はエラーログを出力して終了
            Log.e("error", "GreetingRepository in null");
            return;
        }
    //  メソッドを呼び出して、非同期で挨拶データを取得
        greetingRepository.getGreeting(new GreetingRepository.GreetingRepositoryCallback() {
            @Override   //  データ取得成功の場合はcontentに取得したGreetingオブジェクトを設定
            public void onSuccess(Greeting greeting) {
                Log.d("TAG", "onSuccess!");
                content.setValue(greeting);
            }

            @Override   //  データ取得失敗の場合はエラーログを出力
            public void onError(String error) {
                Log.e("TAG", "onError: " + error);
            }
        });
    }
}