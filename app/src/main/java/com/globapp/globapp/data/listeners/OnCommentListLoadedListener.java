package com.globapp.globapp.data.listeners;

import com.globapp.globapp.model.Comment;

import java.util.ArrayList;

public interface OnCommentListLoadedListener {
    void onCommentListLoaded(ArrayList<Comment> commentList);
}
