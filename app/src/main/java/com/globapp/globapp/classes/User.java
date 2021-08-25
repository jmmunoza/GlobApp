package com.globapp.globapp.classes;

import java.util.ArrayList;

public class User {
    private String                 meID;
    private String                 meName;
    private String                 meDescription;
    private int                    meImage;
    private int                    meCoverImage;
    private ArrayList<News>        meNews;
    private ArrayList<Recognition> meRecognitions;

    public User(String meID,      String meName,          String meDescription,  int meImage,
                int meCoverImage, ArrayList<News> meNews, ArrayList<Recognition> meRecognitions){

        this.meID           = meID;
        this.meName         = meName;
        this.meDescription  = meDescription;
        this.meImage        = meImage;
        this.meCoverImage   = meCoverImage;
        this.meNews         = meNews;
        this.meRecognitions = meRecognitions;
    }

    public String getMeID() {
        return meID;
    }

    public ArrayList<News> getMeNews() {
        return meNews;
    }

    public ArrayList<Recognition> getMeRecognitions() {
        return meRecognitions;
    }

    public int getMeCoverImage() {
        return meCoverImage;
    }

    public int getMeImage() {
        return meImage;
    }

    public String getMeDescription() {
        return meDescription;
    }

    public String getMeName() {
        return meName;
    }
}
