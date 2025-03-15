package com.example.wearchapp.ui.main.adapter;

import static com.example.wearchapp.data.Constants.IMAGE_PATH;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wearchapp.R;
import com.example.wearchapp.data.model.ClothesItem;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {
    private List<ClothesItem> dataList; //  服アイテムリスト
    private int cardWidth;  //  カードの横幅
    private int cardHeight; //  カードの縦幅
    public RecommendationAdapter(List<ClothesItem> dataList, int cardWidth, int cardHeight) {   //  コンストラクタ
        this.dataList = dataList;   //  服アイテムリストを初期化
        this.cardWidth = cardWidth; //  カードの横幅を初期化
        this.cardHeight = cardHeight; //  カードの縦幅を初期化
    }
    //  ViewHolder（Adapter内で使用するデータをまとめるクラス）
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;  //  カードの画像を表示するためのImageView
        CardView cardView;  //  カードを表示するためのCardView
        ProgressBar progressBar; // ロード中に表示するためのプログレスバー
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.recommendation_card);  //  カードのレイアウトを取得
            image = itemView.findViewById(R.id.recommendation_image);
            progressBar = itemView.findViewById(R.id.progress); //  カードの画像を取得

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

    // ViewHolderにデータをバインド、画像を表示後、クリックイベントを設定するメソッド
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClothesItem currentItem = dataList.get(position);
        String imageName = currentItem.getImageName();
        Context context = holder.itemView.getContext();
        String imageUrl = IMAGE_PATH + imageName;
        Log.d("RecommendationAdapter", "imageUrl=" + imageUrl);
        Glide.with(context)
             .load(imageUrl)
            .listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                    // 読込み失敗時の処理
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                    // 画像の読込みが完了
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            })
             .into(holder.image);
        holder.image.setOnClickListener(v -> {
            // TODO　画像タップ処理
        });
    }
    //  アダプタのアイテム数を返すメソッド
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // 指定位置の洋服アイテムを返すメソッド
    public ClothesItem getClothesItem(int position) {
        if (dataList.size() > position) {
            return dataList.get(position);
        }
        return null;
    }
    public void setClothesItem(ClothesItem item, int position) {
        if (dataList.size() > position) {
            dataList.set(position, item);
        } else {
            dataList.add(item);
        }
    }
}
