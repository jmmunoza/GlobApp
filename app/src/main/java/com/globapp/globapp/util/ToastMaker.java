package com.globapp.globapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class ToastMaker {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private ToastMaker(Context context){
        ToastMaker.context = context;
    }

    public static void init(Context context){
        new ToastMaker(context);
    }

    public static void show(String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
