package com.globapp.globapp.classes;

public class NewsRecognition extends News {
    private User newsUserRecognized;

    public NewsRecognition(String newsID, String newsContent, int newsImage, User newsUserOwner, User newsUserRecognized) {
        super(newsID, newsContent, newsImage, newsUserOwner);
        super.setNewsIsRecognition(true);

        this.newsUserRecognized = newsUserRecognized;
    }

    public User getNewsUserRecognized() {
        return newsUserRecognized;
    }
}
