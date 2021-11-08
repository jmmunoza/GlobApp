package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.factories.NewsFactory;
import com.globapp.globapp.data.listeners.OnNewsListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsLoadedListener;
import com.globapp.globapp.data.local.LocalDB;
import com.globapp.globapp.data.services.INewsGetter;
import com.globapp.globapp.model.News;

import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class NewsGetterMongo implements INewsGetter {
    private static final int DESCENDING = -1;

    private final MongoCollection<Document> newsCollection;

    public NewsGetterMongo(){
        newsCollection = MongoDB.getNewsCollection();
    }

    @Override
    public void getLatestNews(ArrayList<ObjectId> exceptedIDs, OnNewsListLoadedListener onNewsListLoadedListener) {
        ArrayList<News> newsList = new ArrayList<>();

        // Except news IDs
        Document exceptIdsQuery = new Document("_id", new Document("$nin", exceptedIDs));

        // Sort by date
        Document descendingDate  = new Document("date", DESCENDING);

        newsCollection.find(exceptIdsQuery).sort(descendingDate).limit(3).iterator().getAsync(result -> {
            if(result.isSuccess()){
                MongoCursor<Document> newsIterator = result.get();
                while (newsIterator.hasNext()){
                    Document document = newsIterator.next();
                    News news = NewsFactory.DocumentToNews(document);
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
            if(result.isSuccess() && result.get() != null){
                Document document = result.get();
                News news = NewsFactory.DocumentToNews(document);
                onNewsLoadedListener.onNewsLoaded(news);
            }
        });
    }
}