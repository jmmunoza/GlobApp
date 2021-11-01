package com.globapp.globapp.data.remote;

import android.widget.Toast;

import com.globapp.globapp.GlobAppApplication;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.ILoginUser;
import com.globapp.globapp.data.services.IUserSessionController;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.DocConverter;
import com.globapp.globapp.view.MainActivity;
import com.globapp.globapp.view.fragments.FragmentCreateProfile;
import com.globapp.globapp.view.fragments.FragmentLogin;

import org.bson.Document;

import io.realm.mongodb.mongo.MongoCollection;

public class LoginUserMongo implements ILoginUser {
    private final MongoCollection<Document> userCollection;
    private final FragmentLogin.OnLoginReadyListener onLoginReadyListener;

    public LoginUserMongo(FragmentLogin.OnLoginReadyListener onLoginReadyListener){
        this.onLoginReadyListener = onLoginReadyListener;
        this.userCollection = MongoDB.getInstance().getCollection("user");
    }

    @Override
    public void login(String email, String password) {
        Document userQuery = new Document("email", email);

        userCollection.findOne(userQuery).getAsync(result -> {
            if (result.isSuccess()) {
                if (result.get() != null){
                    Document userLogin = result.get();

                    if (userLogin.getString("password").equals(password)) {

                        String userSessionID = userLogin.getObjectId("_id").toString();
                        IUserSessionController iUserSessionController = new UserSessionController();
                        iUserSessionController.setUserSessionID(userSessionID);
                        iUserSessionController.setUserAdmin(userLogin.getBoolean("admin"));

                        if (userLogin.getString("description") != null) {
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
