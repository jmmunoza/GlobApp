package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.listeners.OnNewsCommentedListener;
import com.globapp.globapp.data.services.ICommentInserter;
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
        Document newsQuery = new Document("_id", newsID);
        newsCollection.findOne(newsQuery).getAsync(result -> {
            if(result.isSuccess()){
                ArrayList<Document> comments = (ArrayList<Document>) result.get().getList(
                        "comments",
                        Document.class,
                        new ArrayList<>());

                Document commentDoc = DocCreator.createComment(commentContent, newsID);
                comments.add(commentDoc);

                Document newsInsertion = result.get().append("comments", comments);

                newsCollection.findOneAndUpdate(newsQuery, newsInsertion).getAsync(inserted -> {
                    if(inserted.isSuccess()){
                        onNewsCommentedListener.onNewsCommented(comments.size());
                       /* Toast.makeText(getContext(), "COMENTARIO PUBLICADO", Toast.LENGTH_LONG).show();
                        notificationNews.getNewsComments().add(newCommentDocument);
                        notificationCommentListAdapter.notifyItemInserted(notificationNews.getNewsComments().size()-1);
                        notificationNestedScroll.post(() -> notificationNestedScroll.fullScroll(RecyclerView.FOCUS_DOWN));

                        */
                    }
                });
            }
        });
    }
}
