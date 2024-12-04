package com.example.wearchapp.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearchapp.date.model.Greeting;
import com.example.wearchapp.date.repository.GreetingRepository;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Greeting> content = new MutableLiveData<>(null);
    private GreetingRepository greetingRepository;

    public MainViewModel() {
        greetingRepository = new GreetingRepository();
    }
    public LiveData<Greeting> getContent() {
        return content;
    }
    public void loadGreeting() {
        if (greetingRepository == null) {
            Log.e("error", "GreetingRepository in null");
            return;
        }

        greetingRepository.getGreeting(new GreetingRepository.GreetingRepositoryCallback() {
            @Override
            public void onSuccess(Greeting greeting) {
                Log.d("TAG", "onSuccess!");
                content.setValue(greeting);
            }

            @Override
            public void onError(String error) {
                Log.e("TAG", "onError: " + error);
            }
        });
    }
}
