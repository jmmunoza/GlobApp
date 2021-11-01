package com.globapp.globapp.data.repositories;

import com.globapp.globapp.data.listeners.OnCommentListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsCommentedListener;
import com.globapp.globapp.data.services.ICommentGetter;
import com.globapp.globapp.data.services.ICommentInserter;

import org.bson.types.ObjectId;

public class CommentRepository {
    private final ICommentInserter iCommentInserter;
    private final ICommentGetter   iCommentGetter;

    public CommentRepository(ICommentInserter iCommentInserter, ICommentGetter iCommentGetter){
        this.iCommentInserter = iCommentInserter;
        this.iCommentGetter   = iCommentGetter;
    }

    public void insertComment(ObjectId newsID, String commentContent, OnNewsCommentedListener onNewsCommentedListener){
        iCommentInserter.comment(newsID, commentContent, onNewsCommentedListener);
    }

    public void getComments(ObjectId newsID, OnCommentListLoadedListener onCommentListLoadedListener){
        iCommentGetter.getComments(newsID, onCommentListLoadedListener);
    }
}