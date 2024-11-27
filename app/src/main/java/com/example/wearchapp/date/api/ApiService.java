package com.example.wearchapp.date.api;

import com.example.wearchapp.date.model.Greeting;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/greeting")
    Call<Greeting> getGreeting();
}
