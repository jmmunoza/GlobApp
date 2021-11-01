package com.globapp.globapp.data.listeners;

import com.globapp.globapp.model.Comment;

public interface OnNewsCommentedListener {
    void onNewsCommented(int commentsCount, Comment newComment);
}
