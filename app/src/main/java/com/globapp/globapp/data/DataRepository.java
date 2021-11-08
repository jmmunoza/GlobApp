package com.globapp.globapp.data;

import com.globapp.globapp.data.listeners.OnCommentListLoadedListener;
import com.globapp.globapp.data.listeners.OnDatabaseConnectedListener;
import com.globapp.globapp.data.listeners.OnNewsCommentedListener;
import com.globapp.globapp.data.listeners.OnNewsLikedListener;
import com.globapp.globapp.data.listeners.OnNewsListLoadedListener;
import com.globapp.globapp.data.listeners.OnNewsLoadedListener;
import com.globapp.globapp.data.listeners.OnNotificationsLoadedListener;
import com.globapp.globapp.data.listeners.OnNotificationsUpdatedListener;
import com.globapp.globapp.data.listeners.OnUserLoadedListener;
import com.globapp.globapp.data.local.LocalDB;
import com.globapp.globapp.data.local.NewsGetterLocal;
import com.globapp.globapp.data.local.NewsInserterLocal;
import com.globapp.globapp.data.local.UserGetterLocal;
import com.globapp.globapp.data.local.UserInserterLocal;
import com.globapp.globapp.data.remote.CommentGetterMongo;
import com.globapp.globapp.data.remote.CommentInserterMongo;
import com.globapp.globapp.data.remote.MongoDB;
import com.globapp.globapp.data.remote.NewsGetterMongo;
import com.globapp.globapp.data.remote.NewsInserterMongo;
import com.globapp.globapp.data.remote.NewsLikerMongo;
import com.globapp.globapp.data.remote.NotificationsGetterMongo;
import com.globapp.globapp.data.remote.NotificationsObserverMongo;
import com.globapp.globapp.data.remote.NotifierMongo;
import com.globapp.globapp.data.remote.UserGetterMongo;
import com.globapp.globapp.data.remote.UserInserterMongo;
import com.globapp.globapp.data.repositories.CommentRepository;
import com.globapp.globapp.data.repositories.NewsRepository;
import com.globapp.globapp.data.repositories.NotificationRepository;
import com.globapp.globapp.data.repositories.UserRepository;
import com.globapp.globapp.model.News;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.model.User;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class DataRepository {

    // LOCAL DB REPOSITORIES
    private static CommentRepository localComments;
    private static NewsRepository    localNews;
    private static UserRepository    localUser;

    // MONGO DB REPOSITORIES
    private static UserRepository         mongoUser;
    private static CommentRepository      mongoComments;
    private static NewsRepository         mongoNews;
    private static NotificationRepository mongoNotifications;

    public static void init(OnDatabaseConnectedListener onDatabaseConnectedListener){
        LocalDB.initDB();
        MongoDB.initDB(new OnDatabaseConnectedListener() {
            @Override
            public void onDBConnected() {
                mongoUser =
                        new UserRepository(new UserInserterMongo(), new UserGetterMongo());

                mongoNews =
                        new NewsRepository(new NewsInserterMongo(), new NewsGetterMongo(), new NewsLikerMongo());

                mongoComments =
                        new CommentRepository(new CommentInserterMongo(), new CommentGetterMongo());

                mongoNotifications =
                        new NotificationRepository(new NotifierMongo(), new NotificationsGetterMongo(), new NotificationsObserverMongo());

                onDatabaseConnectedListener.onDBConnected();
            }

            @Override
            public void onError() {
                MongoDB.initDB(this);
            }
        });
    }

    public static void getUser(ObjectId userID, OnUserLoadedListener onUserLoadedListener){
        mongoUser.getUser(userID, onUserLoadedListener);
    }

    public static void insertUser(User user){
        mongoUser.insertUser(user);
    }

    public static void getNews(ObjectId newsID, OnNewsLoadedListener onNewsLoadedListener){
        mongoNews.getNews(newsID, onNewsLoadedListener);
    }

    public static void  getLatestNews(ArrayList<ObjectId> exceptedIDs, OnNewsListLoadedListener onNewsListLoadedListener){
        mongoNews.getLatestNews(exceptedIDs, onNewsListLoadedListener);
    }

    public static void insertNews(News news){
        mongoNews.insertNews(news);
    }

    public static void getIsLiked(ObjectId newsID, OnNewsLikedListener onNewsLikedListener){
        mongoNews.getIsLiked(newsID, onNewsLikedListener);
    }

    public static void likeNews(ObjectId newsID, OnNewsLikedListener onNewsLikedListener){
        mongoNews.likeNews(newsID, onNewsLikedListener);
    }

    public static void  getNewsComments(ObjectId newsID, OnCommentListLoadedListener onCommentListLoadedListener){
        mongoComments.getComments(newsID, onCommentListLoadedListener);
    }

    public static void insertComment(ObjectId newsID, String commentContent, OnNewsCommentedListener onNewsCommentedListener){
        mongoComments.insertComment(newsID, commentContent, onNewsCommentedListener);
    }

    public static void getNotifications(OnNotificationsLoadedListener onNotificationsLoadedListener){
        mongoNotifications.getNotifications(onNotificationsLoadedListener);
    }

    public static void notify(Notification notification){
        mongoNotifications.notify(notification);
    }

    public static void subscribeNotifications(OnNotificationsUpdatedListener onNotificationsUpdatedListener){
        mongoNotifications.subscribe(onNotificationsUpdatedListener);
    }
}
