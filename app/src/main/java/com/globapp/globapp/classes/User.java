package com.globapp.globapp.classes;

import android.net.Uri;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class User {
    private ObjectId               userID;
    private String                 userFirstName;
    private String                 userSecondName;
    private String                 userLastName;
    private String                 userDescription;
    private Uri                    userImage;
    private Uri                    userCoverImage;
    private int                    userCredits;
    private int                    userStars;
    private ArrayList<Recognition> userRecognitions;

    public User(ObjectId userID,                         String userFirstName,
                String userSecondName,                   String userLastName,
                String userDescription,                  Uri userImage, Uri userCoverImage,
                ArrayList<Recognition> userRecognitions, int userCredits,
                int userStars){

        this.userCredits      = userCredits;
        this.userStars        = userStars;
        this.userID           = userID;
        this.userFirstName    = userFirstName;
        this.userSecondName   = userSecondName;
        this.userLastName     = userLastName;
        this.userDescription  = userDescription;
        this.userImage        = userImage;
        this.userCoverImage   = userCoverImage;
        this.userRecognitions = userRecognitions;
    }

    public Uri getUserImage() {
        return userImage;
    }

    public Uri getUserCoverImage() {
        return userCoverImage;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public ObjectId getUserID() {
        return userID;
    }

    public int getUserStars() {
        return userStars;
    }

    public int getUserCredits() {
        return userCredits;
    }

    public ArrayList<Recognition> getUserRecognitions() {
        return userRecognitions;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public void setUserCredits(int userCredits) {
        this.userCredits = userCredits;
    }

    public void setUserCoverImage(Uri userCoverImage) {
        this.userCoverImage = userCoverImage;
    }

    public void setUserRecognitions(ArrayList<Recognition> userRecognitions) {
        this.userRecognitions = userRecognitions;
    }

    public void setUserStars(int userStars) {
        this.userStars = userStars;
    }

    public void setUserImage(Uri userImage) {
        this.userImage = userImage;
    }

    public void giveLike(ObjectId newsID){

    }
}
