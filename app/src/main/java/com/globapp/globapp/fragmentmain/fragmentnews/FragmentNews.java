package com.globapp.globapp.fragmentmain.fragmentnews;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.classes.Notification;
import com.globapp.globapp.classes.Recognition;
import com.globapp.globapp.classes.User;

import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Sort;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class FragmentNews extends Fragment {
    public RecyclerView       newsList;
    public NewsListAdapter    newsListAdapter;
    public RecyclerView       newsPager;
    public NewsPagerAdapter   newsPagerAdapter;
    public SwipeRefreshLayout newsRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(((MainActivity)getContext()).isDarkMode){
            return inflater.inflate(R.layout.fragment_news_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_news, null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNews();
        loadComponents();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadComponents(){
        newsRefresh = getView().findViewById(R.id.news_refresh);
        newsRefresh.setOnRefreshListener(() -> ((MainActivity)getContext()).runOnUiThread(this::loadNews));

        // News list configuration


        // News pager configuration
        newsPager = getView().findViewById(R.id.news_pager);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        newsPagerAdapter = new NewsPagerAdapter(getContext(), ((MainActivity)getContext()).news);
        newsPager.setLayoutManager(horizontalLayoutManager);
        newsPager.setAdapter(newsPagerAdapter);

        PagerSnapHelper linearSnapHelper = new PagerSnapHelper();
        linearSnapHelper.attachToRecyclerView(newsPager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadNews(){
        ((MainActivity)getContext()).news.clear();
        if(newsListAdapter != null) newsListAdapter.notifyDataSetChanged();
        newsList = getView().findViewById(R.id.news_list);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        newsListAdapter = new NewsListAdapter(getContext(), ((MainActivity)getContext()).news);
        newsList.setLayoutManager(verticalLayoutManager);
        newsList.setAdapter(newsListAdapter);

        ((MainActivity)getContext()).newsCollection.find().sort(
                new Document().append("likes", MainActivity.DESCENDING)
        ).limit(50).iterator().getAsync(result -> {
           if(result.isSuccess()){
               gettingDataNews(result.get());
           }
        });
    }

    private void gettingDataNews(MongoCursor<Document> data){
        if(!data.hasNext()){
            newsRefresh.setRefreshing(false);
        } else {
            Document document = data.next();
            if (document.getObjectId("user_recognized_id") != null) {
                NewsRecognition newsRecognition = new NewsRecognition(
                        document.getObjectId("_id"),
                        document.getString("content"),
                        document.getDate("date"),
                        null,
                        document.getInteger("likes"),
                        document.getInteger("comments"),
                        document.getList("users_likes_id", ObjectId.class, new ArrayList<>()).contains(((MainActivity) getContext()).me.getUserID()),
                        document.getObjectId("user_owner_id"),
                        document.getObjectId("user_recognized_id"));

                ((MainActivity) getContext()).news.add(newsRecognition);
                ((MainActivity) getContext()).notifications.add(new Notification(newsRecognition.getNewsDate(), newsRecognition.getNewsID()));


            } else {
                News news = new News(
                        document.getObjectId("_id"),
                        document.getString("content"),
                        document.getDate("date"),
                        null,
                        document.getInteger("likes"),
                        document.getInteger("comments"),
                        document.getList("users_likes_id", ObjectId.class, new ArrayList<>()).contains(((MainActivity) getContext()).me.getUserID()),
                        document.getObjectId("user_owner_id"));

                ((MainActivity) getContext()).news.add(news);
                ((MainActivity) getContext()).notifications.add(new Notification(news.getNewsDate(), news.getNewsID()));

            }
            newsListAdapter.notifyItemInserted(((MainActivity) getContext()).news.size() - 1);
            gettingDataNews(data);
        }
    }
}