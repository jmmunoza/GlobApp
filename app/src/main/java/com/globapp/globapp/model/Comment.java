package com.globapp.globapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;

@Entity
public class Comment {
    @ColumnInfo(name = "content")
    private String   commentContent;

    @ColumnInfo(name = "date")
    private Date     commentDate;

    @ColumnInfo(name = "userID")
    private String   commentUser;

    @ColumnInfo(name = "newsID")
    private String   commentNews;

    public Comment(String commentContent, Date commentDate, String commentUser, String commentNews){
        this.commentContent = commentContent;
        this.commentUser    = commentUser;
        this.commentDate    = commentDate;
        this.commentNews    = commentNews;
    }

    public String getCommentNews() {
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

    public void setCommentNews(String commentNews) {
        this.commentNews = commentNews;
    }
}
