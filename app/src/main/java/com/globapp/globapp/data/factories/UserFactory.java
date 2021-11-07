package com.globapp.globapp.data.factories;

import com.globapp.globapp.model.User;

import org.bson.Document;
import org.bson.types.Binary;

public class UserFactory {
    public static User documentToUser(Document document){

        byte[] userImage = null;
        if(document.get("userImage", Binary.class) != null)
            userImage = document.get("userImage", Binary.class).getData();


        byte[] coverImage = null;
        if(document.get("coverImage", Binary.class) != null)
            coverImage = document.get("coverImage", Binary.class).getData();

        return new User(
                document.getObjectId("_id"),
                document.getString("firstName"),
                document.getString("secondName"),
                document.getString("lastName"),
                document.getString("description"),
                userImage,
                coverImage,
                document.getInteger("credits", 0),
                document.getInteger("stars", 0));
    }
}