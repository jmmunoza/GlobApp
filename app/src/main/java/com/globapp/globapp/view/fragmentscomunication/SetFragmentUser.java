package com.globapp.globapp.view.fragmentscomunication;

import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.view.fragments.FragmentMe;
import com.globapp.globapp.view.fragments.FragmentUser;

import org.bson.types.ObjectId;

public class SetFragmentUser {
    public static void set(ObjectId userID){
        ObjectId userSessionID = UserSessionController.getUserSessionID();

        if(userID.equals(userSessionID)){
            FragmentMe fragmentMe = new FragmentMe();
            FragmentAdderManager.addFragmentRight(fragmentMe);
        } else {
            FragmentUser fragmentUser = new FragmentUser(userID);
            fragmentUser.addOnUserListener(SetFragmentGiveStar::set);
            FragmentAdderManager.addFragmentRight(fragmentUser);
        }
    }
}
