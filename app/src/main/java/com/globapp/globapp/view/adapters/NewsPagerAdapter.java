package com.globapp.globapp.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.R;
import com.globapp.globapp.model.News;

import java.util.ArrayList;


public class NewsPagerAdapter extends RecyclerView.Adapter<NewsPagerAdapter.ViewHolder> {

    private final ArrayList<News> newsPager;
    private final LayoutInflater inflater;

    public NewsPagerAdapter(Context context, ArrayList<News> newsPager) {
        this.inflater = LayoutInflater.from(context);
        this.newsPager = newsPager;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new ViewHolder(inflater.inflate(R.layout.fragment_news_pager_dark, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.fragment_news_pager, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsPager.get(position);

        if(news.getNewsImage() != null) holder.pagerImage.setImageURI(news.getNewsImage());
        else holder.pagerImage.setImageResource(R.drawable.cover);
        holder.pagerDescription.setText(news.getNewsContent());

    }

    @Override
    public int getItemCount() {
        return newsPager.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView pagerBackground;
        ImageView pagerImage;
        TextView pagerTitle;
        TextView pagerDescription;

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(View itemView) {
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
}
