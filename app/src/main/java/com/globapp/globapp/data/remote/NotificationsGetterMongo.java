package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.factories.NotificationFactory;
import com.globapp.globapp.data.listeners.OnNotificationsLoadedListener;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.INotificationsGetter;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.util.DocConverter;
import com.google.common.collect.Lists;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.mongodb.mongo.MongoCollection;

public class NotificationsGetterMongo implements INotificationsGetter {
    private final MongoCollection<Document> userCollection;

    public NotificationsGetterMongo(){
        this.userCollection = MongoDB.getUserCollection();
    }

    @Override
    public void getNotifications(OnNotificationsLoadedListener onNotificationsLoadedListener) {
        ObjectId userSessionID = UserSessionController.getUserSessionID();
        Document userSessionQuery = new Document("_id", userSessionID);

        userCollection.findOne(userSessionQuery).getAsync(result -> {
            if(result.isSuccess()){
                ArrayList<Document> notificationsDoc = new ArrayList<>(
                        result.get().getList(
                                "notifications",
                                Document.class,
                                new ArrayList<>()));

                ArrayList<Notification> notifications =
                        new ArrayList<>(Lists.transform(notificationsDoc, NotificationFactory::documentToNotification));

                Collections.reverse(notifications);

                onNotificationsLoadedListener.onNotificationsLoaded(notifications);
            } else {
                onNotificationsLoadedListener.onNotificationsLoaded(new ArrayList<>());
            }
        });
    }
}
