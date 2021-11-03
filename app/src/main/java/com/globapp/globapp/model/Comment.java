package com.globapp.globapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.globapp.globapp.util.DateConverter;
import com.globapp.globapp.util.ObjectIdConverter;

import org.bson.types.ObjectId;

import java.util.Date;

@Entity
@TypeConverters({DateConverter.class, ObjectIdConverter.class})
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private long     roomID;

    @ColumnInfo(name = "content")
    private String   commentContent;

    @ColumnInfo(name = "date")
    private Date     commentDate;

    @ColumnInfo(name = "userID")
    private String   commentUser;

    @ColumnInfo(name = "newsID")
    private ObjectId commentNews;

    public Comment(String commentContent, Date commentDate, String commentUser, ObjectId commentNews){
        this.commentContent = commentContent;
        this.commentUser    = commentUser;
        this.commentDate    = commentDate;
        this.commentNews    = commentNews;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public ObjectId getCommentNews() {
        return commentNews;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public void setCommentNews(ObjectId commentNews) {
        this.commentNews = commentNews;
    }
}
