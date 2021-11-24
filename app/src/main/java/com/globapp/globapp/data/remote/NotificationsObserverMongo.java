package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.factories.NotificationFactory;
import com.globapp.globapp.data.listeners.OnNotificationsUpdatedListener;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.INotificationsObserver;
import com.globapp.globapp.model.Notification;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.events.BaseChangeEvent;

public class NotificationsObserverMongo implements INotificationsObserver {
    private final ArrayList<OnNotificationsUpdatedListener> notificationsUpdatedList;

    public NotificationsObserverMongo(){
        notificationsUpdatedList = new ArrayList<>();
        loadMongoObserver();
    }

    @Override
    public void subscribe(OnNotificationsUpdatedListener notificationsUpdatedListener) {
        notificationsUpdatedList.add(notificationsUpdatedListener);
    }

    @Override
    public void unsubscribe(OnNotificationsUpdatedListener onNotificationsUpdatedListener) {
        notificationsUpdatedList.remove(onNotificationsUpdatedListener);
    }

    @Override
    public void notify(Notification notification){
        for(OnNotificationsUpdatedListener listener: notificationsUpdatedList){
            listener.update(notification);
        }
    }

    private void loadMongoObserver(){
        ObjectId userSessionID = UserSessionController.getUserSessionID();

        MongoCollection<Document> userCollection = MongoDB.getUserCollection();
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
                        Notification notification = NotificationFactory.documentToNotification(notificationDoc);
                        notify(notification);
                    }
                }
            }
        });
    }
}
