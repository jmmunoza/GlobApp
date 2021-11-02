package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnNotificationsLoadedListener;

public interface INotificationsGetter {
    void getNotifications(OnNotificationsLoadedListener onNotificationsLoadedListener);
}
