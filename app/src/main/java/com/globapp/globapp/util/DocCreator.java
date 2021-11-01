package com.globapp.globapp.util;

import com.globapp.globapp.data.local.UserSessionController;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Calendar;

public class DocCreator {
    public static Document createComment(String commentContent, ObjectId newsID){
        ObjectId userSessionID = new ObjectId(UserSessionController.getUserSessionID());

        return  new Document()
                .append("content", commentContent)
                .append("date", Calendar.getInstance().getTime())
                .append("userID", userSessionID)
                .append("newsID", newsID);
    }

    public static Document createRecognitionNews(ObjectId userRecognizedID, String newsContent){
        ObjectId userSessionID = new ObjectId(UserSessionController.getUserSessionID());

        return new Document()
                .append("content", newsContent)
                .append("ownerID", userSessionID)
                .append("likes", new ArrayList<ObjectId>())
                .append("comments", 0)
                .append("date", Calendar.getInstance().getTime())
                .append("comments", new ArrayList<Document>())
                .append("recognizedID", userRecognizedID);
    }
}
