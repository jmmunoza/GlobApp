package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.ILoginUser;
import com.globapp.globapp.view.fragments.FragmentLogin;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.mongodb.mongo.MongoCollection;

public class LoginUserMongo implements ILoginUser {
    private final MongoCollection<Document> userCollection;
    private final FragmentLogin.OnLoginReadyListener onLoginReadyListener;

    public LoginUserMongo(FragmentLogin.OnLoginReadyListener onLoginReadyListener){
        this.onLoginReadyListener = onLoginReadyListener;
        this.userCollection = MongoDB.getUserCollection();
    }

    @Override
    public void login(String email, String password) {
        Document userQuery = new Document("email", email);

        userCollection.findOne(userQuery).getAsync(result -> {
            if (result.isSuccess()) {
                if (result.get() != null){
                    Document userLogin = result.get();

                    if (userLogin.getString("password").equals(password)) {
                        if (userLogin.getString("description") != null) {

                            ObjectId userSessionID = userLogin.getObjectId("_id");
                            UserSessionController.setUserSessionID(userSessionID);
                            UserSessionController.setUserAdmin(userLogin.getBoolean("admin"));

                            onLoginReadyListener.onUserCreated();
                        } else {
                            onLoginReadyListener.onNewUser(userLogin.getObjectId("_id"));
                        }
                    } else {
                        onLoginReadyListener.onWrongPassword();
                    }
                } else {
                    onLoginReadyListener.onWrongEmail();
                }
            } else {
                onLoginReadyListener.onError();
            }
        });
    }
}
