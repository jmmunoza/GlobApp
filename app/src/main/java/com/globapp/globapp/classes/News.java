package com.globapp.globapp.classes;

public class News {

    private String  newsID;
    private String  newsContent;
    private int     newsImage;
    private User    newsUserOwner;
    private boolean newsIsRecognition;

    public News(String newsID, String newsContent, int newsImage, User newsUserOwner){
        this.newsID            = newsID;
        this.newsContent       = newsContent;
        this.newsImage         = newsImage;
        this.newsUserOwner     = newsUserOwner;
        this.newsIsRecognition = false;
    }

    public boolean isNewsIsRecognition() {
        return newsIsRecognition;
    }

    public void setNewsIsRecognition(boolean newsIsRecognition) {
        this.newsIsRecognition = newsIsRecognition;
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

    public User getNewsUserOwner() {
        return newsUserOwner;
    }
}
