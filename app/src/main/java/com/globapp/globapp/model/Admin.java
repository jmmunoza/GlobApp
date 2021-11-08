package com.globapp.globapp.model;

import org.bson.types.ObjectId;

public class Admin extends User {
    public Admin(ObjectId userID,       String userFirstName,   String userSecondName,
                 String userLastName, String userDescription, byte[] userImage,
                 byte[] userCoverImage,  int userCredits,        int userStars) {

        super(userID,         userFirstName,   userSecondName,
              userLastName,   userDescription, userImage,
              userCoverImage, userCredits,     userStars);
    }
}
