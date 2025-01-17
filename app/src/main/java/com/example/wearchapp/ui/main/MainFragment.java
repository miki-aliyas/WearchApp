package com.example.wearchapp.ui.main;
//  ViewPager2 を使用してカルーセル表示を行い、自動スクロール機能を実装

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wearchapp.R;
import com.example.wearchapp.date.model.Category;
import com.example.wearchapp.ui.detail.DetailFragment;
import com.example.wearchapp.ui.main.adapter.CarouselAdapter;
import com.example.wearchapp.ui.top.TopActivity;
import com.example.wearchapp.ui.topbar.TopBarActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();   //  ログ出力用のタグ
    private static final long DISPLAY_TIME = 3000L; //  カルーセルの表示時間（ミリ秒）
    private MainViewModel viewModel;    //  MainViewModel のインスタンス
    private ViewPager2 viewPager;   //  カルーセルを表示する
    private RecyclerView recyclerView;  //  カテゴリリストを表示する
    private final List<View> pointerList = new ArrayList<>();   //  カルーセルのポイント（ドット）ビューのリスト
    private CarouselAdapter carouselAdapter;    //  カルーセル表示を行うためのアダプタ
    private Runnable runnable;  //  自動スクロールを実行するためのオブジェクト
    private final Handler handler = new Handler();  //  Runnable の実行を管理するためのオブジェクト
    private int currentPage = 0;    //  現在のページを保持するための変数

    public static Fragment newInstance() {
        return new MainFragment();
    }
    //  フラグメントのビューを作成する
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    //  フラグメントのビューを作成後に呼び出されるメソッド
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModelの設定
        settingViewModel();

        // ViewPagerの取得
        viewPager =view.findViewById(R.id.viewPager);

        // RecyclerViewの取得
        recyclerView = view.findViewById(R.id.recyclerView);

        // カルーセル設定
        carouselSettings(position -> {
            clearPointer();
        // ポインターの位置を設定(画像が5つの場合限定)
            int pointerPosition = 0;
            if (0 <= position && position <=1 ) {

            }else if (3 <= position && position <= 4) {
                pointerPosition = 2;
            }else {
                pointerPosition = 1;
            }
            pointerList.get(pointerPosition).setBackgroundResource(R.drawable.circle_on_shape);
        });

        // カルーセルポイントView取得しpointerListに追加
        pointerList.add(view.findViewById(R.id.pointer_first));
        pointerList.add(view.findViewById(R.id.pointer_second));
        pointerList.add(view.findViewById(R.id.pointer_third));
    }
    //  フラグメントが再開されたときに呼び出されるメソッド
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadCategoryList();   //  カテゴリリストを取得するメソッドの呼び出し
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
        //  LiveDateの監視設定
        viewModel.getCategoryList().observe(requireActivity(), new Observer<List<Category>>() {

            @Override
            public void onChanged(List<Category> categories) {
                //  カテゴリリストが取得できた場合、カルーセルアダプターに画像URLを設定
                for (int i = 0; i < categories.size(); i++) {
                    String imageUrl = carouselAdapter.getImageUrl(i);
                    if (!imageUrl.equals(categories.get(i).getPath())) {
                        carouselAdapter.setImageUrl(categories.get(i).getPath(), i);
                        carouselAdapter.notifyItemChanged(i);
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

        });
        viewPager.setAdapter(carouselAdapter);  // アダプタをセットしページ変更コールバックを登録
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