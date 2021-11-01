package com.globapp.globapp.view.fragmentscomunication;

import androidx.fragment.app.Fragment;

import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.view.fragments.FragmentSettings;

public class SetFragmentSettings {
    public static void set(){
        FragmentSettings fragmentSettings = new FragmentSettings();
        fragmentSettings.addOnSettingsListener(new FragmentSettings.OnSettingsListener() {
            @Override
            public void editProfile() {
                SetFragmentEditProfile.set();
            }

            @Override
            public void logout() {
                FragmentAdderManager.deleteAllFragments();
                UserSessionController.setUserSessionID(null);
                UserSessionController.setUserAdmin(false);
                SetFragmentLogin.set();
            }
        });
        FragmentAdderManager.addFragmentLeft(fragmentSettings);
    }
}
