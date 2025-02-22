package com.example.wearchapp.data.repository;
//  RetrofitをしとうしてAPIから挨拶データを取得しその結果をコールバックを通じて呼び出し元に通知する仕組みを提供

import com.example.wearchapp.data.api.ApiService;
import com.example.wearchapp.data.api.RetrofitClient;
import com.example.wearchapp.data.model.Greeting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GreetingRepository {

    private ApiService apiService;  //  APIリクエストを実行するためのインターフェース

    public GreetingRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }
//  挨拶データを取得するためのAPIリクエストを実行
    public void getGreeting(GreetingRepositoryCallback callback) {
        Call<Greeting> call = apiService.getGreeting(); // APIリクエストを作成

        call.enqueue(new Callback<Greeting>() { // 非同期でリクエストを実行しレスポンスを処理

            @Override   // 成功の場合はコールバックのonSuccessメソッドを呼び出す
            public void onResponse(Call<Greeting> call, Response<Greeting> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("response not success");
                }
            }

            @Override   // 失敗の場合はコールバックのonErrorメソッドを呼び出しエラーメッセージを渡す
            public void onFailure(Call<Greeting> call, Throwable t) {
                callback.onError("APIリクエストが失敗しました。" + t.getMessage());
            }
        });
    }
    public interface GreetingRepositoryCallback {   // APIリクエストの結果を処理する
        void onSuccess(Greeting greeting);  // リクエストが成功した時に呼び出される
        void onError(String error); // リクエスト失敗した時に呼び出される
    }
}