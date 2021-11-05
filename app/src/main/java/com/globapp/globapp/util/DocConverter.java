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
}
