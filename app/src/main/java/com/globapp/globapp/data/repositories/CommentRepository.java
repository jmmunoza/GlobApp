package com.globapp.globapp.data.repositories;

import com.globapp.globapp.data.listeners.OnCommentAddedListener;
import com.globapp.globapp.data.listeners.OnCommentListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsCommentedListener;
import com.globapp.globapp.data.listeners.OnNotificationsUpdatedListener;
import com.globapp.globapp.data.services.ICommentGetter;
import com.globapp.globapp.data.services.ICommentInserter;
import com.globapp.globapp.data.services.ICommentObserver;

import org.bson.types.ObjectId;

public class CommentRepository {
    private final ICommentInserter iCommentInserter;
    private final ICommentGetter   iCommentGetter;
    private final ICommentObserver iCommentObserver;

    public CommentRepository(ICommentInserter iCommentInserter, ICommentGetter iCommentGetter, ICommentObserver iCommentObserver){
        this.iCommentInserter = iCommentInserter;
        this.iCommentGetter   = iCommentGetter;
        this.iCommentObserver = iCommentObserver;
    }

    public void insertComment(ObjectId newsID, String commentContent, OnNewsCommentedListener onNewsCommentedListener){
        iCommentInserter.comment(newsID, commentContent, onNewsCommentedListener);
    }

    public void getComments(ObjectId newsID, OnCommentListLoadedListener onCommentListLoadedListener){
        iCommentGetter.getComments(newsID, onCommentListLoadedListener);
    }

    public void subscribe(OnCommentAddedListener onCommentAddedListener, ObjectId newsID){
        iCommentObserver.subscribe(onCommentAddedListener, newsID);
    }

    public void unsubscribe(OnCommentAddedListener onCommentAddedListener){
        iCommentObserver.unsubscribe(onCommentAddedListener);
    }
}