package com.globapp.globapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.globapp.globapp.util.DateConverter;
import com.globapp.globapp.util.ObjectIdConverter;

import org.bson.types.Binary;
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
    private int newsLikes;

    @ColumnInfo(name = "comments")
    private int newsComments;

    @ColumnInfo(name = "ownerID")
    private ObjectId newsUserOwner;

    @ColumnInfo(name = "recognizedID")
    private ObjectId newsUserRecognized;

    @ColumnInfo(name = "image",typeAffinity = ColumnInfo.BLOB)
    private byte[] newsImage;

    public News(ObjectId newsID, String newsTitle, String newsContent, Date newsDate, byte[] newsImage, int newsLikes, int newsComments, ObjectId newsUserOwner, ObjectId newsUserRecognized){
        this.newsID             = newsID;
        this.newsContent        = newsContent;
        this.newsImage          = newsImage;
        this.newsUserOwner      = newsUserOwner;
        this.newsComments       = newsComments;
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

    public int getNewsComments() {
        return newsComments;
    }

    public int getNewsLikes() {
        return newsLikes;
    }

    public byte[] getNewsImage() {
        return newsImage;
    }

    public String getNewsContent() {
        return newsContent;
    }

    @NonNull
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

    public void setNewsImage(Binary newsImage2) {
        if(newsImage2 != null)
        this.newsImage = newsImage2.getData();
    }

    public void setNewsLikes(int newsLikes) {
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

    public void setNewsComments(int newsComments) {
        this.newsComments = newsComments;
    }
}
