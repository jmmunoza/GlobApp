package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.listeners.OnNewsCommentedListener;
import com.globapp.globapp.data.services.ICommentInserter;
import com.globapp.globapp.model.Comment;
import com.globapp.globapp.util.DocConverter;
import com.globapp.globapp.util.DocCreator;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoCollection;

public class CommentInserterMongo implements ICommentInserter {
    private final MongoCollection<Document> newsCollection;

    public CommentInserterMongo(){
        this.newsCollection = MongoDB.getNewsCollection();
    }

    @Override
    public void comment(ObjectId newsID, String commentContent, OnNewsCommentedListener onNewsCommentedListener) {
        // Create the Comment as a Document
        Document commentDoc = DocCreator.createComment(commentContent, newsID);

        // The news query where the comment will be put
        Document newsQuery = new Document("_id", newsID);

        // The insertion Doc for the new comment
        Document commentInsertion = new Document("$addToSet", new Document("comments", commentDoc));

        newsCollection.findOneAndUpdate(newsQuery, commentInsertion).getAsync(result -> {
            if(result.isSuccess()){

                Comment newComment = DocConverter.documentToComment(commentDoc);
                onNewsCommentedListener.onNewsCommented(newComment);
            }
        });
    }
}
