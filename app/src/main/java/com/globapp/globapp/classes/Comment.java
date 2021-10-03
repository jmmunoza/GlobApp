package com.globapp.globapp.classes;

import org.bson.types.ObjectId;

import java.util.Date;

public class Comment {
    private ObjectId commentID;
    private String   commentContent;
    private Date     commentDate;
    private ObjectId commentUser;

    public Comment(ObjectId commentID, String commentContent, Date commentDate, ObjectId commentUser){
        this.commentContent = commentContent;
        this.commentID      = commentID;
        this.commentUser    = commentUser;
        this.commentDate    = commentDate;
    }


    public Date getCommentDate() {
        return commentDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public ObjectId getCommentID() {
        return commentID;
    }

    public ObjectId getCommentUser() {
        return commentUser;
    }
}
