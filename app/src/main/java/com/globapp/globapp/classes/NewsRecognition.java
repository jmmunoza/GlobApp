package com.globapp.globapp.classes;

import android.net.Uri;

import org.bson.types.ObjectId;

public class NewsRecognition extends News {
    private User newsUserRecognized;

    public NewsRecognition(ObjectId newsID, String newsContent, Uri newsImage, int newsLikes, int newsComments, boolean newsUserLiked, User newsUserOwner, User newsUserRecognized) {
        super(newsID, newsContent, newsImage, newsLikes, newsComments, newsUserLiked, newsUserOwner);
        this.newsUserRecognized = newsUserRecognized;
    }

    public User getNewsUserRecognized() {
        return newsUserRecognized;
    }
}
