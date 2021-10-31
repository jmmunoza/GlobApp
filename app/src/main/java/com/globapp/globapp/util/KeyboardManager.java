package com.globapp.globapp.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.globapp.globapp.GlobAppApplication;
import com.globapp.globapp.view.MainActivity;

public class KeyboardManager {
    public static void hide(){
        Context appContext = GlobAppApplication.getAppContext();
        InputMethodManager imm = (InputMethodManager) appContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((MainActivity)appContext).getCurrentFocus().getWindowToken(), 0);
    }
}
