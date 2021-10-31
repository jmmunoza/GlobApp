package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.listeners.OnCommentListLoadedListener;
import com.globapp.globapp.data.services.ICommentGetter;
import com.globapp.globapp.model.Comment;
import com.globapp.globapp.util.DocConverter;
import com.google.common.collect.Lists;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoCollection;

public class CommentGetterMongo implements ICommentGetter {
    private final MongoCollection<Document> newsCollection;

    public CommentGetterMongo(){
        this.newsCollection = MongoDB.getInstance().getCollection("news");
    }

    @Override
    public void getComments(ObjectId newsID, OnCommentListLoadedListener onCommentListLoadedListener) {
        Document newsQuery = new Document("_id", newsID);
        newsCollection.findOne(newsQuery).getAsync(result -> {
            if(result.isSuccess()){
                ArrayList<Document> docComments = (ArrayList<Document>) result.get().getList(
                        "comments",
                        Document.class,
                        new ArrayList<>());

                ArrayList<Comment> comments = (ArrayList<Comment>) Lists.transform(docComments, DocConverter::documentToComment);

                onCommentListLoadedListener.onCommentListLoaded(comments);
            }
        });
    }
}
