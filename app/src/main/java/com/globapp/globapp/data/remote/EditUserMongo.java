package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.IEditUser;
import com.globapp.globapp.data.services.IUserSessionController;
import com.globapp.globapp.view.fragments.FragmentEditProfile;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.mongodb.mongo.MongoCollection;

public class EditUserMongo implements IEditUser {
    private final MongoCollection<Document> userCollection;
    private final FragmentEditProfile.OnEditProfileListener onEditProfileListener;

    public EditUserMongo(FragmentEditProfile.OnEditProfileListener onEditProfileListener){
        this.onEditProfileListener = onEditProfileListener;
        this.userCollection = MongoDB.getInstance().getCollection("user");
    }

    @Override
    public void edit(String newDescription) {
        IUserSessionController iUserSessionController = new UserSessionController();
        ObjectId userSessionID = new ObjectId(iUserSessionController.getUserSessionID());
        Document userQuery = new Document().append("_id", userSessionID);

        userCollection.findOne(userQuery).getAsync(userData -> {
            if(userData.isSuccess()){
                Document userDataUpdated = userData.get().append("description", newDescription);
                userCollection.findOneAndUpdate(userQuery, userDataUpdated).getAsync(result -> {
                    if(result.isSuccess()){
                        onEditProfileListener.onSuccess();
                    }
                });
            }
        });
    }
}
