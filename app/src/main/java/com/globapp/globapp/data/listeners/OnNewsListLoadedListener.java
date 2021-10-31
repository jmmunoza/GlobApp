package com.globapp.globapp.data.listeners;

import com.globapp.globapp.model.News;

import java.util.ArrayList;

public interface OnNewsListLoadedListener {
    void onNewsListLoaded(ArrayList<News> newsList);
}
