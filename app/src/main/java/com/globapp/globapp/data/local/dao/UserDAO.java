package com.globapp.globapp.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.globapp.globapp.model.News;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.ObjectIdConverter;

import org.bson.types.ObjectId;

import java.util.List;

@Dao
@TypeConverters(ObjectIdConverter.class)
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE userID = :userID")
    User getUser(ObjectId userID);

    @Query("SELECT COUNT() FROM user WHERE userID = :userID LIMIT 1")
    boolean userExists(ObjectId userID);

    @Insert
    void insert (User user);
}