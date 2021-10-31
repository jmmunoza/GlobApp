package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnNewsLikedListener;

import org.bson.types.ObjectId;

public interface INewsLiker {
    void like(ObjectId newsID, OnNewsLikedListener onNewsLikedListener);
    void getIsLiked(ObjectId newsID, OnNewsLikedListener onNewsLikedListener);
}
