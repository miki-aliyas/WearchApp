package com.example.wearchapp.ui.detail;
//クティビティの一部として再利用可能なUI部品を定義する
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wearchapp.R;
import com.example.wearchapp.ui.main.MainFragment;
import com.example.wearchapp.ui.main.adapter.CarouselAdapter;
import com.example.wearchapp.ui.main.adapter.RecommendationAdapter;
import com.example.wearchapp.ui.topbar.TopBarActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailFragment extends Fragment {

    private ViewPager2 viewPager;   //  カルーセルを表示する
    private final List<View> pointerList = new ArrayList<>();   //  カルーセルのポイント（ドット）ビューのリスト
    private CarouselAdapter carouselAdapter;    //  カルーセル表示を行うためのアダプタ
    private static final int CARD_WIDTH_DP = 140;   // カードの横幅（dp）
    private static final int CARD_HEIGHT_DP = 140;  // カードの縦幅（dp）
    private static final List<Integer> INT_DATA_LIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);   // カテゴリリストのデータ
    private RecyclerView recyclerView;  //  カテゴリリストを表示する
    private RecommendationAdapter adapter;  //  カテゴリリストを表示するためのアダプタ
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

        // ViewPagerの取得
        viewPager =view.findViewById(R.id.viewPager);

        // XMLレイアウトのIDからRecyclerViewの取得
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void recyclerViewSettings(){    //  RecyclerViewの設定
        GridLayoutManager layoutManager = new GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
        );
        float dp = getResources().getDisplayMetrics().density;
        // Adapterの生成（インスタンス化）
        adapter = new RecommendationAdapter(INT_DATA_LIST, (int)(CARD_WIDTH_DP * dp), (int)(CARD_HEIGHT_DP * dp));
        // RecyclerViewにAdapterを設定
        recyclerView.setAdapter(adapter);
        // RecyclerViewにLayoutManagerを設定
        recyclerView.setLayoutManager(layoutManager);
    }
}
