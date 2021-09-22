package com.globapp.globapp.classes;

public class Comment {
    private String  commentID;
    private String  commentContent;
    private User    commentUser;

    public Comment(String commentID, String commentContent, User commentUser){
        this.commentContent = commentContent;
        this.commentID      = commentID;
        this.commentUser    = commentUser;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getCommentID() {
        return commentID;
    }

    public User getCommentUser() {
        return commentUser;
    }
}
