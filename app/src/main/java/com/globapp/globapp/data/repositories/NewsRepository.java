package com.globapp.globapp.data.repositories;

import com.globapp.globapp.data.listeners.OnNewsLikedListener;
import com.globapp.globapp.data.listeners.OnNewsListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsLoadedListener;
import com.globapp.globapp.data.services.INewsGetter;
import com.globapp.globapp.data.services.INewsInserter;
import com.globapp.globapp.data.services.INewsLiker;
import com.globapp.globapp.model.News;

import org.bson.types.ObjectId;

public class NewsRepository {
    private final INewsInserter iNewsInserter;
    private final INewsGetter   iNewsGetter;
    private final INewsLiker    iNewsLiker;

    public NewsRepository(INewsInserter iNewsInserter, INewsGetter iNewsGetter, INewsLiker iNewsLiker){
        this.iNewsGetter   = iNewsGetter;
        this.iNewsInserter = iNewsInserter;
        this.iNewsLiker    = iNewsLiker;
    }

    public void insertNews(News news){
        iNewsInserter.insert(news);
    }

    public void getNews(ObjectId newsID, OnNewsLoadedListener onNewsLoadedListener){
        iNewsGetter.getNews(newsID, onNewsLoadedListener);
    }

    public void getLatestNews(OnNewsListLoadedListener onNewsListLoadedListener){
        iNewsGetter.getLatestNews(onNewsListLoadedListener);
    }

    public void likeNews(ObjectId newsID, OnNewsLikedListener onNewsLikedListener){
        iNewsLiker.like(newsID, onNewsLikedListener);
    }

    public void getIsLiked(ObjectId newsID, OnNewsLikedListener onNewsLikedListener){
        iNewsLiker.getIsLiked(newsID, onNewsLikedListener);
    }
}