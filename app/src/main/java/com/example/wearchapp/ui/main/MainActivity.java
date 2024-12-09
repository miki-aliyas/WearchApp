package com.example.wearchapp.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wearchapp.R;
import com.example.wearchapp.date.api.ApiService;
import com.example.wearchapp.date.api.RetrofitClient;
import com.example.wearchapp.date.model.Greeting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

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

        settingViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.loadGreeting();
        }
    }

    private void settingViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getContent().observe(this, new Observer<Greeting>() {
            @Override
            public void onChanged(Greeting greeting) {
                if (greeting == null) return;
                Log.d("onChanged", "greeting: " +greeting.getContent());
            }
        });
    }
}