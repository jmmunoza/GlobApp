package com.globapp.globapp.classes;

public class News {

    private String newsID;
    private String newsContent;
    private int    newsImage;
    private int    newsUserImage;
    private String newsUserName;

    public News(String newsID, String newsContent, int newsImage, String newsUserName, int newsUserImage){
        this.newsID        = newsID;
        this.newsContent   = newsContent;
        this.newsImage     = newsImage;
        this.newsUserImage = newsUserImage;
        this.newsUserName  = newsUserName;
    }

    public int getNewsImage() {
        return newsImage;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String getNewsID() {
        return newsID;
    }

    public int getNewsUserImage() {
        return newsUserImage;
    }

    public String getNewsUserName() {
        return newsUserName;
    }
}
