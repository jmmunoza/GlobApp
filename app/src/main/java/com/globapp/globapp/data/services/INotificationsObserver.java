package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnNotificationsUpdatedListener;
import com.globapp.globapp.model.Notification;

import java.util.ArrayList;

public interface INotificationsObserver {
    void subscribe(OnNotificationsUpdatedListener onNotificationsUpdatedListener);
    void unsubscribe(OnNotificationsUpdatedListener onNotificationsUpdatedListener);
    void notify(Notification notification);
}
