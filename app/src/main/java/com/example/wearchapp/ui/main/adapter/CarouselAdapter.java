package com.example.wearchapp.ui.main.adapter;
//  RecyclerViewを使用してカルーセル表示する
import static com.example.wearchapp.data.Constants.IMAGE_PATH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wearchapp.R;

import java.util.List;
public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<String> imageUrls; // 画像のURLリスト
    private CarouselAdapterListener listener;  // クリックイベントのリスナー
    // クリックイベントをリスンするためのインターフェースを定義
    public interface CarouselAdapterListener {
        void onClickCarouselItem();
    }
    // コンストラクター: 画像URLのリストの初期化
    public CarouselAdapter(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    // RecyclerViewのViewHolderを作成するメソッド
    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel,parent,false);
        return new CarouselViewHolder(view);
    }

    // ViewHolderにデータをバインド、画像を表示後、クリックイベントを設定するメソッド
    @Override
    public void onBindViewHolder(@NonNull CarouselAdapter.CarouselViewHolder holder, int position) {
        String imageName = imageUrls.get(position); // 現在の画像名を取得
        Context context = holder.itemView.getContext(); // コンテキストを取得categoryList
        String imageUrl = IMAGE_PATH + imageName;
        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageView);    // 画像をImageViewにセット
        holder.imageView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClickCarouselItem(); // クリックイベントをリスナーに伝える
            }
        });
    }
    // アダプタのアイテム数を返すメソッド
    @Override
    public int getItemCount() {
        return imageUrls.size();
    }
    // 指定位置の画像URLを返すメソッド
    public String getImageUrl(int position) {
        if (imageUrls.size() > position) {
            return imageUrls.get(position);
        }
    //　取得できなかった場合は空文字を返す
        return "";
    }
    // 指定位置に画像URLを設定するメソッド
    public void setImageUrl(String imageUrl, int position) {
        if (imageUrls.size() > position) {
            imageUrls.set(position, imageUrl);
        } else {
            imageUrls.add(imageUrl);
        }
    }
    // クリックイベントのリスナーを設定するメソッド
    public void setListener(CarouselAdapterListener listener) {
        this.listener = listener;
    }
    //  画像を表示するためのビューを保持
    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}