package com.globapp.globapp;

import android.app.Application;
import android.content.Context;

import com.globapp.globapp.data.local.LocalDB;
import com.globapp.globapp.data.remote.MongoDB;

public class GlobAppApplication extends Application {
    private static Context        appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        MongoDB.initDB();
        LocalDB.initDB();
    }

    public static Context getAppContext() {
        return appContext;
    }
}