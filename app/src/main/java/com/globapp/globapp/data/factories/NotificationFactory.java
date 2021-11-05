package com.globapp.globapp.data.factories;

import com.globapp.globapp.model.Notification;

import org.bson.Document;

public class NotificationFactory {
    public static Notification documentToNotification(Document document){
        return new Notification(
                document.getDate("date"),
                document.getObjectId("newsID"),
                document.getBoolean("seen")
        );
    }
}
