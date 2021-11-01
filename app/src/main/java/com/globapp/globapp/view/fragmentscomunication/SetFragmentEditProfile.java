package com.globapp.globapp.view.fragmentscomunication;

import com.globapp.globapp.view.fragments.FragmentEditProfile;

public class SetFragmentEditProfile {
    public static void set(){
        FragmentEditProfile fragmentEditProfile = new FragmentEditProfile();
        fragmentEditProfile.addOnEditProfileListener(new FragmentEditProfile.OnEditProfileListener() {
            @Override
            public void onSuccess() {
                FragmentAdderManager.deleteLastFragment();
            }

            @Override
            public void onCancel() {
                FragmentAdderManager.deleteLastFragment();
            }
        });
        FragmentAdderManager.addFragmentLeft(fragmentEditProfile);
    }
}
