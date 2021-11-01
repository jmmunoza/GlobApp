package com.globapp.globapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private long     roomID;

    @ColumnInfo(name = "content")
    private String   commentContent;

    @ColumnInfo(name = "date")
    private String   commentDate;

    @ColumnInfo(name = "userID")
    private String   commentUser;

    @ColumnInfo(name = "newsID")
    private String   commentNews;

    public Comment(String commentContent, String commentDate, String commentUser, String commentNews){
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

    public String getCommentNews() {
        return commentNews;
    }

    public String getCommentDate() {
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

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public void setCommentNews(String commentNews) {
        this.commentNews = commentNews;
    }
}
