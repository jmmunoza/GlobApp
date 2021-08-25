package com.globapp.globapp.fragmentmain.fragmentsettings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.fragmentmain.FragmentMain;

import java.util.concurrent.TimeUnit;

public class FragmentSettings extends Fragment {
    private ImageButton logoutButton;
    private RadioGroup radioLanguage;
    private RadioGroup radioDarkMode;

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

        logoutButton  = ((MainActivity)getContext()).findViewById(R.id.settings_logout_button);
        // Radio language settings
        radioLanguage = ((MainActivity)getContext()).findViewById(R.id.language_selection);
        if(((MainActivity)getContext()).isEnglish){
            radioLanguage.check(R.id.radio_english);
        } else {
            radioLanguage.check(R.id.radio_spanish);
        }

        radioLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_english:
                        ((MainActivity)getContext()).isEnglish = true;
                        break;
                    case R.id.radio_spanish:
                        ((MainActivity)getContext()).isEnglish = false;
                }
            }
        });


        // Radio dark mode settings
        radioDarkMode = ((MainActivity)getContext()).findViewById(R.id.dark_mode_selection);
        if(((MainActivity)getContext()).isDarkMode){
            radioDarkMode.check(R.id.radio_dark_mode_enabled);
        } else {
            radioDarkMode.check(R.id.radio_dark_mode_disabled);
        }

        radioDarkMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_dark_mode_disabled:
                        ((MainActivity)getContext()).isDarkMode = false;
                        break;
                    case R.id.radio_dark_mode_enabled:
                        ((MainActivity)getContext()).isDarkMode = true;
                }

                int fragmentsCount = ((MainActivity)getContext()).getSupportFragmentManager().getBackStackEntryCount();

                while (fragmentsCount != 0){
                    ((MainActivity)getContext()).getSupportFragmentManager().popBackStack();
                    fragmentsCount--;
                }

                ((MainActivity)getContext()).addFragment(new FragmentMain());
                ((MainActivity)getContext()).addFragment(FragmentSettings.this);
            }
        });
    }
}