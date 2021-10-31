package com.globapp.globapp.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.globapp.globapp.model.News;
import com.globapp.globapp.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<News> getAll();

    @Query("SELECT * FROM user WHERE userID = :userID")
    User getUser(String userID);

    @Insert
    void insert (User user);
}