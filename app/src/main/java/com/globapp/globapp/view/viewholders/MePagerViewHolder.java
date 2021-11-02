package com.globapp.globapp.view.viewholders;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;

public class MePagerViewHolder extends RecyclerView.ViewHolder {

    public CardView pagerBackground;
    public ImageView pagerImage;
    public TextView pagerTitle;

    @SuppressLint("ClickableViewAccessibility")
    public MePagerViewHolder(View itemView) {
        super(itemView);
        pagerBackground  = itemView.findViewById(R.id.me_recognition_background);
        pagerImage       = itemView.findViewById(R.id.me_recognition_image);
        pagerTitle       = itemView.findViewById(R.id.me_recognition_title);
    }
}