package com.globapp.globapp.view.fragmentscomunication;

import com.globapp.globapp.view.fragments.FragmentMain;

import org.bson.types.ObjectId;

public class SetFragmentMain {
    public static void set(){
        FragmentMain fragmentMain = new FragmentMain();
        fragmentMain.addOnMainListener(new FragmentMain.OnMainListener() {
            @Override
            public void search() {
                SetFragmentSearch.set();
            }

            @Override
            public void settings() {
                SetFragmentSettings.set();
            }

            @Override
            public void onNewsClicked(ObjectId newsID) {
                SetFragmentOnNotification.set(newsID);
            }

            @Override
            public void onUserImageClicked(ObjectId userID) {
                SetFragmentUser.set(userID);
            }
        });
        FragmentAdderManager.addFragmentRight(fragmentMain);
    }
}
