package com.globapp.globapp.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.globapp.globapp.model.Comment;

import java.util.List;

@Dao
public interface CommentDAO {
    @Query("SELECT * FROM comment WHERE newsID = :newsID")
    List<Comment> getNewsComments(String newsID);

    @Insert
    void insert (Comment comment);
}
