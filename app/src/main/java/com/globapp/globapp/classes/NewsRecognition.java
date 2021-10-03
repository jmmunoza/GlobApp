package com.globapp.globapp.classes;

import android.net.Uri;

import org.bson.types.ObjectId;

import java.util.Date;

public class NewsRecognition extends News {
    private ObjectId newsUserRecognized;

    public NewsRecognition(ObjectId newsID,       String newsContent,
                           Date newsDate,         Uri newsImage,
                           int newsLikes,         int newsComments,
                           boolean newsUserLiked, ObjectId newsUserOwner,
                           ObjectId newsUserRecognized) {
        super(newsID, newsContent, newsDate, newsImage, newsLikes, newsComments, newsUserLiked, newsUserOwner);
        this.newsUserRecognized = newsUserRecognized;
    }

    public ObjectId getNewsUserRecognized() {
        return newsUserRecognized;
    }
}
