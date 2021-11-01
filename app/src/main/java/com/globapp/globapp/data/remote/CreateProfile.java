package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.services.ICreateProfile;
import com.globapp.globapp.view.fragments.FragmentCreateProfile;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.mongodb.mongo.MongoCollection;

public class CreateProfile implements ICreateProfile {
    private final MongoCollection<Document> userCollection;
    private final FragmentCreateProfile.OnCreateProfileListener onCreateProfileListener;

    public CreateProfile(FragmentCreateProfile.OnCreateProfileListener onCreateProfileListener){
        this.onCreateProfileListener = onCreateProfileListener;
        this.userCollection          = MongoDB.getUserCollection();
    }

    @Override
    public void create(ObjectId userID, String userDescription) {
        Document userQuery = new Document("_id", userID);
        userCollection.findOne(userQuery).getAsync(userDocument -> {
            if(userDocument.isSuccess()){
                Document newUser = userDocument.get().append("description", userDescription);
                userCollection.findOneAndUpdate(userQuery, newUser).getAsync(result -> {
                    if(result.isSuccess()){
                        onCreateProfileListener.onSuccess();
                    } else {
                        onCreateProfileListener.onCancel();
                    }
                });
            }
        });
    }
}
