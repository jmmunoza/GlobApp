package com.globapp.globapp.data.remote;

import com.globapp.globapp.GlobAppApplication;
import com.globapp.globapp.data.listeners.OnDatabaseConnectedListener;


import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class MongoDB {
    private static final String APP_ID        = "globapp-tbbdi";
    private static final String DB_EMAIL      = "demo1@gmail.com";
    private static final String DB_PASSWORD   = "000000";
    private static final String DB_SERVICE    = "mongodb-atlas";
    private static final String DATABASE_NAME = "GlobAppPrueba1";

    private static MongoCollection<org.bson.Document> userCollection;
    private static MongoCollection<org.bson.Document> newsCollection;
    private static MongoDatabase databaseInstance;

    private MongoDB(){

    }

    public static void initDB(OnDatabaseConnectedListener onDatabaseConnectedListener){
        Realm.init(GlobAppApplication.getAppContext());
        App app = new App(new AppConfiguration.Builder(APP_ID).build());
        Credentials emailPasswordCredentials = Credentials.emailPassword(DB_EMAIL, DB_PASSWORD);
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                User currentUser = app.currentUser();
                assert currentUser != null;
                MongoClient mongoClient = currentUser.getMongoClient(DB_SERVICE);
                databaseInstance = mongoClient.getDatabase(DATABASE_NAME);
                onDatabaseConnectedListener.onDBConnected();
            } else {
                onDatabaseConnectedListener.onError();
            }
        });
    }

    public static MongoCollection<Document> getUserCollection(){
        if(userCollection == null){
            userCollection = databaseInstance.getCollection("user");
        }

        return userCollection;
    }

    public static MongoCollection<Document> getNewsCollection(){
        if(newsCollection == null){
            newsCollection = databaseInstance.getCollection("news");
        }

        return newsCollection;
    }
}