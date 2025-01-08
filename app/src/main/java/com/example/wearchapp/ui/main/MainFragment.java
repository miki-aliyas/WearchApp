package com.example.wearchapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wearchapp.R;
import com.example.wearchapp.ui.detail.DetailFragment;
import com.example.wearchapp.ui.main.adapter.CarouselAdapter;
import com.example.wearchapp.ui.top.TopActivity;
import com.example.wearchapp.ui.topbar.TopBarActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final long DISPLAY_TIME = 3000L;
    private MainViewModel viewModel;
    private ViewPager2 viewPager;
    private final List<View> pointerList = new ArrayList<>();
    private CarouselAdapter carouselAdapter;
    private Runnable runnable;
    private final Handler handler = new Handler();
    private int currentPage = 0;

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
        // ViewModelの設定
        settingViewModel();
        // ViewPagerの取得
        viewPager =view.findViewById(R.id.viewPager);

        // カルーセルポイントView取得
        pointerList.add(view.findViewById(R.id.pointer_first));
        pointerList.add(view.findViewById(R.id.pointer_second));
        pointerList.add(view.findViewById(R.id.pointer_third));
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll();
    }
    // ViewModelの設定
    private void settingViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    //  LiveDateの監視設定

    }

    // カルーセル設定
    private void carouselSettings(Consumer<Integer> consumer) {
        if (viewPager == null) {
            Log.w(TAG,"viewPager is null");
            return;
        }
        carouselAdapter = new CarouselAdapter(new ArrayList<>());
        carouselAdapter.setListener(() ->{
            // TODO: 未実装
        });
        viewPager.setAdapter(carouselAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                consumer.accept(position);
            }
        });
    }
    // 自動スクロール取得
    private void startAutoScroll() {
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(this);
                if (viewPager.getAdapter() != null && currentPage >= viewPager.getAdapter().getItemCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, DISPLAY_TIME);
            }
        };
        currentPage++;
        handler.postDelayed(runnable, DISPLAY_TIME);
    }
}