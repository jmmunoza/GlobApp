package com.globapp.globapp.data.services;

import com.globapp.globapp.data.listeners.OnNewsCommentedListener;

import org.bson.types.ObjectId;

public interface ICommentInserter {
    void comment(ObjectId newsID, String commentContent, OnNewsCommentedListener onNewsCommentedListener);
}
