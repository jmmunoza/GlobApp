package com.globapp.globapp.data.factories;

import com.globapp.globapp.model.User;

import org.bson.Document;

public class UserFactory {
    public static User documentToUser(Document document){
        return new User(
                document.getObjectId("_id"),
                document.getString("firstName"),
                document.getString("secondName"),
                document.getString("lastName"),
                document.getString("description"),
                document.getInteger("credits", 0),
                document.getInteger("stars", 0));
    }
}