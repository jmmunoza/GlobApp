package com.globapp.globapp.util;

import com.globapp.globapp.GlobAppApplication;
import com.globapp.globapp.R;
import com.globapp.globapp.model.User;

public class NotificationTextGetter {
    public static String ifUserRecognizedYou(User userOwner){
        return  userOwner.getUserFirstName() + " " +
                GlobAppApplication.getAppContext().getString(R.string.notification_news_recognition_you);
    }

    public static String ifUserRecognizedSomeoneElse(User userOwner, User userRecognized){
        return  userOwner.getUserFirstName() + " " +
                GlobAppApplication.getAppContext().getString(R.string.notification_news_recognition_1) + " " +
                userRecognized.getUserFirstName() + " " +
                GlobAppApplication.getAppContext().getString(R.string.notification_news_recognition_2);
    }

    public static String ifUserPostedAnImage(User userOwner){
        return  userOwner.getUserFirstName() + " " +
                userOwner.getUserLastName()  + " " +
                GlobAppApplication.getAppContext().getString(R.string.notification_news_image);
    }

    public static String ifUserPosted(User userOwner){
        return  userOwner.getUserFirstName() + " " +
                userOwner.getUserLastName() + " " +
                GlobAppApplication.getAppContext().getString(R.string.notification_news_text);
    }
}
