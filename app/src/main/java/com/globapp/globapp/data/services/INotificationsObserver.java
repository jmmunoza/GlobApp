package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnNotificationsUpdatedListener;

public interface INotificationsObserver {
    void subscribe(OnNotificationsUpdatedListener onNotificationsUpdatedListener);
}
