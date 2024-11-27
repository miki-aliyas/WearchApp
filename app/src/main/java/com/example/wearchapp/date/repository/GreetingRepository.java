package com.example.wearchapp.date.repository;

import android.util.Log;

import com.example.wearchapp.date.api.ApiService;
import com.example.wearchapp.date.api.RetrofitClient;
import com.example.wearchapp.date.model.Greeting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GreetingRepository {

    private ApiService apiService;

    public GreetingRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void getGreeting(GreetingRepositoryCallback callback) {
        Call<Greeting> call = apiService.getGreeting();

        call.enqueue(new Callback<Greeting>() {

            @Override
            public void onResponse(Call<Greeting> call, Response<Greeting> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("response not success");
                }
            }

            @Override
            public void onFailure(Call<Greeting> call, Throwable t) {
                callback.onError("APIリクエストが失敗しました。" + t.getMessage());
            }
        });
    }
    public interface GreetingRepositoryCallback {
        void onSuccess(Greeting greeting);
        void onError(String error);
    }
}
