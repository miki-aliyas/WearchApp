package com.example.wearchapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wearchapp.R;
import com.example.wearchapp.ui.detail.DetailFragment;
import com.example.wearchapp.ui.top.TopActivity;
import com.example.wearchapp.ui.topbar.TopBarActivity;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private ViewPager2 viewPager;
    private final List<View> pointerList = new ArrayList<>();
    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ViewPagerの取得
        viewPager =view.findViewById(R.id.viewPager);

//        カルーセルポイントView取得
        pointerList.add(view.findViewById(R.id.pointer_first));
        pointerList.add(view.findViewById(R.id.pointer_second));
        pointerList.add(view.findViewById(R.id.pointer_third));
    }
}
