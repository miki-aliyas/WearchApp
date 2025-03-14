package com.example.wearchapp.ui.main;
//  ViewPager2 を使用してカルーセル表示を行い、自動スクロール機能を実装

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wearchapp.R;
import com.example.wearchapp.data.model.Category;
import com.example.wearchapp.data.model.ClothesItem;
import com.example.wearchapp.data.repository.CategoryRepository;
import com.example.wearchapp.data.repository.ClothesItemRepository;
import com.example.wearchapp.ui.main.adapter.CarouselAdapter;
import com.example.wearchapp.ui.main.adapter.RecommendationAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();   //  ログ出力用のタグ
    private static final long DISPLAY_TIME = 3000L; //  カルーセルの表示時間（ミリ秒）
    private MainViewModel viewModel;    //  MainViewModel のインスタンス
    private ViewPager2 viewPager;   //  カルーセルを表示する
    private final List<View> pointerList = new ArrayList<>();   //  カルーセルのポイント（ドット）ビューのリスト
    private CarouselAdapter carouselAdapter;    //  カルーセル表示を行うためのアダプタ
    private static final int CARD_WIDTH_DP = 140;   // カードの横幅（dp）
    private static final int CARD_HEIGHT_DP = 140;  // カードの縦幅（dp）
    private RecyclerView recyclerView;  //  カテゴリリストを表示する
    private RecommendationAdapter adapter;  //  カテゴリリストを表示するためのアダプタ
    private Runnable runnable;  //  自動スクロールを実行するためのオブジェクト
    private final Handler handler = new Handler();  //  Runnable の実行を管理するためのオブジェクト
    private int currentPage = 0;    //  現在のページ番号\
    private MainFragmentListener carouseListener; //  カルーセルアイテムがクリックされた際のリスナー

    //  カルーセルアイテムがクリックされた際のリスナー
    public interface MainFragmentListener {
        void onClickCategoryItem(ClothesItem clothesItem);
    }
    public static Fragment newInstance() { return new MainFragment();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            carouseListener = (MainFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement MainFragmentListener");
        }

    }
    //  フラグメントのビューを作成する
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    //  フラグメントが作成されたときに呼び出されるメソッド
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModelの設定
        settingViewModel();

        // ViewPagerの取得
        viewPager =view.findViewById(R.id.viewPager);

        // XMLレイアウトのIDからRecyclerViewの取得
        recyclerView = view.findViewById(R.id.recyclerView);

        // カルーセル設定
        carouselSettings(position -> {
            clearPointer();

        // ポインターの位置を設定
            int pointerPosition = position;
            pointerList.get(pointerPosition).setBackgroundResource(R.drawable.circle_on_shape);
        });

        // RecyclerView設定
        recyclerViewSettings();

        // カルーセルポイントView取得しpointerListに追加
        pointerList.add(view.findViewById(R.id.pointer_first));
        pointerList.add(view.findViewById(R.id.pointer_second));
        pointerList.add(view.findViewById(R.id.pointer_third));
        pointerList.add(view.findViewById(R.id.pointer_fourth));
        pointerList.add(view.findViewById(R.id.pointer_fifth));
    }
    //  フラグメントが再開されたときに呼び出されるメソッド
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadCategoryList();   //  カテゴリリストを取得するメソッドの呼び出し
        viewModel.loadClothesItemList();    //  服アイテムリストを取得するメソッドの呼び出し
        startAutoScroll();  //  自動スクロールを開始するメソッドの呼び出し
    }
    //  ポインターリセット
    private void clearPointer() {
        for (View pointer: pointerList) {
            pointer.setBackgroundResource(R.drawable.circle_off_shape);
        }
    }

    // ViewModelの設定
    private void settingViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //  カテゴリリストの監視設定
        viewModel.getCategoryList().observe(requireActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                //  カテゴリリストが取得できた場合、カルーセルアダプターに画像URLを設定
                for (int i = 0; i < categories.size(); i++) {
                    String imageUrl = carouselAdapter.getImageUrl(i);
                    if (!imageUrl.equals(categories.get(i).getImageName())) {
                        carouselAdapter.setImageUrl(categories.get(i).getImageName(), i);
                        carouselAdapter.notifyItemChanged(i);
                    }
                }
            }
        });

        //  服アイテムリストの監視設定
        viewModel.getClothesItemList().observe(requireActivity(), new Observer<List<ClothesItem>>() {
            @Override
            public void onChanged(List<ClothesItem> clothesItems) {
                for (int i = 0; i < clothesItems.size(); i++) {
                    ClothesItem clothesItem = adapter.getClothesItem(i);
                    if (clothesItem != clothesItems.get(i)) {
                        adapter.setClothesItem(clothesItems.get(i), i);
                        adapter.notifyItemChanged(i);
                    }
                }
            }
        });
    }

    // カルーセル設定
    private void carouselSettings(Consumer<Integer> consumer) {
        if (viewPager == null) {    // null である場合、警告ログを出力して終了
            Log.w(TAG,"viewPager is null");
            return;
        }
        carouselAdapter = new CarouselAdapter(new ArrayList<>());
        carouselAdapter.setListener(() -> {
            // ボタンをクリックした際の処理
            if (carouseListener != null) {
//                carouseListener.onClickCategoryItem();
            }
        });
        viewPager.setAdapter(carouselAdapter);  // ViewPagerにAdapterを設定
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                consumer.accept(position);
            }
        });
    }
    // LayoutManagerを作成し、RecyclerViewに設定
    private void recyclerViewSettings(){
        GridLayoutManager layoutManager = new GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
        );
        float dp = getResources().getDisplayMetrics().density;
        // Adapterの生成（インスタンス化）
        adapter = new RecommendationAdapter(new ArrayList<>(), (int)(CARD_WIDTH_DP * dp), (int)(CARD_HEIGHT_DP * dp));
        // RecyclerViewにAdapterを設定
        recyclerView.setAdapter(adapter);
        // RecyclerViewにLayoutManagerを設定
        recyclerView.setLayoutManager(layoutManager);
    }

    // 自動スクロール取得
    private void startAutoScroll() {
        runnable = new Runnable() { // runnableを定義
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
        handler.postDelayed(runnable, DISPLAY_TIME);    // handlerを使用してDISPLAY_TIME毎にページをスクロールする
    }
}