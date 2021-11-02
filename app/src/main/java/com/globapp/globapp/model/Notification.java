package com.globapp.globapp.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class Notification {

    private ObjectId notificationNews;
    private Date     notificationDate;
    private boolean  notificationSeen;

    public Notification(Date notificationDate, ObjectId notificationNews, boolean notificationSeen){
        this.notificationNews = notificationNews;
        this.notificationSeen = notificationSeen;
        this.notificationDate = notificationDate;
    }

    public boolean isNotificationSeen(){
        return notificationSeen;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public ObjectId getNotificationNews() {
        return notificationNews;
    }
}
