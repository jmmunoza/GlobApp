package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnNewsListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsLoadedListener;

import org.bson.types.ObjectId;

public interface INewsGetter {
    void getLatestNews(OnNewsListLoadedListener onNewsLoadedListener);
    void getNews(ObjectId newsID, OnNewsLoadedListener onNewsLoadedListener);
}
