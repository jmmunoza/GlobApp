package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.INotifier;
import com.globapp.globapp.model.Notification;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.mongodb.mongo.MongoCollection;

public class NotifierMongo implements INotifier {
    private final MongoCollection<Document> userCollection;

    public NotifierMongo(){
        this.userCollection = MongoDB.getUserCollection();
    }

    @Override
    public void notify(Notification notification) {
        ObjectId userSessionID = UserSessionController.getUserSessionID();

        Document usersExceptUserSessionQuery = new Document("_id", new Document("$ne", userSessionID));

        Document notificationInsertion = new Document("$addToSet",
                new Document("notifications",
                        new Document("newsID", notification.getNotificationNews())
                                .append("date", notification.getNotificationDate())
                                .append("seen", false)));

        userCollection.updateMany(usersExceptUserSessionQuery, notificationInsertion).getAsync(result -> {
            if(result.isSuccess()){
                System.out.println("NOTIFIED!!!!");
            } else {
                System.out.println(result.getError().toString());
            }
        });
    }
}
