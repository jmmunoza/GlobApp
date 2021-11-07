package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.ImageConverter;
import com.globapp.globapp.view.MainActivity;
import com.globapp.globapp.view.dialogs.AboutSettingsDialog;
import com.globapp.globapp.view.dialogs.LanguageSettingsDialog;

import org.bson.types.ObjectId;

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

    // Data
    private User me;

    // Listener
    private OnSettingsListener onSettingsListener;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_settings, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserData();
    }

    private void logoutButtonFunction(){
        logoutButton.setOnClickListener(v -> onSettingsListener.logout());
    }

    private void editProfileButtonFunction(){
        editProfileButton.setOnClickListener(v -> onSettingsListener.editProfile());
    }

    private void darkModeSwitchFunction(){
        darkModeSwitch.setChecked(Preferences.getDarkMode());
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Preferences.setDarkMode(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    private void languageButtonFunction(){
        languageButton.setOnClickListener(v -> {
            LanguageSettingsDialog languageSettingsDialog = new LanguageSettingsDialog(requireContext());
            languageSettingsDialog.createDialog().show();
        });
    }

    private void aboutButtonFunction(){
        aboutButton.setOnClickListener(v -> {
            AboutSettingsDialog aboutSettingsDialog = new AboutSettingsDialog(requireContext());
            aboutSettingsDialog.createDialog().show();
        });
    }

    private void notificationButtonFunction(){
        notificationButton.setOnClickListener(v -> {

        });
    }

    private void loadUserData(){
        ObjectId userSessionID = UserSessionController.getUserSessionID();
        DataRepository.getUser(userSessionID, user -> {
            me = user;
            loadComponents();
        });
    }

    private void userImageFunction(){
        if(me.getUserImage() != null) userImage.setImageBitmap(ImageConverter.ByteArrayToBitmap(me.getUserImage()));
    }

    private void usernameFunction(){
        username.setText(me.getUserFirstName());
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