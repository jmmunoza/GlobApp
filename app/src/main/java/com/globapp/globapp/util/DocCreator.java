package com.globapp.globapp.util;

import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.IUserSessionController;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Calendar;

public class DocCreator {
    public static Document createComment(String commentContent, ObjectId newsID){
        IUserSessionController iUserSessionController = new UserSessionController();
        ObjectId userSessionID = new ObjectId(iUserSessionController.getUserSessionID());

        return  new Document()
                .append("content", commentContent)
                .append("date", Calendar.getInstance().getTime())
                .append("user_id", userSessionID)
                .append("news_id", newsID);
    }

    public static Document createRecognitionNews(ObjectId userRecognizedID, String newsContent){
        IUserSessionController iUserSessionController = new UserSessionController();
        ObjectId userSessionID = new ObjectId(iUserSessionController.getUserSessionID());

        return new Document()
                .append("content", newsContent)
                .append("user_owner_id", userSessionID)
                .append("likes", new ArrayList<ObjectId>())
                .append("comments", 0)
                .append("date", Calendar.getInstance().getTime())
                .append("comments", new ArrayList<Document>())
                .append("user_recognized_id", userRecognizedID);
    }
}
