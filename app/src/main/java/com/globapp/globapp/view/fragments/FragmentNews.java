package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.listeners.OnUserImageClickedListener;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.view.adapters.NewsListAdapter;
import com.globapp.globapp.view.adapters.NewsPagerAdapter;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FragmentNews extends Fragment {

    // UI Components
    private RecyclerView       newsList;
    private NewsListAdapter    newsListAdapter;
    private RecyclerView       newsPager;
    private NewsPagerAdapter   newsPagerAdapter;
    private SwipeRefreshLayout newsRefresh;
    private ShimmerFrameLayout newsPlaceholder;

    // Listeners
    private OnUserImageClickedListener onUserImageClickedListener;

    @SuppressLint("InflateParams") @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_news, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComponents();
    }

    private void refreshFunction(){
        newsRefresh.setOnRefreshListener(() -> {
            newsListAdapter.clear();
            newsListAdapter.notifyDataSetChanged();
            loadNews(new ArrayList<>());
        });
    }

    private void newsListFunction(){
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        newsList.setLayoutManager(verticalLayoutManager);
        newsList.setNestedScrollingEnabled(false);
        newsListAdapter = new NewsListAdapter(getContext(), new ArrayList<>(), onUserImageClickedListener);
        newsList.setAdapter(newsListAdapter);
        newsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadNews(newsListAdapter.getExceptedIDs());
                }
            }
        });
    }

    private void newsPagerFunction(){
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        newsPagerAdapter = new NewsPagerAdapter(getContext(), new ArrayList<>());
        newsPager.setLayoutManager(horizontalLayoutManager);
        newsPager.setAdapter(newsPagerAdapter);
        new PagerSnapHelper().attachToRecyclerView(newsPager);
    }

    private void loadComponents(){
        newsRefresh      = requireView().findViewById(R.id.news_refresh);
        newsPlaceholder  = requireView().findViewById(R.id.news_placeholder);
        newsList         = requireView().findViewById(R.id.news_list);
        newsPager        = requireView().findViewById(R.id.news_pager);

        refreshFunction();
        newsPagerFunction();
        newsListFunction();
        loadNews(new ArrayList<>());
    }

    private void loadNews(ArrayList<ObjectId> exceptedIDs){
        newsPlaceholder.setVisibility(View.GONE);
        //newsList.setVisibility(View.INVISIBLE);
       // newsPlaceholder.setVisibility(View.VISIBLE);
        //newsPlaceholder.startShimmer();

        DataRepository.getLatestNews(exceptedIDs, newsList -> getActivity().runOnUiThread(() -> {
            newsListAdapter.addNews(newsList);
            newsListAdapter.notifyItemRangeInserted(
                    newsListAdapter.getItemCount()-newsList.size(),
                    newsListAdapter.getItemCount());
            newsRefresh.setRefreshing(false);


            newsListAdapter.addDataLoadedListener(() -> {
                newsPlaceholder.stopShimmer();
                newsPlaceholder.setVisibility(View.GONE);
                FragmentNews.this.newsList.setVisibility(View.VISIBLE);
            });
        }));
    }

    public void addOnUserImageClickedListener(OnUserImageClickedListener onUserImageClickedListener){
        this.onUserImageClickedListener = onUserImageClickedListener;
    }
}