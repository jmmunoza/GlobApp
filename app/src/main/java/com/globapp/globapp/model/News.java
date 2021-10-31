package com.globapp.globapp.model;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class News {

    @PrimaryKey(autoGenerate = false)
    private String   newsID;

    @ColumnInfo(name = "title")
    private String   newsTitle;

    @ColumnInfo(name = "content")
    private String   newsContent;

    private Uri      newsImage;

    @ColumnInfo(name = "likes")
    private String newsLikes;

    @ColumnInfo(name = "date")
    private Date     newsDate;

    @ColumnInfo(name = "ownerID")
    private String newsUserOwner;

    @ColumnInfo(name = "recognizedID")
    private String newsUserRecognized;

    public News(String newsID,        String newsTitle,
                String newsContent,   Date newsDate,
                Uri newsImage,        String newsLikes,
                String newsUserOwner, String newsUserRecognized){
        this.newsID             = newsID;
        this.newsContent        = newsContent;
        this.newsImage          = newsImage;
        this.newsUserOwner      = newsUserOwner;
        this.newsLikes          = newsLikes;
        this.newsDate           = newsDate;
        this.newsTitle          = newsTitle;
        this.newsUserRecognized = newsUserRecognized;
    }

    public Date getNewsDate() {
        return newsDate;
    }


    public String getNewsLikes() {
        return newsLikes;
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

    public String getNewsUserOwner() {
        return newsUserOwner;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsUserRecognized() {
        return newsUserRecognized;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public void setNewsImage(Uri newsImage) {
        this.newsImage = newsImage;
    }

    public void setNewsLikes(String newsLikes) {
        this.newsLikes = newsLikes;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public void setNewsUserOwner(String newsUserOwner) {
        this.newsUserOwner = newsUserOwner;
    }

    public void setNewsUserRecognized(String newsUserRecognized) {
        this.newsUserRecognized = newsUserRecognized;
    }
}
