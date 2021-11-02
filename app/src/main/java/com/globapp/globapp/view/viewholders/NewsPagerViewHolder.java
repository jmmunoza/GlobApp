package com.globapp.globapp.view.viewholders;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;

public class NewsPagerViewHolder extends RecyclerView.ViewHolder {

    public CardView pagerBackground;
    public ImageView pagerImage;
    public TextView pagerTitle;
    public TextView pagerDescription;

    @SuppressLint("ClickableViewAccessibility")
    public NewsPagerViewHolder(View itemView) {
        super(itemView);
        pagerBackground  = itemView.findViewById(R.id.news_pager_background);
        pagerImage       = itemView.findViewById(R.id.news_pager_image);
        pagerTitle       = itemView.findViewById(R.id.news_pager_title);
        pagerDescription = itemView.findViewById(R.id.news_pager_description);
        // WAIIIIIIIIIIIIIIIIIIIIIIIIIIITTTTTTT
            /*
            pagerBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).addFragmentUp(new FragmentOnNotification(newsPager.get(getAdapterPosition()).getNewsID()));
                }
            });

             */
    }
}
