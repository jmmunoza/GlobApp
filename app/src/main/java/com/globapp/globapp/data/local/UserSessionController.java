package com.globapp.globapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.globapp.globapp.GlobAppApplication;

import org.bson.types.ObjectId;

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

    public static ObjectId getUserSessionID(){
        if(userSessionControllerInstance == null) createInstance();
        if(sharedPreferences.getString(USER_SESSION, null) == null) return null;
        else return new ObjectId(sharedPreferences.getString(USER_SESSION, null));
    }

    public static void setUserSessionID(ObjectId userSessionID){
        if(userSessionControllerInstance == null) createInstance();
        if(userSessionID == null){
            sharedPreferences.edit().putString(USER_SESSION, null).apply();
        } else {
            sharedPreferences.edit().putString(USER_SESSION, userSessionID.toString()).apply();
        }
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