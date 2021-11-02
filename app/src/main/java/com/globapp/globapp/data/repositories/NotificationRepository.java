package com.globapp.globapp.data.repositories;

import com.globapp.globapp.data.listeners.OnNotificationsLoadedListener;
import com.globapp.globapp.data.services.INotificationsGetter;
import com.globapp.globapp.data.services.INotifier;
import com.globapp.globapp.model.Notification;

public class NotificationRepository {
    private final INotificationsGetter iNotificationsGetter;
    private final INotifier iNotifier;

    public NotificationRepository(INotifier iNotifier, INotificationsGetter iNotificationsGetter){
        this.iNotificationsGetter = iNotificationsGetter;
        this.iNotifier = iNotifier;
    }

    public void notify(Notification notification){
        iNotifier.notify(notification);
    }

    public void getNotifications(OnNotificationsLoadedListener onNotificationsLoadedListener){
        iNotificationsGetter.getNotifications(onNotificationsLoadedListener);
    }
}
