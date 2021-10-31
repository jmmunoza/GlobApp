package com.globapp.globapp.data.remote;

import com.globapp.globapp.GlobAppApplication;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;

public class MongoDB {
    private static final String APP_ID        = "globapp-tbbdi";
    private static final String DB_EMAIL      = "demo1@gmail.com";
    private static final String DB_PASSWORD   = "000000";
    private static final String DB_SERVICE    = "mongodb-atlas";
    private static final String DATABASE_NAME = "GlobAppPrueba1";
    private static MongoDatabase databaseInstance;

    private MongoDB(){
        Realm.init(GlobAppApplication.getAppContext());
    }

    public static void initDB(){
        App app = new App(new AppConfiguration.Builder(APP_ID).build());
        Credentials emailPasswordCredentials = Credentials.emailPassword(DB_EMAIL, DB_PASSWORD);

        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                User currentUser = app.currentUser();
                assert currentUser != null;
                MongoClient mongoClient = currentUser.getMongoClient(DB_SERVICE);
                databaseInstance = mongoClient.getDatabase(DATABASE_NAME);
            }
        });
    }

    public static MongoDatabase getInstance(){
        if(databaseInstance == null){
            initDB();
        }

        return databaseInstance;
    }
}