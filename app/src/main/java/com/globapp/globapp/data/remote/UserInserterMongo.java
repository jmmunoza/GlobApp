package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.services.IUserInserter;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.DocConverter;

import org.bson.Document;

import io.realm.mongodb.mongo.MongoCollection;

public class UserInserterMongo implements IUserInserter {
    private final MongoCollection<Document> userCollection;

    public UserInserterMongo(){
        userCollection = MongoDB.getUserCollection();
    }

    @Override
    public void insert(User user) {
        Document document = DocConverter.userToDocument(user);
        userCollection.insertOne(document).getAsync(result -> {

        });
    }
}
