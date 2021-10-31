package com.globapp.globapp.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.globapp.globapp.model.News;

import java.util.List;

@Dao
public interface NewsDAO {
    @Query("SELECT * FROM news")
    List<News> getAll();

    @Query("SELECT * FROM news ORDER BY date DESC LIMIT 50")
    List<News> getLatestNews();

    @Query("SELECT * FROM news WHERE newsID = :newsID")
    News getNews(String newsID);

    @Insert
    void insert (News news);
}
