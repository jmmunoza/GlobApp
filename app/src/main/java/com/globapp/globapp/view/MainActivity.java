package com.globapp.globapp.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.listeners.OnDatabaseConnectedListener;
import com.globapp.globapp.data.local.Preferences;
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

        Preferences.init();
        ToastMaker.init(MainActivity.this);
        enableAnimation(R.drawable.loading_animation_1);
        DataRepository.init(new OnDatabaseConnectedListener() {
            @Override
            public void onDBConnected() {
                FragmentAdderManager.init(MainActivity.this);
                disableAnimation();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void disableAnimation(){
        AlphaAnimation fade = new AlphaAnimation(1.f, 0.f);
        fade.setDuration(1000);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ((GifDrawable) animationContainer.getDrawable()).stop();
                animationContainer.setImageBitmap(null);
                animationContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animationContainer.setAnimation(fade);
    }

    public void enableAnimation(int animationID) {
        animationContainer = findViewById(R.id.animation_container);
        animationContainer.setImageResource(animationID);
        if(Preferences.getDarkMode()){
            animationContainer.setBackgroundColor(getResources().getColor(R.color.dark));
        } else {
            animationContainer.setBackgroundColor(getResources().getColor(R.color.white));
        }
        ((GifDrawable) animationContainer.getDrawable()).start();
    }

    @Override
    public void onBackPressed() {
        FragmentAdderManager.onBackPressed();
    }
}