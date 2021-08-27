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
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FragmentNews extends Fragment {
    RecyclerView newsList;
    NewsListAdapter newsListAdapter;
    RecyclerView newsPager;
    NewsPagerAdapter newsPagerAdapter;

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

        // Temporal ----------------------
        ArrayList<String> news = new ArrayList<>();
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        news.add("NOTIFICATION");
        // -------------------------------------

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
        newsPagerAdapter = new NewsPagerAdapter(getContext(), news);
        RecyclerView.OnItemTouchListener listener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                if (newsPager.canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
                    switch (action){
                        case MotionEvent.ACTION_MOVE:
                            rv.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                } else {
                    switch (action){
                        case MotionEvent.ACTION_MOVE:
                            rv.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    newsPager.removeOnItemTouchListener(this);
                    return true;
                }
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        };
        newsPager.setLayoutManager(horizontalLayoutManager);
        newsPager.setAdapter(newsPagerAdapter);
        newsPager.addOnItemTouchListener(listener);
    }
}