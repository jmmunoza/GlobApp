package com.globapp.globapp.data.local;

import com.globapp.globapp.data.listeners.OnNewsListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsLoadedListener;
import com.globapp.globapp.data.local.dao.NewsDAO;
import com.globapp.globapp.data.services.INewsGetter;
import com.globapp.globapp.model.News;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class NewsGetterLocal implements INewsGetter {
    private final NewsDAO newsDAO;

    public NewsGetterLocal(){
        newsDAO = LocalDB.getInstance().newsDAO();
    }

    @Override
    public void getLatestNews(ArrayList<ObjectId> exceptedIDs, OnNewsListLoadedListener onNewsListLoadedListener) {
        ArrayList<News> newsList = (ArrayList<News>) newsDAO.getLatestNews();
        onNewsListLoadedListener.onNewsListLoaded(newsList);
    }

    @Override
    public void getNews(ObjectId newsID, OnNewsLoadedListener onNewsLoadedListener) {
        News news = newsDAO.getNews(newsID);
        onNewsLoadedListener.onNewsLoaded(news);
    }
}
