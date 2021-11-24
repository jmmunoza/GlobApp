package com.globapp.globapp.data.repositories;

import com.globapp.globapp.data.listeners.OnNotificationsLoadedListener;
import com.globapp.globapp.data.listeners.OnNotificationsUpdatedListener;
import com.globapp.globapp.data.services.INotificationsGetter;
import com.globapp.globapp.data.services.INotificationsObserver;
import com.globapp.globapp.data.services.INotifier;
import com.globapp.globapp.model.Notification;

public class NotificationRepository {
    private final INotificationsGetter   iNotificationsGetter;
    private final INotifier              iNotifier;
    private final INotificationsObserver iNotificationsObserver;

    public NotificationRepository(INotifier iNotifier, INotificationsGetter iNotificationsGetter, INotificationsObserver iNotificationsObserver){
        this.iNotificationsGetter   = iNotificationsGetter;
        this.iNotifier              = iNotifier;
        this.iNotificationsObserver = iNotificationsObserver;
    }

    public void notify(Notification notification){
        iNotifier.notify(notification);
    }

    public void getNotifications(OnNotificationsLoadedListener onNotificationsLoadedListener){
        iNotificationsGetter.getNotifications(onNotificationsLoadedListener);
    }

    public void subscribe(OnNotificationsUpdatedListener onNotificationsUpdatedListener){
        iNotificationsObserver.subscribe(onNotificationsUpdatedListener);
    }

    public void unsubscribe(OnNotificationsUpdatedListener onNotificationsUpdatedListener){
        iNotificationsObserver.unsubscribe(onNotificationsUpdatedListener);
    }
}
