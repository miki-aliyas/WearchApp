package com.example.wearchapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Greeting> call = apiService.getGreeting();

        call.enqueue(new Callback<Greeting>() {

            @Override
            public void onResponse(Call<Greeting> call, Response<Greeting> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Greeting greeting = response.body();
                    Log.d("MainActivitity", "Greeting: " + greeting.getContent());
                } else {
                    Log.e("MainActivity", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Greeting> call, Throwable t) {
                Log.e("MainActivity", "Response error: " + t.getMessage());
            }
        });
    }
}