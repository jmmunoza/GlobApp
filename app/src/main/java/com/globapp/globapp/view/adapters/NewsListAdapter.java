package com.globapp.globapp.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.listeners.OnNewsLikedListener;
import com.globapp.globapp.data.listeners.OnUserImageClickedListener;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.model.News;
import com.globapp.globapp.util.DateTextGetter;
import com.globapp.globapp.util.UserNameGetter;
import com.globapp.globapp.view.viewholders.NewsListViewHolder;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListViewHolder> {

    private final ArrayList<News>    newsList;
    private final LayoutInflater     inflater;
    private DataLoadedListener       dataLoadedListener;
    private int                      loadedNews;
    private final Context            context;

    // LISTENERS
    private final OnUserImageClickedListener onUserImageClickedListener;

    public NewsListAdapter(Context context, ArrayList<News> newsList, OnUserImageClickedListener onUserImageClickedListener){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.newsList = newsList;
        this.onUserImageClickedListener = onUserImageClickedListener;
    }

    @NonNull @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new NewsListViewHolder(
                    inflater.inflate(R.layout.fragment_news_item_dark, parent, false),
                    context,
                    onUserImageClickedListener);
        } else {
            return new NewsListViewHolder(
                    inflater.inflate(R.layout.fragment_news_item, parent, false),
                    context,
                    onUserImageClickedListener);
        }
    }

    @SuppressLint("CheckResult") @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.setNewsID(news.getNewsID());

        /*Glide.with(context)
                .asBitmap()
                .load(BitmapFactory.decodeResource(context.getResources(), R.drawable.cover))
                .override(400, 200)
                .transition(new BitmapTransitionOptions().crossFade())
                .fitCenter()
                .into(holder.newsPostImage);*/

        DataRepository.getUser(news.getNewsUserOwner(), userOwner -> {
            holder.setUserOwnerID(news.getNewsUserOwner());
            holder.newsPostContent.setText(news.getNewsContent());
            holder.newsLikeCounter.setText(String.valueOf(news.getNewsLikes()));
            holder.newsCommentCounter.setText(String.valueOf(news.getNewsComments()));
            holder.newsTime.setText(DateTextGetter.getDateText(news.getNewsDate()));
            if(news.getNewsImage() != null) holder.newsPostImage.setImageURI(news.getNewsImage());
            if(userOwner.getUserImage() != null)
                holder.newsUserImage.setImageURI(userOwner.getUserImage());
            else
                holder.newsUserImage.setImageResource(R.drawable.user);

            if(news.getNewsUserRecognized() != null){
                holder.newsRecognitionLayout.setVisibility(View.VISIBLE);
                DataRepository.getUser(news.getNewsUserRecognized(), userRecognized -> {
                    holder.setUserRecognizedID(news.getNewsUserRecognized());
                    holder.newsUsername.setText(UserNameGetter.getUserNameRecognition(userOwner, userRecognized));

                    if(userRecognized.getUserImage() != null)
                        holder.newsRecognitionUserImage.setImageURI(userRecognized.getUserImage());
                    else
                        holder.newsRecognitionUserImage.setImageResource(R.drawable.user);

                    loadedNews++;
                    isDataLoaded();
                });
            } else {
                holder.newsRecognitionLayout.setVisibility(View.GONE);
                holder.newsUsername.setText(UserNameGetter.getUserName(userOwner));
                loadedNews++;
                isDataLoaded();
            }
        });

        DataRepository.getIsLiked(news.getNewsID(), new OnNewsLikedListener() {
            @Override
            public void liked() {
                holder.newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
            }

            @Override
            public void disliked() {
                if (Preferences.getDarkMode())
                    holder.newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
                else
                    holder.newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        });
    }

    public void addDataLoadedListener(DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
    }

    public interface DataLoadedListener {
        void onDataLoaded();
    }

    private void isDataLoaded() {
        if(newsList.size() == loadedNews){
            if(dataLoadedListener != null)
                dataLoadedListener.onDataLoaded();
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
