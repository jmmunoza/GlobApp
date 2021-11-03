package com.globapp.globapp.util;

import com.globapp.globapp.model.Comment;
import com.globapp.globapp.model.News;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.model.User;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class DocConverter {
    public static Document newsToDocument(News news){
        return new Document()
                .append("title",        news.getNewsTitle())
                .append("content",      news.getNewsContent())
                .append("ownerID",      news.getNewsUserOwner())
                .append("likes",        news.getNewsLikes())
                .append("comments",     news.getNewsComments())
                .append("date",         news.getNewsDate())
                .append("recognizedID", news.getNewsUserRecognized());
    }

    public static News DocumentToNews(Document document){
        return new News(
                document.getObjectId("_id"),
                document.getString("title"),
                document.getString("content"),
                document.getDate("date"),
                //null,
                document.getList("likes",    ObjectId.class, new ArrayList<>()).size(),
                document.getList("comments", Document.class, new ArrayList<>()).size(),
                document.getObjectId("ownerID"),
                document.getObjectId("recognizedID"));
    }

    public static Document userToDocument(User user){
        return new Document()
                .append("_id",         user.getUserID())
                .append("firstName",   user.getUserFirstName())
                .append("secondName",  user.getUserSecondName())
                .append("lastName",    user.getUserLastName())
                .append("description", user.getUserDescription())
                .append("credits",     user.getUserCredits())
                .append("stars",       user.getUserImage());
    }

    public static User documentToUser(Document document){
        return new User(
                document.getObjectId("_id"),
                document.getString("firstName"),
                document.getString("secondName"),
                document.getString("lastName"),
                document.getString("description"),
            //    null,
             //   null,
                document.getInteger("credits", 0),
                document.getInteger("stars", 0));
    }

    public static Comment documentToComment(Document document){
        return new Comment(
                document.getString("content"),
                document.getDate("date"),
                document.getObjectId("userID").toString(),
                document.getObjectId("newsID")
        );
    }

    public static Notification documentToNotification(Document document){
        return new Notification(
                document.getDate("date"),
                document.getObjectId("newsID"),
                document.getBoolean("seen")
        );
    }
}
