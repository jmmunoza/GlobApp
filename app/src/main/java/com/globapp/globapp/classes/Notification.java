package com.globapp.globapp.classes;

import org.bson.types.ObjectId;

public class Notification {

    private News notificationNews;

    public Notification(News notificationNews){
        this.notificationNews    = notificationNews;
    }

    public News getNotificationNews() {
        return notificationNews;
    }

}
