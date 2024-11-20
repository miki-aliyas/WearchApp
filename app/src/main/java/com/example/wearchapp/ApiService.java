package com.example.wearchapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/greeting")
    Call<Greeting> getGreeting();
}
