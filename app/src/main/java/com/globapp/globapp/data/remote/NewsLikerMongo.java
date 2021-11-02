package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.listeners.OnNewsLikedListener;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.INewsLiker;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.mongodb.mongo.MongoCollection;

public class NewsLikerMongo implements INewsLiker {
    private final MongoCollection<Document> newsCollection;

    public NewsLikerMongo(){
        this.newsCollection = MongoDB.getNewsCollection();
    }

    @Override
    public void like(ObjectId newsID, OnNewsLikedListener onNewsLikedListener) {

        ObjectId userSessionID = new ObjectId(UserSessionController.getUserSessionID());

        ArrayList<ObjectId> likesListQuery = new ArrayList<>(
                Collections.singleton(userSessionID));

        Document isUserLikedQuery = new Document("_id", newsID)
                .append("likes",
                        new Document("$in", likesListQuery));


        newsCollection.findOne(isUserLikedQuery).getAsync(result -> {
            if(result.isSuccess()){
                Document newsQuery = new Document("_id", newsID);
                if(result.get() == null){
                    // User wants to like

                    // The insertion Doc for the user like
                    Document likeInsertion = new Document("$addToSet", new Document("likes", userSessionID));

                    newsCollection.findOneAndUpdate(newsQuery, likeInsertion).getAsync(like -> {
                        if(like.isSuccess()){
                            onNewsLikedListener.liked();
                        }
                    });
                } else {
                    // User wants to dislike
                    // The remove Doc for the user like
                    Document likeRemove = new Document("$pull", new Document("likes", userSessionID));
                    newsCollection.findOneAndUpdate(newsQuery, likeRemove).getAsync(dislike -> {
                        if(dislike.isSuccess()){
                            onNewsLikedListener.disliked();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getIsLiked(ObjectId newsID, OnNewsLikedListener onNewsLikedListener) {
        ObjectId userSessionID = new ObjectId(UserSessionController.getUserSessionID());

        ArrayList<ObjectId> likesListQuery = new ArrayList<>(
                Collections.singleton(userSessionID));

        Document isUserLikedQuery = new Document("_id", newsID)
                .append("likes",
                        new Document("$in", likesListQuery));

        newsCollection.findOne(isUserLikedQuery).getAsync(result -> {
            if(result.isSuccess()){
                if(result.get() == null){
                    onNewsLikedListener.disliked();
                } else {
                    onNewsLikedListener.liked();
                }
            }
        });
    }
}
