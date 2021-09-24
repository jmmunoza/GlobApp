package com.globapp.globapp.fragmentmain.fragmentnews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class FragmentNews extends Fragment {
    public RecyclerView newsList;
    public NewsListAdapter newsListAdapter;
    public RecyclerView newsPager;
    public NewsPagerAdapter newsPagerAdapter;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComponents();
    }

    private void loadComponents(){

        newsRefresh = getView().findViewById(R.id.news_refresh);
        newsRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsListAdapter.notifyDataSetChanged();
                        newsPagerAdapter.notifyDataSetChanged();
                        newsRefresh.setRefreshing(false);
                    }
                });
            }
        });


        // News list configuration
        newsList = getView().findViewById(R.id.news_list);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        newsListAdapter = new NewsListAdapter(getContext(), ((MainActivity)getContext()).news);
        newsList.setLayoutManager(verticalLayoutManager);
        newsList.setAdapter(newsListAdapter);

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
}