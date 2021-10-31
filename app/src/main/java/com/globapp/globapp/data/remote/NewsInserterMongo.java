package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.services.INewsInserter;
import com.globapp.globapp.model.News;
import com.globapp.globapp.util.DocConverter;

import org.bson.Document;

import io.realm.mongodb.mongo.MongoCollection;

public class NewsInserterMongo implements INewsInserter {
    private final MongoCollection<Document> newsCollection;

    public NewsInserterMongo(){
        newsCollection = MongoDB.getInstance().getCollection("news");
    }

    @Override
    public void insert(News news) {
        Document document = DocConverter.newsToDocument(news);
        newsCollection.insertOne(document).getAsync(result -> {

        });
    }
}
