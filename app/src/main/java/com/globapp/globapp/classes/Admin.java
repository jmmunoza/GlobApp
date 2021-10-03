package com.globapp.globapp.classes;

import android.net.Uri;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Admin extends User {
    public Admin(ObjectId userID,        String userFirstName,
                 String userSecondName,  String userLastName,
                 String userDescription, Uri userImage,
                 Uri userCoverImage,     ArrayList<Recognition> userRecognitions,
                 int userCredits,        int userStars) {

        super(userID,           userFirstName,    userSecondName,
                userLastName,   userDescription,  userImage,
                userCoverImage, userRecognitions, userCredits,
                userStars);
    }
}
