package com.globapp.globapp.classes;

import android.net.Uri;

public class NewsRecognition extends News {
    private User newsUserRecognized;

    public NewsRecognition(String newsID, String newsContent, Uri newsImage, User newsUserOwner, User newsUserRecognized) {
        super(newsID, newsContent, newsImage, newsUserOwner);
        super.setNewsIsRecognition(true);

        this.newsUserRecognized = newsUserRecognized;
    }

    public User getNewsUserRecognized() {
        return newsUserRecognized;
    }
}
