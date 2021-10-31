package com.globapp.globapp.model;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.bson.types.ObjectId;

import java.util.ArrayList;

@Entity
public class User {
    @PrimaryKey(autoGenerate = false)
    private String userID;

    @ColumnInfo(name = "first_name")
    private String userFirstName;

    @ColumnInfo(name = "second_name")
    private String userSecondName;

    @ColumnInfo(name = "last_name")
    private String userLastName;

    @ColumnInfo(name = "description")
    private String userDescription;

    private Uri    userImage;
    private Uri    userCoverImage;

    @ColumnInfo(name = "credits")
    private int    userCredits;

    @ColumnInfo(name = "stars")
    private int    userStars;

    public User(String userID,          String userFirstName,
                String userSecondName,  String userLastName,
                String userDescription, Uri userImage, Uri userCoverImage,
                int userCredits,        int userStars){

        this.userCredits      = userCredits;
        this.userStars        = userStars;
        this.userID           = userID;
        this.userFirstName    = userFirstName;
        this.userSecondName   = userSecondName;
        this.userLastName     = userLastName;
        this.userDescription  = userDescription;
        this.userImage        = userImage;
        this.userCoverImage   = userCoverImage;
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

    public String getUserID() {
        return userID;
    }

    public int getUserStars() {
        return userStars;
    }

    public int getUserCredits() {
        return userCredits;
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

    public void setUserStars(int userStars) {
        this.userStars = userStars;
    }

    public void setUserImage(Uri userImage) {
        this.userImage = userImage;
    }

    public void giveLike(ObjectId newsID){

    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public void setUserSecondName(String userSecondName) {
        this.userSecondName = userSecondName;
    }
}
