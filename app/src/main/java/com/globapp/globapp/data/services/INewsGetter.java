package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnNewsListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsLoadedListener;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public interface INewsGetter {
    void getLatestNews(ArrayList<ObjectId> exceptedIDs, OnNewsListLoadedListener onNewsLoadedListener);
    void getNews(ObjectId newsID, OnNewsLoadedListener onNewsLoadedListener);
}
