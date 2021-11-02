package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.services.INewsInserter;
import com.globapp.globapp.data.services.INotifier;
import com.globapp.globapp.model.News;
import com.globapp.globapp.util.DocConverter;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.mongodb.mongo.MongoCollection;

public class NewsInserterMongo implements INewsInserter {
    private final MongoCollection<Document> newsCollection;

    public NewsInserterMongo(){
        newsCollection = MongoDB.getNewsCollection();
    }

    @Override
    public void insert(News news) {
        Document document = DocConverter.newsToDocument(news);
        newsCollection.insertOne(document).getAsync(result -> {
            if(result.isSuccess()){
                ObjectId newsInsertedID = result.get().getInsertedId().asObjectId().getValue();
                //Date newsDate = news.getNewsDate().dat.getDate("date");

                //INotifier iNotifier = new NotifierMongo();
                //iNotifier.notify(new ObjectId(news.getNewsID()));
            }
        });
    }
}
