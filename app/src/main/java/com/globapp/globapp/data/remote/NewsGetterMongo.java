package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.listeners.OnNewsListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsLoadedListener;
import com.globapp.globapp.data.services.INewsGetter;
import com.globapp.globapp.model.News;
import com.globapp.globapp.util.DocConverter;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class NewsGetterMongo implements INewsGetter {
    private static final int DESCENDING = -1;
    private static final int ASCENDING  = 1;

    private final MongoCollection<Document> newsCollection;

    public NewsGetterMongo(){
        newsCollection = MongoDB.getNewsCollection();
    }

    @Override
    public void getLatestNews(OnNewsListLoadedListener onNewsListLoadedListener) {
        ArrayList<News> newsList = new ArrayList<>();
        Document descendingDate  = new Document("date", DESCENDING);

        newsCollection.find().sort(descendingDate).limit(50).iterator().getAsync(result -> {
            if(result.isSuccess()){
                MongoCursor<Document> newsIterator = result.get();
                while (newsIterator.hasNext()){
                    Document document = newsIterator.next();
                    News news = DocConverter.DocumentToNews(document);
                    newsList.add(news);
                }
                onNewsListLoadedListener.onNewsListLoaded(newsList);
            }
        });
    }

    @Override
    public void getNews(ObjectId newsID, OnNewsLoadedListener onNewsLoadedListener) {
        Document newsQuery = new Document("_id", newsID);
        newsCollection.findOne(newsQuery).getAsync(result -> {
            if(result.isSuccess()){
                Document document = result.get();
                News news = DocConverter.DocumentToNews(document);
                onNewsLoadedListener.onNewsLoaded(news);
            }
        });
    }
}
