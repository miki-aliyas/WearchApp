package com.example.wearchapp.data.api;

import com.example.wearchapp.data.model.Greeting;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/greeting")
    Call<Greeting> getGreeting();
}
