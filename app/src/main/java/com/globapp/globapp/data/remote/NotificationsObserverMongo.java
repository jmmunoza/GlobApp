package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.listeners.OnNotificationsUpdatedListener;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.INotificationsObserver;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.util.DocConverter;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.events.BaseChangeEvent;

public class NotificationsObserverMongo implements INotificationsObserver {
    private final MongoCollection<Document> userCollection;

    public NotificationsObserverMongo(){
        this.userCollection = MongoDB.getUserCollection();
    }

    @Override
    public void subscribe(OnNotificationsUpdatedListener notificationsUpdatedListener) {
        ObjectId userSessionID = new ObjectId(UserSessionController.getUserSessionID());

        userCollection.watchAsync(userSessionID).get(result -> {
            if(result.isSuccess() && result.get() != null){

                BaseChangeEvent.OperationType operation = result.get().getOperationType();
                BsonDocument updatedField = result.get().getUpdateDescription().getUpdatedFields();

                if(operation.equals(BaseChangeEvent.OperationType.UPDATE) && updatedField.containsKey("notifications")){
                    ArrayList<Document> notificationsDocList =
                            new ArrayList<>(
                                    result.get().getFullDocument().getList(
                                            "notifications",
                                            Document.class,
                                            new ArrayList<>()));

                    if(notificationsDocList.size() > 0){
                        Document notificationDoc = notificationsDocList.get(notificationsDocList.size()-1);
                        Notification notification = DocConverter.documentToNotification(notificationDoc);
                        notificationsUpdatedListener.update(notification);
                    }
                }
            }
        });
    }
}
