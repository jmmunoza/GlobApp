package com.globapp.globapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.globapp.globapp.GlobAppApplication;

public class UserSessionController {
    private static final String USER_SESSION = "USER_SESSION";
    private static final String USER_ADMIN   = "USER_ADMIN";
    private static final String DATA         = "DATA";

    private static UserSessionController userSessionControllerInstance;
    private static SharedPreferences sharedPreferences;

    private UserSessionController(){
        sharedPreferences = GlobAppApplication.getAppContext().getSharedPreferences(DATA, Context.MODE_PRIVATE);
    }

    private static void createInstance(){
        userSessionControllerInstance = new UserSessionController();
    }

    public static String getUserSessionID(){
        if(userSessionControllerInstance == null) createInstance();
        return sharedPreferences.getString(USER_SESSION, null);
    }

    public static void setUserSessionID(String userSessionID){
        if(userSessionControllerInstance == null) createInstance();
        sharedPreferences.edit().putString(USER_SESSION, userSessionID).apply();
    }

    public static boolean isUserAdmin(){
        if(userSessionControllerInstance == null) createInstance();
        return sharedPreferences.getBoolean(USER_ADMIN, false);
    }

    public static void setUserAdmin(boolean isAdmin){
        if(userSessionControllerInstance == null) createInstance();
        sharedPreferences.edit().putBoolean(USER_ADMIN, isAdmin).apply();
    }
}