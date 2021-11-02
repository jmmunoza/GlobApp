package com.globapp.globapp.view.fragmentscomunication;

import com.globapp.globapp.view.fragments.FragmentLogin;

import org.bson.types.ObjectId;

public class SetFragmentLogin {
    public static void set(){
        FragmentLogin fragmentLogin = new FragmentLogin();
        fragmentLogin.addOnLoginReadyListener(new FragmentLogin.OnLoginReadyListener() {
            @Override
            public void onNewUser(ObjectId userID) {
                SetFragmentCreateProfile.set(userID);
            }

            @Override
            public void onUserCreated() {
                FragmentAdderManager.deleteLastFragment();
                SetFragmentMain.set();
            }

            @Override
            public void onWrongPassword() {
                //Toast.makeText(MainActivity.this,"Mala contra", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onWrongEmail() {
                //Toast.makeText(MainActivity.this, "Mal correo", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {
                //Toast.makeText(MainActivity.this, "Errorrrrr", Toast.LENGTH_LONG).show();
            }
        });
        FragmentAdderManager.addFragmentFade(fragmentLogin);
    }
}
