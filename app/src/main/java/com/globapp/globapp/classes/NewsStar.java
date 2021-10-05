package com.globapp.globapp.classes;

import android.net.Uri;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

public class NewsStar extends News {
    public NewsStar(ObjectId newsID,       String newsContent,
                    Date newsDate,         Uri newsImage,
                    int newsLikes,         ArrayList<Document> newsComments,
                    boolean newsUserLiked, ObjectId newsUserOwner) {
        super(newsID, newsContent, newsDate, newsImage, newsLikes, newsComments, newsUserLiked, newsUserOwner);
    }
}
