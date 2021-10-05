package com.globapp.globapp.classes;

import org.bson.types.ObjectId;

import java.util.Date;

public class Comment {
    private String   commentContent;
    private Date     commentDate;
    private ObjectId commentUser;

    public Comment(String commentContent, Date commentDate, ObjectId commentUser){
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

    public ObjectId getCommentUser() {
        return commentUser;
    }
}
