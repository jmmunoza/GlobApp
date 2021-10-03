package com.globapp.globapp.classes;

import org.bson.types.ObjectId;

import java.util.Date;

public class Notification {

    private ObjectId notificationNews;
    private Date     notificationDate;

    public Notification(Date notificationDate, ObjectId notificationNews){
        this.notificationNews = notificationNews;
        this.notificationDate = notificationDate;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public ObjectId getNotificationNews() {
        return notificationNews;
    }
}
