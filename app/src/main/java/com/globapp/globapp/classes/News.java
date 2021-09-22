package com.globapp.globapp.classes;

import android.net.Uri;

import java.util.ArrayList;

public class News {

    private String  newsID;
    private String  newsContent;
    private Uri     newsImage;
    private int     newsLikes;
    private User    newsUserOwner;
    private boolean newsIsRecognition;
    private ArrayList<Comment> newsComments;

    public News(String newsID, String newsContent, Uri newsImage, User newsUserOwner){
        this.newsID            = newsID;
        this.newsContent       = newsContent;
        this.newsImage         = newsImage;
        this.newsUserOwner     = newsUserOwner;
        this.newsIsRecognition = false;
        this.newsLikes         = 0;
        this.newsComments      = new ArrayList<>();
    }

    public ArrayList<Comment> getNewsComments() {
        return newsComments;
    }

    public void addComment(Comment comment){
        newsComments.add(comment);
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

    public boolean isNewsIsRecognition() {
        return newsIsRecognition;
    }

    public void setNewsIsRecognition(boolean newsIsRecognition) {
        this.newsIsRecognition = newsIsRecognition;
    }

    public Uri getNewsImage() {
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
