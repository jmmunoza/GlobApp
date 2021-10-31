package com.globapp.globapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.globapp.globapp.GlobAppApplication;

public class Preferences {
    private static final String DARK_MODE   = "DARK_MODE";
    private static final String IS_ENGLISH  = "IS_ENGLISH";
    private static final String DATA        = "DATA";
    private static SharedPreferences sharedPreferences;

    public Preferences(){
        sharedPreferences = GlobAppApplication.getAppContext().getSharedPreferences(DATA, Context.MODE_PRIVATE);
    }

    public static boolean getDarkMode(){
        return sharedPreferences.getBoolean(DARK_MODE, false);
    }

    public static boolean getEnglish(){
        return sharedPreferences.getBoolean(IS_ENGLISH, true);
    }

    public static void setDarkMode(boolean darkMode){
        sharedPreferences.edit().putBoolean(DARK_MODE, darkMode).apply();
    }

    public static void setIsEnglish(boolean isEnglish){
        sharedPreferences.edit().putBoolean(IS_ENGLISH, isEnglish).apply();
    }
}
