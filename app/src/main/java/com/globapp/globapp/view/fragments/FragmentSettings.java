package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.Preferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentSettings extends Fragment {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch            darkModeSwitch;
    private CircleImageView   userImage;
    private TextView          username;
    private CardView          editProfileButton;
    private CardView          languageButton;
    private CardView          notificationButton;
    private CardView          aboutButton;
    private CardView          logoutButton;

    // Listener
    private OnSettingsListener onSettingsListener;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);

        if(Preferences.getDarkMode()){
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

    private void logoutButtonFunction(){
        logoutButton.setOnClickListener(v -> onSettingsListener.logout());
    }

    private void editProfileButtonFunction(){
        editProfileButton.setOnClickListener(v -> onSettingsListener.editProfile());
    }

    private void darkModeSwitchFunction(){
        darkModeSwitch.setChecked(Preferences.getDarkMode());
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> Preferences.setDarkMode(isChecked));
    }

    private void languageButtonFunction(){
        languageButton.setOnClickListener(v -> createLanguageDialog().show());
    }

    private BottomSheetDialog createLanguageDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        if(Preferences.getDarkMode()){
            bottomSheetDialog.setContentView(R.layout.fragment_settings_language_dark);
        } else {
            bottomSheetDialog.setContentView(R.layout.fragment_settings_language);
        }

        CardView spanish = bottomSheetDialog.findViewById(R.id.spanish_button);
        CardView english = bottomSheetDialog.findViewById(R.id.english_button);

        if(Preferences.getEnglish()){
            assert english != null;
            english.setCardBackgroundColor(getResources().getColor(R.color.globant_main_color));
        } else {
            assert spanish != null;
            spanish.setCardBackgroundColor(getResources().getColor(R.color.globant_main_color));
        }

        assert spanish != null;
        spanish.setOnClickListener(v1 -> {
            Preferences.setIsEnglish(false);
            Toast.makeText(getContext(), getString(R.string.spanish),Toast.LENGTH_LONG).show();
            bottomSheetDialog.cancel();
        });

        assert english != null;
        english.setOnClickListener(v1 -> {
            Preferences.setIsEnglish(true);
            Toast.makeText(getContext(), getString(R.string.english),Toast.LENGTH_LONG).show();
            bottomSheetDialog.cancel();
        });

        return bottomSheetDialog;
    }

    private void aboutButtonFunction(){
        aboutButton.setOnClickListener(v -> createAboutDialog().show());
    }

    private void notificationButtonFunction(){
        notificationButton.setOnClickListener(v -> {

        });
    }

    private void userImageFunction(){
        //if (((MainActivity)getContext()).me.getUserImage() != null) userImage.setImageURI(((MainActivity)getContext()).me.getUserImage());
    }

    private void usernameFunction(){
        //username.setText(((MainActivity)getContext()).me.getUserFirstName());
    }

    private BottomSheetDialog createAboutDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        if(Preferences.getDarkMode()){
            bottomSheetDialog.setContentView(R.layout.fragment_settings_about_dark);
        } else {
            bottomSheetDialog.setContentView(R.layout.fragment_settings_about);
        }

        return bottomSheetDialog;
    }

    private void loadComponents(){
        userImage          = requireView().findViewById(R.id.settings_user_image);
        username           = requireView().findViewById(R.id.settings_username);
        editProfileButton  = requireView().findViewById(R.id.settings_edit_profile_button);
        darkModeSwitch     = requireView().findViewById(R.id.settings_dark_mode_switch);
        languageButton     = requireView().findViewById(R.id.settings_language_button);
        notificationButton = requireView().findViewById(R.id.settings_notification_button);
        aboutButton        = requireView().findViewById(R.id.settings_about_button);
        logoutButton       = requireView().findViewById(R.id.settings_logout_button);

        userImageFunction();
        usernameFunction();
        notificationButtonFunction();
        aboutButtonFunction();
        darkModeSwitchFunction();
        editProfileButtonFunction();
        languageButtonFunction();
        logoutButtonFunction();
    }

    public interface OnSettingsListener {
        void editProfile();
        void logout();
    }

    public void addOnSettingsListener(OnSettingsListener onSettingsListener){
        this.onSettingsListener = onSettingsListener;
    }
}