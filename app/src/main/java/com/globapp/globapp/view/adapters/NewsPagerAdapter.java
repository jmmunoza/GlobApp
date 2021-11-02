package com.globapp.globapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.model.News;
import com.globapp.globapp.view.viewholders.NewsPagerViewHolder;

import java.util.ArrayList;

public class NewsPagerAdapter extends RecyclerView.Adapter<NewsPagerViewHolder> {

    private final ArrayList<News> newsPager;
    private final LayoutInflater inflater;

    public NewsPagerAdapter(Context context, ArrayList<News> newsPager) {
        this.inflater = LayoutInflater.from(context);
        this.newsPager = newsPager;
    }

    @NonNull @Override
    public NewsPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new NewsPagerViewHolder(inflater.inflate(R.layout.fragment_news_pager_dark, parent, false));
        } else {
            return new NewsPagerViewHolder(inflater.inflate(R.layout.fragment_news_pager, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPagerViewHolder holder, int position) {
        News news = newsPager.get(position);
        if(news.getNewsImage() != null) holder.pagerImage.setImageURI(news.getNewsImage());
        else holder.pagerImage.setImageResource(R.drawable.cover);
        holder.pagerDescription.setText(news.getNewsContent());
    }

    @Override
    public int getItemCount() {
        return newsPager.size();
    }
}
