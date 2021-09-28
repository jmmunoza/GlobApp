package com.globapp.globapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.Notification;
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentlogin.FragmentLogin;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.globapp.globapp.fragmentmain.fragmentadminpane.FragmentAdminPane;
import com.globapp.globapp.fragmentmain.fragmentme.FragmentMe;
import com.globapp.globapp.fragmentmain.fragmentnews.FragmentNews;
import com.globapp.globapp.fragmentmain.fragmentnotifications.FragmentNotifications;

import org.bson.Document;

import java.util.ArrayList;
import io.realm.Realm;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;

public class MainActivity extends AppCompatActivity {
    public static final String DATA                        = "DATA";
    public static final String APP_ID                      = "globapp-tbbdi";
    public static final String DB_EMAIL                    = "demo1@gmail.com";
    public static final String DB_PASSWORD                 = "000000";
    public static final String DB_USER_COLLECTION          = "user";
    public static final String DB_NEWS_COLLECTION          = "news";
    public static final String DB_COMMENTS_COLLECTION      = "comments";
    public static final String DB_NOTIFICATIONS_COLLECTION = "notifications";
    public static final String DB_SERVICE                  = "mongodb-atlas";
    public static final String DATABASE_NAME               = "GlobAppPrueba1";
    public static final String DARK_MODE                   = "DARK_MODE";
    public static final String IS_ENGLISH                  = "IS_ENGLISH";
    public static final int    DESCENDING                  = -1;
    public static final int    ASCENDING                   = 1;

    public FragmentMain  fragmentMain;
    public FragmentLogin fragmentLogin;

    public GifImageView animationContainer;

    public Boolean isDarkMode;
    public Boolean isEnglish;

    // Database data
    public io.realm.mongodb.User databaseConnection;
    public MongoCollection<Document> newsCollection;
    public MongoCollection<Document> userCollection;
    public MongoCollection<Document> commentsCollection;
    public MongoCollection<Document> notificationsCollection;

    // data
    public User me;
    public ArrayList<News> news;
    public ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        connectDB();
        loadData();
        animationContainer = findViewById(R.id.animation_container);

        fragmentLogin      = new FragmentLogin();
        fragmentMain       = new FragmentMain();
        addFragmentRight(fragmentLogin);
    }

    public void enableAnimation(int animationID) {
        animationContainer.setImageResource(animationID);
        ((GifDrawable) animationContainer.getDrawable()).start();
        ((GifDrawable) animationContainer.getDrawable()).addAnimationListener(i -> {
            ((GifDrawable) animationContainer.getDrawable()).stop();
            animationContainer.setImageBitmap(null);
        });
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        System.out.println(getSupportFragmentManager().getFragments());

        if (count  == 1) {
            Fragment bottomFragment = getSupportFragmentManager().getFragments().get(0);
            if(bottomFragment instanceof FragmentMain          || bottomFragment instanceof FragmentNews ||
               bottomFragment instanceof FragmentNotifications || bottomFragment instanceof FragmentMe   ||
               bottomFragment instanceof FragmentAdminPane){
                if(fragmentMain.mainViewPager.getCurrentItem() == FragmentMain.NEWS){
                    moveTaskToBack(true);
                } else {
                    fragmentMain.mainViewPager.setCurrentItem(FragmentMain.NEWS);
                }
            } else {
                moveTaskToBack(true);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    public void connectDB(){
        App app = new App(new AppConfiguration.Builder(APP_ID).build());
        Credentials emailPasswordCredentials = Credentials.emailPassword(DB_EMAIL, DB_PASSWORD);
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                databaseConnection = app.currentUser();
                MongoClient mongoClient     = databaseConnection.getMongoClient(DB_SERVICE);
                MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
                newsCollection              = mongoDatabase.getCollection(DB_NEWS_COLLECTION);
                userCollection              = mongoDatabase.getCollection(DB_USER_COLLECTION);
                notificationsCollection     = mongoDatabase.getCollection(DB_NOTIFICATIONS_COLLECTION);
                commentsCollection          = mongoDatabase.getCollection(DB_COMMENTS_COLLECTION);
            }
        });
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        isDarkMode    = sharedPreferences.getBoolean(DARK_MODE, false);
        isEnglish     = sharedPreferences.getBoolean(IS_ENGLISH, true);
        news          = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    public void addFragmentUp(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_up,
                        R.anim.slide_out_down,
                        R.anim.slide_in_down,
                        R.anim.slide_out_up)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void addFragmentLeft(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void addFragmentRight(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}