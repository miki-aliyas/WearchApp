package com.example.wearchapp.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wearchapp.R;
import com.example.wearchapp.data.model.ClothesItem;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {
    private List<ClothesItem> dataList; //  カテゴリリストのデータ
    private int cardWidth;  //  カードの横幅
    private int cardHeight; //  カードの縦幅
    public RecommendationAdapter(List<ClothesItem> dataList, int cardWidth, int cardHeight) {   //  コンストラクタ
        this.dataList = dataList;   //  カテゴリリストのデータを初期化
        this.cardWidth = cardWidth; //  カードの横幅を初期化
        this.cardHeight = cardHeight; //  カードの縦幅を初期化
    }
    //  ViewHolder（Adapter内で使用するデータをまとめるクラス）
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;  //  カードの画像を表示するためのImageView
        CardView cardView;  //  カードを表示するためのCardView
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.recommendation_card);  //  カードのレイアウトを取得
            image = itemView.findViewById(R.id.recommendation_image);    //  カードの画像を取得

            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();  // カードのレイアウトを取得
            layoutParams.width = cardWidth;     // カードの横幅を設定
            layoutParams.height = cardHeight;   // カードの縦幅を設定
            cardView.setLayoutParams(layoutParams); // カードのレイアウトを設定
        }
    }

    //  ViewHolderを生成する処理
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    //  カテゴリリストのデータを取得
       ClothesItem currentItem = dataList.get(position);
    //　画像名からリソースIDを取得
       Context context = holder.itemView.getContext();
         int resId = context.getResources().getIdentifier(currentItem.getImageName(), "drawable", context.getPackageName());
    // 画像を設定
        Glide.with(context).load(resId).into(holder.image);
    }
    //  リストに表示するアイテム数を返すメソッド
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
