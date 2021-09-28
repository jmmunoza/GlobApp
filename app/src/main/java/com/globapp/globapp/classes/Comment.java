package com.globapp.globapp.classes;

import org.bson.types.ObjectId;

import java.util.Date;

public class Comment {
    private ObjectId commentID;
    //private Date     commentDate;
    private String   commentContent;
    private User     commentUser;

    public Comment(ObjectId commentID, String commentContent, User commentUser){
        this.commentContent = commentContent;
        this.commentID      = commentID;
        this.commentUser    = commentUser;
    }

    /*
    public Date getCommentDate() {
        return commentDate;
    }

     */

    public String getCommentContent() {
        return commentContent;
    }

    public ObjectId getCommentID() {
        return commentID;
    }

    public User getCommentUser() {
        return commentUser;
    }
}
