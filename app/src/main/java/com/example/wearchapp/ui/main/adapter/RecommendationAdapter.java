package com.example.wearchapp.ui.main.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearchapp.R;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder> {
    @NonNull
    @Override
    public RecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class RecommendationViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public RecommendationViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recommendation_image);
        }
    }
}
