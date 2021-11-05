package com.globapp.globapp.data.factories;

import com.globapp.globapp.model.Comment;

import org.bson.Document;

public class CommentFactory {
    public static Comment documentToComment(Document document){
        return new Comment(
                document.getString("content"),
                document.getDate("date"),
                document.getObjectId("userID").toString(),
                document.getObjectId("newsID")
        );
    }
}
