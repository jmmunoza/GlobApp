package com.globapp.globapp.data.remote;

import android.net.Uri;

import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.IEditUser;
import com.globapp.globapp.util.ImageConverter;
import com.globapp.globapp.view.fragments.FragmentEditProfile;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.mongodb.mongo.MongoCollection;

public class EditUserMongo implements IEditUser {
    private final MongoCollection<Document> userCollection;
    private final FragmentEditProfile.OnEditProfileListener onEditProfileListener;

    public EditUserMongo(FragmentEditProfile.OnEditProfileListener onEditProfileListener){
        this.onEditProfileListener = onEditProfileListener;
        this.userCollection = MongoDB.getUserCollection();
    }

    @Override
    public void edit(String newDescription, Uri userImageUri, Uri coverImageUri) {
        ObjectId userSessionID = UserSessionController.getUserSessionID();
        Document userQuery = new Document().append("_id", userSessionID);

        userCollection.findOne(userQuery).getAsync(userData -> {
            if(userData.isSuccess()){
                Document userDataUpdated = userData.get()
                        .append("description", newDescription)
                        .append("userImage", ImageConverter.UriToByteArray(userImageUri))
                        .append("coverImage", ImageConverter.UriToByteArray(coverImageUri));
                userCollection.findOneAndUpdate(userQuery, userDataUpdated).getAsync(result -> {
                    if(result.isSuccess()){
                        onEditProfileListener.onSuccess();
                    }
                });
            }
        });
    }
}
