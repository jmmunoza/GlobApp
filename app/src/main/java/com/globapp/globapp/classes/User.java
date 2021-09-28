package com.globapp.globapp.classes;

import android.net.Uri;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class User {
    private ObjectId               meID;
    private String                 meName;
    private String                 meDescription;
    private Uri                    meImage;
    private Uri                    meCoverImage;
    private int                    meCredits;
    private int                    meStars;
    private ArrayList<Recognition> meRecognitions;
    private boolean                meIsAdmin;

    public User(ObjectId meID,      String meName,                         String meDescription,  Uri meImage,
                Uri meCoverImage, ArrayList<Recognition> meRecognitions, int meCredits,
                int meStars,      boolean meIsAdmin){

        this.meCredits      = meCredits;
        this.meStars        = meStars;
        this.meID           = meID;
        this.meName         = meName;
        this.meDescription  = meDescription;
        this.meImage        = meImage;
        this.meCoverImage   = meCoverImage;
        this.meRecognitions = meRecognitions;
        this.meIsAdmin      = meIsAdmin;
    }

    public User(ObjectId meID, String meName, String meDescription,  Uri meImage, Uri meCoverImage, boolean meIsAdmin){

        this.meCredits      = 0;
        this.meStars        = 0;
        this.meID           = meID;
        this.meName         = meName;
        this.meDescription  = meDescription;
        this.meImage        = meImage;
        this.meCoverImage   = meCoverImage;
        this.meRecognitions = new ArrayList<>();
        this.meIsAdmin      = meIsAdmin;
    }

    public boolean getMeIsAdmin() {
        return meIsAdmin;
    }

    public void setMeIsAdmin(boolean meIsAdmin) {
        this.meIsAdmin = meIsAdmin;
    }

    public void setMeCoverImage(Uri meCoverImage) {
        this.meCoverImage = meCoverImage;
    }

    public void setMeDescription(String meDescription) {
        this.meDescription = meDescription;
    }

    public void setMeImage(Uri meImage) {
        this.meImage = meImage;
    }

    public int getMeStars() {
        return meStars;
    }

    public int getMeCredits() {
        return meCredits;
    }

    public void addMeRecognitions(Recognition recognition){
        meRecognitions.add(recognition);
    }

    public ObjectId getMeID() {
        return meID;
    }

    public ArrayList<Recognition> getMeRecognitions() {
        return meRecognitions;
    }

    public Uri getMeCoverImage() {
        return meCoverImage;
    }

    public Uri getMeImage() {
        return meImage;
    }

    public String getMeDescription() {
        return meDescription;
    }

    public String getMeName() {
        return meName;
    }
}
