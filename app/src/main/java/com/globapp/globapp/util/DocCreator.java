package com.globapp.globapp.util;

import com.globapp.globapp.data.local.UserSessionController;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Calendar;

public class DocCreator {
    public static Document createComment(String commentContent, ObjectId newsID){
        ObjectId userSessionID = UserSessionController.getUserSessionID();

        return  new Document()
                .append("content", commentContent)
                .append("date", Calendar.getInstance().getTime())
                .append("userID", userSessionID)
                .append("newsID", newsID);
    }
}