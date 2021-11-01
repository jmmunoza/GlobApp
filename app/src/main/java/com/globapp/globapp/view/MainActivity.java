package com.globapp.globapp.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.globapp.globapp.R;
import com.globapp.globapp.util.ToastMaker;
import com.globapp.globapp.view.fragmentscomunication.FragmentAdderManager;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    private GifImageView animationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationContainer = findViewById(R.id.animation_container);
        ToastMaker.init(this);
        FragmentAdderManager.init(this);

    }

    public void enableAnimation(int animationID) {
        animationContainer.setImageResource(animationID);
        ((GifDrawable) animationContainer.getDrawable()).start();
        ((GifDrawable) animationContainer.getDrawable()).addAnimationListener(i -> {
            ((GifDrawable) animationContainer.getDrawable()).stop();
            animationContainer.setImageBitmap(null);
        });
    }

    @Override
    public void onBackPressed() {
        FragmentAdderManager.onBackPressed();
    }
}