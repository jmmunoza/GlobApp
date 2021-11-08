package com.globapp.globapp.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.globapp.globapp.model.News;
import com.globapp.globapp.util.ObjectIdConverter;

import org.bson.types.ObjectId;

import java.util.List;

@Dao
@TypeConverters(ObjectIdConverter.class)
public interface NewsDAO {
    @Query("SELECT * FROM news")
    List<News> getAll();

    @Query("SELECT * FROM news ORDER BY date DESC LIMIT 50")
    List<News> getLatestNews();

    @Query("SELECT * FROM news WHERE newsID = :newsID")
    News getNews(ObjectId newsID);

    @Query("SELECT newsID FROM news")
    List<ObjectId> getAllNews();

    @Query("SELECT COUNT() FROM news WHERE newsID = :newsID")
    boolean newsExists(ObjectId newsID);

    @Insert
    void insert (News news);
}
