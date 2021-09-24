package com.globapp.globapp.classes;

public class Notification {

    private String  notificationID;
    private News    notificationNews;

    public Notification(String notificationID, News notificationNews){
        this.notificationID      = notificationID;
        this.notificationNews    = notificationNews;
    }

    public News getNotificationNews() {
        return notificationNews;
    }

    public String getNotificationID() {
        return notificationID;
    }

}
