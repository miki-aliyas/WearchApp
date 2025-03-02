package com.example.wearchapp.data.repository;

import com.example.wearchapp.data.api.ApiService;
import com.example.wearchapp.data.api.RetrofitClient;
import com.example.wearchapp.data.model.ClothesItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//  Retrofitを使用してAPIから服アイテム情報を取得し、その結果をコールバックを通じて呼び出し元に通知する仕組みを提供する
public class ClothesItemRepository {
    private ApiService apiService;
//    APIリクエストを実行するためのインターフェース
    public ClothesItemRepository(){
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    // カテゴリ情報を取得するためのAPIリクエストを実行
    public void getClothesItemListAll(ClothesItemRepository.ClothesItemRepositoryCallback callback){
        Call<List<ClothesItem>> call = apiService.getClothesItemListAll();

        //   非同期でリクエストを実行しレスポンスを処理
        call.enqueue(new Callback<List<ClothesItem>>() {
            //   レスポンスが成功した場合はコールバックのonSuccessメソッドを呼び出す
            @Override
            public void onResponse(Call<List<ClothesItem>> call, Response<List<ClothesItem>> response) {
                //  レスポンスが成功した場合はコールバックのonSuccessメソッドを呼び出す
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(("response not success"));
                }
            }
            //  レスポンスが失敗した場合はコールバックのonErrorメソッドを呼び出しエラーメッセージを渡す
            @Override
            public void onFailure(Call<List<ClothesItem>> call, Throwable t) {
                callback.onError("APIリクエストが失敗しました。" + t.getMessage());
            }
        });
    }

    //  リポジトリがデータ取得成功したときにコールバックで呼び出されるインターフェース
    public interface ClothesItemRepositoryCallback {
        void onSuccess(List<ClothesItem> categoryList);
        void onError(String error);
    }
}
