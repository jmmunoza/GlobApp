package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnCommentAddedListener;
import com.globapp.globapp.model.Comment;

import org.bson.types.ObjectId;

public interface ICommentObserver {
    void subscribe(OnCommentAddedListener onCommentAddedListener, ObjectId newsID);
    void unsubscribe(OnCommentAddedListener onCommentAddedListener);
    void notify(Comment comment);
}
