package com.globapp.globapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.globapp.globapp.GlobAppApplication;
import com.globapp.globapp.data.services.IUserSessionController;

public class UserSessionController implements IUserSessionController {
    private static final String USER_SESSION = "USER_SESSION";
    private static final String USER_ADMIN   = "USER_ADMIN";
    private static final String DATA         = "DATA";

    private final SharedPreferences sharedPreferences;

    public UserSessionController(){
        sharedPreferences = GlobAppApplication.getAppContext().getSharedPreferences(DATA, Context.MODE_PRIVATE);
    }

    public String getUserSessionID(){
        return sharedPreferences.getString(USER_SESSION, null);
    }

    public void setUserSessionID(String userSessionID){
        sharedPreferences.edit().putString(USER_SESSION, userSessionID).apply();
    }

    public boolean isUserAdmin(){
        return sharedPreferences.getBoolean(USER_ADMIN, false);
    }

    public void setUserAdmin(boolean isAdmin){
        sharedPreferences.edit().putBoolean(USER_ADMIN, isAdmin).apply();
    }
}