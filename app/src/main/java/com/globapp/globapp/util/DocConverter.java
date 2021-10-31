package com.globapp.globapp.util;

import android.widget.Adapter;

import com.globapp.globapp.model.Admin;
import com.globapp.globapp.model.Comment;
import com.globapp.globapp.model.News;
import com.globapp.globapp.model.User;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Calendar;

public class DocConverter {
    public static Document newsToDocument(News news){
        return new Document()
                .append("title", news.getNewsTitle())
                .append("content", news.getNewsContent())
                .append("ownerID", new ObjectId(news.getNewsUserOwner()))
                .append("likes", ArrayStringConverter.fromString(news.getNewsLikes()))
                .append("comments", 0)
                .append("date", news.getNewsDate())
                .append("recognizedID", new ObjectId(news.getNewsUserRecognized()));
    }

    public static News DocumentToNews(Document document){
        return new News(
                document.getObjectId("_id").toString(),
                document.getString("title"),
                document.getString("content"),
                document.getDate("date"),
                null,
                ArrayStringConverter.fromArrayList(new ArrayList<>(document.getList(
                        "likes", ObjectId.class, new ArrayList<>()))),
                document.getObjectId("ownerID").toString(),
                document.getObjectId("recognizedID").toString());
    }

    public static Document userToDocument(User user){
        return new Document()
                .append("_id",         new ObjectId(user.getUserID()))
                .append("firstName",   user.getUserFirstName())
                .append("secondName",  user.getUserSecondName())
                .append("lastName",    user.getUserLastName())
                .append("description", user.getUserDescription())
                .append("credits",     user.getUserCredits())
                .append("stars",       user.getUserImage());
    }

    public static User documentToUser(Document document){
        return new User(
                document.getObjectId("_id").toString(),
                document.getString("firstName"),
                document.getString("secondName"),
                document.getString("lastName"),
                document.getString("description"),
                null,
                null,
                document.getInteger("credits", 0),
                document.getInteger("stars", 0));
    }

    public static Comment documentToComment(Document document){
        return new Comment(
                document.getString("content"),
                document.getDate("date"),
                document.getObjectId("user_id").toString()

        );
    }
}
