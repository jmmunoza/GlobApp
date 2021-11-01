package com.globapp.globapp.util;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

import com.globapp.globapp.GlobAppApplication;
import com.globapp.globapp.view.MainActivity;

public class KeyboardManager {
    public static void hide(Context context, IBinder windowToken){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }
}
