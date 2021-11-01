package com.globapp.globapp.view.fragmentscomunication;

import com.globapp.globapp.view.fragments.FragmentOnNotification;

import org.bson.types.ObjectId;

public class SetFragmentOnNotification {
    public static void set(ObjectId newsID){
        FragmentOnNotification fragmentOnNotification = new FragmentOnNotification(newsID);
        fragmentOnNotification.addOnUserImageClickedListener(SetFragmentUser::set);
        FragmentAdderManager.addFragmentRight(fragmentOnNotification);
    }
}