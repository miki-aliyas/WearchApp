package com.example.wearchapp.ui.detail;
//クティビティの一部として再利用可能なUI部品を定義する
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wearchapp.R;
import com.example.wearchapp.ui.main.MainFragment;

public class DetailFragment extends Fragment {

    public static Fragment newInstance() {
        return new DetailFragment();
    }

    @Nullable   //  フラグメントのUIを作成するためにビューを呼び出す
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override   //  ビューの作成が完了した後にビューを呼び出す
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //画面の処理
    }
}
