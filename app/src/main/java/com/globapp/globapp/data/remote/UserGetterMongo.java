package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.listeners.OnUserLoadedListener;
import com.globapp.globapp.data.services.IUserGetter;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.DocConverter;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.mongodb.mongo.MongoCollection;

public class UserGetterMongo implements IUserGetter {
    private final MongoCollection<Document> userCollection;

    public UserGetterMongo(){
        userCollection = MongoDB.getInstance().getCollection("user");
    }

    public void getUser(ObjectId userID, OnUserLoadedListener onUserLoadedListener){
        Document userQuery = new Document("_id", userID);
        userCollection.findOne(userQuery).getAsync(userData -> {
            if(userData.isSuccess()) {
                User user = DocConverter.documentToUser(userData.get());
                onUserLoadedListener.onUserLoaded(user);
            }
        });
    }
}
