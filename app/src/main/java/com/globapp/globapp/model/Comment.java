package com.globapp.globapp.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class Comment {
    private String   commentContent;
    private Date     commentDate;
    private String   commentUser;

    public Comment(String commentContent, Date commentDate, String commentUser){
        this.commentContent = commentContent;
        this.commentUser    = commentUser;
        this.commentDate    = commentDate;
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
}
