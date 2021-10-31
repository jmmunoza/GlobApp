package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnCommentListLoadedListener;

import org.bson.types.ObjectId;

public interface ICommentGetter {
    void getComments(ObjectId newsID, OnCommentListLoadedListener onCommentListLoadedListener);
}
