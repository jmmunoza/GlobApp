package com.globapp.globapp.data.remote;

import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.services.IGiveStar;
import com.globapp.globapp.data.services.INotifier;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.util.DocCreator;
import com.globapp.globapp.view.fragments.FragmentGiveStar;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.mongodb.mongo.MongoCollection;

public class GiveStarMongo implements IGiveStar {
    private final MongoCollection<Document> userCollection, newsCollection;
    private final FragmentGiveStar.OnGiveStarListener onGiveStarListener;

    public GiveStarMongo(FragmentGiveStar.OnGiveStarListener onGiveStarListener){
        this.onGiveStarListener = onGiveStarListener;
        this.userCollection = MongoDB.getUserCollection();
        this.newsCollection = MongoDB.getNewsCollection();
    }

    @Override
    public void giveStar(ObjectId userID, String congratulationsMessage) {
        Document userQuery = new Document("_id", userID);
        Document newsRecognition = DocCreator.createRecognitionNews(userID, congratulationsMessage);

        userCollection.findOne(userQuery).getAsync(userData -> {
            if(userData.isSuccess()){
                int userStars = userData.get().getInteger("stars");
                userStars++;

                userCollection.findOneAndUpdate(userQuery, userData.get().append("stars", userStars)).getAsync(userUpdated -> {

                    if(userUpdated.isSuccess()){
                        newsCollection.insertOne(newsRecognition).getAsync(result -> {
                            if (result.isSuccess()) {
                                ObjectId newsInsertedID = result.get().getInsertedId().asObjectId().getValue();
                                Date         newsDate = newsRecognition.getDate("date");
                                Notification notification = new Notification(newsDate, newsInsertedID, false);

                                DataRepository.notify(notification);
                                onGiveStarListener.onSuccess();
                            } else {
                                onGiveStarListener.onError();
                            }
                        });
                    } else {
                        onGiveStarListener.onError();
                    }
                });
            } else {
                onGiveStarListener.onError();
            }
        });
    }
}
