package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.factories.CommentFactory;
import com.globapp.globapp.data.listeners.OnCommentAddedListener;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.ICommentObserver;
import com.globapp.globapp.model.Comment;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.events.BaseChangeEvent;

public class CommentObserverMongo implements ICommentObserver {
    private final ArrayList<OnCommentAddedListener> listeners;

    public CommentObserverMongo(){
        listeners = new ArrayList<>();
    }

    @Override
    public void subscribe(OnCommentAddedListener onCommentAddedListener, ObjectId newsID) {
        listeners.add(onCommentAddedListener);
        setNewsID(newsID);
    }

    @Override
    public void unsubscribe(OnCommentAddedListener onCommentAddedListener) {
        listeners.remove(onCommentAddedListener);
    }

    @Override
    public void notify(Comment comment) {
        for(OnCommentAddedListener listener: listeners){
            listener.update(comment);
        }
    }

    private void setNewsID(ObjectId newsID){
        MongoCollection<Document> newsCollection = MongoDB.getNewsCollection();
        newsCollection.watchAsync(newsID).get(result -> {

            BaseChangeEvent.OperationType operation = result.get().getOperationType();
            BsonDocument updatedField = result.get().getUpdateDescription().getUpdatedFields();

            if(operation.equals(BaseChangeEvent.OperationType.UPDATE) && updatedField.containsKey("comments")){
                ArrayList<Document> commentsDocList = new ArrayList<>(result.get().getFullDocument().getList("comments", Document.class, new ArrayList<>()));

                if(commentsDocList.size() > 0){
                    Document commentDoc = commentsDocList.get(commentsDocList.size()-1);
                    Comment comment = CommentFactory.documentToComment(commentDoc);
                    if(!comment.getCommentUser().equals(UserSessionController.getUserSessionID())){
                        notify(comment);
                    }
                }
            }
        });
    }
}
