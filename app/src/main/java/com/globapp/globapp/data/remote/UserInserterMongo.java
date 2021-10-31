package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.services.IUserInserter;
import com.globapp.globapp.model.User;

import org.bson.Document;

import io.realm.mongodb.mongo.MongoCollection;

public class UserInserterMongo implements IUserInserter {
    private final MongoCollection<Document> userCollection;

    public UserInserterMongo(){
        userCollection = MongoDB.getInstance().getCollection("user");
    }

    @Override
    public void insert(User user) {
        Document document = new Document();
        userCollection.insertOne(document).getAsync(result -> {

        });
    }
}
