package com.globapp.globapp.model;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.globapp.globapp.util.DateConverter;
import com.globapp.globapp.util.ObjectIdConverter;

import org.bson.types.ObjectId;

@Entity
@TypeConverters(ObjectIdConverter.class)
public class User {

    @PrimaryKey(autoGenerate = true)
    private long   roomID;

    @ColumnInfo(name = "userID")
    private ObjectId userID;

    @ColumnInfo(name = "first_name")
    private String userFirstName;

    @ColumnInfo(name = "second_name")
    private String userSecondName;

    @ColumnInfo(name = "last_name")
    private String userLastName;

    @ColumnInfo(name = "description")
    private String userDescription;

    @ColumnInfo(name = "credits")
    private int    userCredits;

    @ColumnInfo(name = "stars")
    private int    userStars;

    @Ignore
    private Uri    userImage;

    @Ignore
    private Uri    userCoverImage;

    public User(ObjectId userID,          String userFirstName,
                String userSecondName,  String userLastName,
                String userDescription,// Uri userImage, Uri userCoverImage,
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

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
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

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserID(ObjectId userID) {
        this.userID = userID;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public void setUserSecondName(String userSecondName) {
        this.userSecondName = userSecondName;
    }
}
