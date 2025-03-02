package com.example.wearchapp.data.repository;

import com.example.wearchapp.data.api.ApiService;
import com.example.wearchapp.data.api.RetrofitClient;
import com.example.wearchapp.data.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//  Retrofitを使用してAPIからカテゴリ情報を取得し、その結果をコールバックを通じて呼び出し元に通知する仕組みを提供する
public class CategoryRepository {
//    APIリクエストを実行するためのインターフェース
    private ApiService apiService;

    public CategoryRepository(){
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    //  カテゴリ情報を取得するためのAPIリクエストを実行
    public void getCategoryListAll(CategoryRepositoryCallback callback){
       Call<List<Category>> call = apiService.getCategoryListAll();

    //   非同期でリクエストを実行しレスポンスを処理
    call.enqueue(new Callback<List<Category>>() {
        //   レスポンスが成功した場合はコールバックのonSuccessメソッドを呼び出す
        @Override
        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
            //  レスポンスが成功した場合はコールバックのonSuccessメソッドを呼び出す
            if (response.isSuccessful()) {
                callback.onSuccess(response.body());
            } else {
                callback.onError(("response not success"));
            }
        }
    //  レスポンスが失敗した場合はコールバックのonErrorメソッドを呼び出しエラーメッセージを渡す
        @Override
        public void onFailure(Call<List<Category>> call, Throwable t) {
            callback.onError("APIリクエストが失敗しました。" + t.getMessage());
        }
    });
    }

    //  リポジトリがデータ取得成功したときにコールバックで呼び出されるインターフェース
    public interface CategoryRepositoryCallback {
        void onSuccess(List<Category> categoryList);
        void onError(String error);
    }
}
