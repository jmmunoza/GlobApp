package com.globapp.globapp.classes;

import android.net.Uri;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

public class NewsHeadline extends News {
    private String newsTitle;

    public NewsHeadline(ObjectId newsID,       String newsContent,
                        Date newsDate,         Uri newsImage,
                        int newsLikes,         ArrayList<Document> newsComments,
                        boolean newsUserLiked, ObjectId newsUserOwner,
                        String  newsTitle) {
        super(newsID, newsContent, newsDate, newsImage, newsLikes, newsComments, newsUserLiked, newsUserOwner);
        this.newsTitle = newsTitle;
    }

    public String getNewsTitle() {
        return newsTitle;
    }
}
