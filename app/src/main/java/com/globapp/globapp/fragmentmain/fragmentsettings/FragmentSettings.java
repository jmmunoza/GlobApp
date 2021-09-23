package com.globapp.globapp.fragmentmain.fragmentsettings;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.fragmentmain.FragmentMain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentSettings extends Fragment {

    private CircleImageView userImage;
    private TextView        username;
    private CardView        editProfileButton;
    private Switch          darkModeSwitch;
    private CardView        languageButton;
    private CardView        notificationButton;
    private CardView        aboutButton;
    private CardView        logoutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(((MainActivity)getContext()).isDarkMode){
            return inflater.inflate(R.layout.fragment_settings_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_settings, null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComponents();
    }

    private void loadComponents(){
        userImage          = getView().findViewById(R.id.settings_user_image);
        username           = getView().findViewById(R.id.settings_username);
        editProfileButton  = getView().findViewById(R.id.settings_edit_profile_button);
        darkModeSwitch     = getView().findViewById(R.id.settings_dark_mode_switch);
        languageButton     = getView().findViewById(R.id.settings_language_button);
        notificationButton = getView().findViewById(R.id.settings_notification_button);
        aboutButton        = getView().findViewById(R.id.settings_about_button);
        logoutButton       = getView().findViewById(R.id.settings_logout_button);

        if (((MainActivity)getContext()).me.getMeImage() != null) userImage.setImageURI(((MainActivity)getContext()).me.getMeImage());
        username.setText(((MainActivity)getContext()).me.getMeName());



        // DARK MODE SETTINGS
        SharedPreferences sharedPreferences = ((MainActivity)getContext()).getSharedPreferences(MainActivity.DATA, Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean(MainActivity.DARK_MODE, false)){
            darkModeSwitch.setChecked(true);
        } else {
            darkModeSwitch.setChecked(false);
        }

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = ((MainActivity)getContext()).getSharedPreferences(MainActivity.DATA, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MainActivity.DARK_MODE, isChecked);
                editor.apply();
                editor.commit();
            }
        });
    }
}