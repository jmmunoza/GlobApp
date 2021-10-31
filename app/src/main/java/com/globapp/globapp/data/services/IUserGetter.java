package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnUserLoadedListener;

import org.bson.types.ObjectId;

public interface IUserGetter {
    void getUser(ObjectId userID, OnUserLoadedListener onUserLoadedListener);
}