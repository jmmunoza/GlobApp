package com.globapp.globapp.data.listeners;

import com.globapp.globapp.model.Notification;

import java.util.ArrayList;

public interface OnNotificationsLoadedListener {
    void onNotificationsLoaded(ArrayList<Notification> notifications);
}
