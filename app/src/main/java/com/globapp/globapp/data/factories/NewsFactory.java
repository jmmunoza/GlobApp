package com.globapp.globapp.data.factories;

import android.net.Uri;

import com.globapp.globapp.GlobAppApplication;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.model.News;
import com.globapp.globapp.util.ImageConverter;

import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;

public class NewsFactory {
    public static News DocumentToNews(Document document){

        byte[] newsImage = null;
        if(document.get("image", Binary.class) != null)
            newsImage = document.get("image", Binary.class).getData();

        return new News(
                document.getObjectId("_id"),
                document.getString("title"),
                document.getString("content"),
                document.getDate("date"),
                newsImage,
                document.getList("likes",    ObjectId.class, new ArrayList<>()).size(),
                document.getList("comments", Document.class, new ArrayList<>()).size(),
                document.getObjectId("ownerID"),
                document.getObjectId("recognizedID"));
    }

    public static Document createRecognitionNews(ObjectId userRecognizedID, String newsContent, Uri imageUri){
        ObjectId userSessionID = UserSessionController.getUserSessionID();

        return new Document()
                .append("content", newsContent)
                .append("ownerID", userSessionID)
                .append("likes", new ArrayList<ObjectId>())
                .append("comments", 0)
                .append("date", Calendar.getInstance().getTime())
                .append("comments", new ArrayList<Document>())
                .append("image", ImageConverter.UriToByteArray(imageUri))
                .append("recognizedID", userRecognizedID);
    }
}
