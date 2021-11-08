package com.globapp.globapp.data.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.globapp.globapp.GlobAppApplication;
import com.globapp.globapp.data.listeners.OnDatabaseConnectedListener;
import com.globapp.globapp.data.local.dao.CommentDAO;
import com.globapp.globapp.data.local.dao.NewsDAO;
import com.globapp.globapp.data.local.dao.UserDAO;
import com.globapp.globapp.model.News;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.DateConverter;
import com.globapp.globapp.util.ObjectIdConverter;

@Database(entities = {User.class,     News.class}, version = 1)
@TypeConverters({ObjectIdConverter.class, DateConverter.class})
public abstract class LocalDB extends RoomDatabase {
    private final static String DATABASE_NAME = "GlobappLocalDB";
    private static LocalDB localDB;

    public abstract NewsDAO    newsDAO();
    public abstract UserDAO    userDAO();
    public abstract CommentDAO commentDAO();

    public static void initDB(){
        localDB = Room.databaseBuilder(
                GlobAppApplication.getAppContext(),
                LocalDB.class,
                DATABASE_NAME).allowMainThreadQueries().build();
    }

    public static LocalDB getInstance(){
        if(localDB == null){
            initDB();
        }

        return localDB;
    }
}