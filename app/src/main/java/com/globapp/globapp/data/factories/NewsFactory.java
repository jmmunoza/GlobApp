package com.globapp.globapp.data.factories;

import com.globapp.globapp.model.News;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class NewsFactory {
    public static News DocumentToNews(Document document){
        return new News(
                document.getObjectId("_id"),
                document.getString("title"),
                document.getString("content"),
                document.getDate("date"),
                document.getList("likes",    ObjectId.class, new ArrayList<>()).size(),
                document.getList("comments", Document.class, new ArrayList<>()).size(),
                document.getObjectId("ownerID"),
                document.getObjectId("recognizedID"));
    }
}
