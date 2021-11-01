package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.listeners.OnNewsLikedListener;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.INewsLiker;
import com.globapp.globapp.data.services.IUserSessionController;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoCollection;

public class NewsLikerMongo implements INewsLiker {
    private final MongoCollection<Document> newsCollection;

    public NewsLikerMongo(){
        this.newsCollection = MongoDB.getNewsCollection();
    }

    @Override
    public void like(ObjectId newsID, OnNewsLikedListener onNewsLikedListener) {
        Document newsQuery = new Document("_id", newsID);
        newsCollection.findOne(newsQuery).getAsync(result -> {
            if(result.isSuccess()){

                ArrayList<ObjectId> listLikes = new ArrayList<>(
                                result.get().getList("likes",
                                ObjectId.class,
                                new ArrayList<>()));

                IUserSessionController iUserSessionController = new UserSessionController();
                ObjectId userSessionID = new ObjectId(iUserSessionController.getUserSessionID());

                boolean isLiked = listLikes.contains(userSessionID);
                if(isLiked){
                    listLikes.remove(userSessionID);
                } else {
                    listLikes.add(userSessionID);
                }

                Document newsUpdate = result.get().append("likes", listLikes);
                newsCollection.findOneAndUpdate(newsQuery, newsUpdate).getAsync(result1 -> {
                    if(result1.isSuccess()){
                        if(isLiked){
                            onNewsLikedListener.disliked(listLikes.size());
                        } else {
                            onNewsLikedListener.liked(listLikes.size());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void getIsLiked(ObjectId newsID, OnNewsLikedListener onNewsLikedListener) {
        Document newsQuery = new Document("_id", newsID);
        newsCollection.findOne(newsQuery).getAsync(result -> {
            if(result.isSuccess()){

                ArrayList<ObjectId> listLikes = new ArrayList<>(
                        result.get().getList("likes",
                                ObjectId.class,
                                new ArrayList<>()));

                IUserSessionController iUserSessionController = new UserSessionController();
                ObjectId userSessionID = new ObjectId(iUserSessionController.getUserSessionID());

                boolean isLiked = listLikes.contains(userSessionID);
                if(isLiked){
                    onNewsLikedListener.liked(listLikes.size());
                } else {
                    onNewsLikedListener.disliked(listLikes.size());
                }
            }
        });
    }
}
