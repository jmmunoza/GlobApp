package com.globapp.globapp.classes;

import android.net.Uri;

import java.util.ArrayList;

public class User {
    private String                 meID;
    private String                 meName;
    private String                 meDescription;
    private Uri                    meImage;
    private Uri                    meCoverImage;
    private int                    meCredits;
    private int                    meStars;
    private ArrayList<News>        meNews;
    private ArrayList<Recognition> meRecognitions;

    public User(String meID,      String meName,          String meDescription,  Uri meImage,
                Uri meCoverImage, ArrayList<News> meNews, ArrayList<Recognition> meRecognitions,
                int meCredits,    int meStars){

        this.meCredits      = meCredits;
        this.meStars        = meStars;
        this.meID           = meID;
        this.meName         = meName;
        this.meDescription  = meDescription;
        this.meImage        = meImage;
        this.meCoverImage   = meCoverImage;
        this.meNews         = meNews;
        this.meRecognitions = meRecognitions;
    }

    public User(String meID, String meName, String meDescription,  Uri meImage, Uri meCoverImage){

        this.meCredits      = 0;
        this.meStars        = 0;
        this.meID           = meID;
        this.meName         = meName;
        this.meDescription  = meDescription;
        this.meImage        = meImage;
        this.meCoverImage   = meCoverImage;
        this.meNews         = new ArrayList<>();
        this.meRecognitions = new ArrayList<>();
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

    public void addMeNews(News news){
        meNews.add(news);
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
