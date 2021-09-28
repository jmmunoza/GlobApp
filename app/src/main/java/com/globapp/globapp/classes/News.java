package com.globapp.globapp.classes;

import android.net.Uri;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class News {

    private ObjectId newsID;
    private String   newsContent;
    private Uri      newsImage;
    private int      newsLikes;
    private int      newsComments;
    private boolean  newsUserLiked;
    private User     newsUserOwner;

    public News(ObjectId newsID, String newsContent, Uri newsImage, int newsLikes, int newsComments, boolean newsUserLiked, User newsUserOwner){
        this.newsID            = newsID;
        this.newsContent       = newsContent;
        this.newsImage         = newsImage;
        this.newsUserOwner     = newsUserOwner;
        this.newsLikes         = newsLikes;
        this.newsUserLiked     = newsUserLiked;
        this.newsComments      = newsComments;
    }

    public void setNewsUserLiked(boolean newsUserLiked) {
        this.newsUserLiked = newsUserLiked;
    }

    public boolean getNewsUserLiked() {
        return newsUserLiked;
    }

    public int getNewsComments() {
        return newsComments;
    }

    public void addLike(){
        newsLikes++;
    }

    public void removeLike(){
        newsLikes--;
    }

    public int getNewsLikes() {
        return newsLikes;
    }

    public Uri getNewsImage() {
        return newsImage;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public ObjectId getNewsID() {
        return newsID;
    }

    public User getNewsUserOwner() {
        return newsUserOwner;
    }
}
