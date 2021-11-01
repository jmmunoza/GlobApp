package com.globapp.globapp.model;

import android.net.Uri;

import com.globapp.globapp.GlobAppApplication;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Admin extends User {
    public Admin(String userID,       String userFirstName,   String userSecondName,
                 String userLastName, String userDescription,/* Uri userImage,*/
                 Uri userCoverImage,  int userCredits,        int userStars) {

        super(userID,         userFirstName,   userSecondName,
              userLastName,   userDescription, /*userImage,*/
             /* userCoverImage,*/ userCredits,     userStars);
    }
}
