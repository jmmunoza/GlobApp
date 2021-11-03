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

import java.util.Date;

@Entity
@TypeConverters({DateConverter.class, ObjectIdConverter.class})
public class News {
    @PrimaryKey(autoGenerate = true)
    private long roomID;

    @ColumnInfo(name = "newsID")
    private ObjectId newsID;

    @ColumnInfo(name = "title")
    private String newsTitle;

    @ColumnInfo(name = "content")
    private String newsContent;

    @ColumnInfo(name = "date")
    private Date newsDate;

    @ColumnInfo(name = "likes")
    private String newsLikes;

    @ColumnInfo(name = "ownerID")
    private ObjectId newsUserOwner;

    @ColumnInfo(name = "recognizedID")
    private ObjectId newsUserRecognized;

    @Ignore
    private Uri      newsImage;

    public News(ObjectId newsID, String newsTitle, String newsContent, Date newsDate, /*Uri newsImage,*/ String newsLikes, ObjectId newsUserOwner, ObjectId newsUserRecognized){
        this.newsID             = newsID;
        this.newsContent        = newsContent;
        //this.newsImage          = newsImage;
        this.newsUserOwner      = newsUserOwner;
        this.newsLikes          = newsLikes;
        this.newsDate           = newsDate;
        this.newsTitle          = newsTitle;
        this.newsUserRecognized = newsUserRecognized;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
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

    public ObjectId getNewsID() {
        return newsID;
    }

    public ObjectId getNewsUserOwner() {
        return newsUserOwner;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public ObjectId getNewsUserRecognized() {
        return newsUserRecognized;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public void setNewsID(ObjectId newsID) {
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

    public void setNewsUserOwner(ObjectId newsUserOwner) {
        this.newsUserOwner = newsUserOwner;
    }

    public void setNewsUserRecognized(ObjectId newsUserRecognized) {
        this.newsUserRecognized = newsUserRecognized;
    }
}
