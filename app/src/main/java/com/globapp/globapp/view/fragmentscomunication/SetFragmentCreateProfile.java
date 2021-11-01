package com.globapp.globapp.view.fragmentscomunication;

import com.globapp.globapp.view.fragments.FragmentCreateProfile;

import org.bson.types.ObjectId;

public class SetFragmentCreateProfile {
    public static void set(ObjectId userID){
        FragmentCreateProfile fragmentCreateProfile = new FragmentCreateProfile(userID);
        fragmentCreateProfile.OnCreateProfileListener(new FragmentCreateProfile.OnCreateProfileListener() {
            @Override
            public void onCancel() {
                FragmentAdderManager.deleteLastFragment();
            }

            @Override
            public void onSuccess() {
                FragmentAdderManager.deleteLastFragment();
                FragmentAdderManager.deleteLastFragment();
                SetFragmentLogin.set();
            }
        });
        FragmentAdderManager.addFragmentRight(fragmentCreateProfile);
    }
}
