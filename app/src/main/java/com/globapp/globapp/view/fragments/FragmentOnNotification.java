package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.listeners.OnNewsCommentedListener;
import com.globapp.globapp.data.listeners.OnNewsLikedListener;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.manager.CommentDataManager;
import com.globapp.globapp.data.manager.NewsDataManager;
import com.globapp.globapp.data.manager.UserDataManager;
import com.globapp.globapp.data.remote.CommentGetterMongo;
import com.globapp.globapp.data.remote.CommentInserterMongo;
import com.globapp.globapp.data.remote.NewsGetterMongo;
import com.globapp.globapp.data.remote.NewsInserterMongo;
import com.globapp.globapp.data.remote.NewsLikerMongo;
import com.globapp.globapp.data.remote.UserGetterMongo;
import com.globapp.globapp.data.remote.UserInserterMongo;
import com.globapp.globapp.model.Comment;
import com.globapp.globapp.model.News;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.KeyboardManager;
import com.globapp.globapp.util.SingleTapConfirm;
import com.globapp.globapp.view.adapters.CommentListAdapter;
import com.google.android.material.textfield.TextInputEditText;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class FragmentOnNotification extends Fragment {
    // DATA
    private final ObjectId           newsID;
    private final NewsDataManager    newsDataManager;
    private final UserDataManager    userDataManager;
    private final CommentDataManager commentDataManager;
    private News                     news;
    private User                     userOwner;
    private User                     userRecognized;
    private ArrayList<Comment>       commentList;
    private boolean                  isUserLiked;

    // LISTENERS
    private OnNotificationListener      onNotificationListener;

    // UI COMPONENTS
    private TextView           notificationUsername;
    private ImageView          notificationUserImage;
    private ImageView          notificationPostImage;
    private TextView           notificationPostContent;
    private ImageButton        notificationLikeButton;
    private ImageButton        notificationCommentButton;
    private ConstraintLayout   notificationPostLayout;
    private ConstraintLayout   notificationRecognitionLayout;
    private ConstraintLayout   notificationContainer;
    private ImageView          notificationRecognitionUserImage;
    private TextView           notificationLikeCounter;
    private GifImageView       notificationStar;
    private TextView           notificationCommentCounter;
    private CommentListAdapter notificationCommentListAdapter;
    private TextInputEditText  notificationCommentInput;
    private ImageButton        notificationCommentSendButton;
    private RecyclerView       notificationCommentList;
    private NestedScrollView   notificationNestedScroll;
    private TextView           notificationTime;

    public FragmentOnNotification(ObjectId newsID){
        this.newsID = newsID;

        newsDataManager = new NewsDataManager(
                new NewsInserterMongo(),
                new NewsGetterMongo(),
                new NewsLikerMongo()
        );

        userDataManager = new UserDataManager(
                new UserInserterMongo(),
                new UserGetterMongo()
        );

        commentDataManager = new CommentDataManager(
                new CommentInserterMongo(),
                new CommentGetterMongo()
        );
    }

    @SuppressLint("InflateParams") @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(Preferences.getDarkMode()){
            return inflater.inflate(R.layout.fragment_on_notification_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_on_notification, null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNewsData();
    }

    private void loadNewsData(){
        newsDataManager.getNews(newsID, news -> {
            FragmentOnNotification.this.news = news;
            newsDataManager.getIsLiked(newsID, new OnNewsLikedListener() {
                @Override
                public void liked(int likesCount) {
                    isUserLiked = true;
                    loadUsersData(news.getNewsUserOwner(), news.getNewsUserRecognized());
                }

                @Override
                public void disliked(int likesCount) {
                    isUserLiked = false;
                    loadUsersData(news.getNewsUserOwner(), news.getNewsUserRecognized());
                }
            });
        });
    }

    private void loadUsersData(String userOwnerID, String userRecognizedID){
        userDataManager.getUser(new ObjectId(userOwnerID), userOwner -> {
            this.userOwner = userOwner;
            if(userRecognizedID != null){
                userDataManager.getUser(new ObjectId(userRecognizedID), userRecognized -> {
                    this.userRecognized = userRecognized;
                    loadComments();
                });
            }
        });
    }

    private void loadComments() {
        commentDataManager.getComments(newsID, commentList -> {
            FragmentOnNotification.this.commentList = commentList;
            loadComponents();
        });
    }

    private void refreshPostData(){
        if(isUserLiked) {
            notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
        } else {
            if (Preferences.getDarkMode())
                notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
            else
                notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        notificationTime.setText(news.getNewsDate().toString());
        notificationPostContent.setText(news.getNewsContent());
        notificationLikeCounter.setText(String.valueOf(news.getNewsLikes()));
        notificationCommentCounter.setText(String.valueOf(commentList.size()));

        if(news.getNewsImage() != null)
            notificationPostImage.setImageURI(news.getNewsImage());

        if(news.getNewsUserRecognized() != null){
            notificationRecognitionLayout.setVisibility(View.VISIBLE);
        } else {
            notificationRecognitionLayout.setVisibility(View.GONE);
        }

        if(userOwner.getUserImage() != null)
            notificationUserImage.setImageURI(userOwner.getUserImage());
        else
            notificationUserImage.setImageResource(R.drawable.user);


        String usernameText;
        if(userRecognized != null){
            usernameText = userOwner.getUserFirstName()      + " " +
                           userOwner.getUserLastName()       + " congratulated " +
                           userRecognized.getUserFirstName() + " " +
                           userRecognized.getUserLastName();


            if(userRecognized.getUserImage() != null)
                notificationRecognitionUserImage.setImageURI(userRecognized.getUserImage());
            else
                notificationRecognitionUserImage.setImageResource(R.drawable.user);

        } else {
            usernameText = userOwner.getUserFirstName()  + " " +
                           userOwner.getUserSecondName() + " " +
                           userOwner.getUserLastName();

        }

        notificationUsername.setText(usernameText);

        ((GifDrawable)notificationStar.getDrawable()).stop();
        ((GifDrawable)notificationStar.getDrawable()).seekTo(0);
        notificationStar.setOnClickListener(v -> {
            ((GifDrawable)notificationStar.getDrawable()).start();
            ((GifDrawable)notificationStar.getDrawable()).addAnimationListener(i ->
                    ((GifDrawable)notificationStar.getDrawable()).stop());
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void containerFunction(){
        notificationContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorDouble.onTouchEvent(event);
                return false;
            }

            private final GestureDetector gestureDetectorDouble = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    newsDataManager.likeNews(newsID, new OnNewsLikedListener() {
                        @Override
                        public void liked(int likesCount) {
                            likeNews(likesCount);
                        }

                        @Override
                        public void disliked(int likesCount) {
                            dislikeNews(likesCount);
                        }
                    });
                    return super.onDoubleTap(e);
                }
            });
        });
    }

    private void userImageFunction(){
        notificationUserImage.setOnClickListener(v ->
                onNotificationListener.onUserImageClicked(new ObjectId(userOwner.getUserID())));
    }

    private void userRecognizedImageFunction(){
        notificationRecognitionUserImage.setOnClickListener(v ->
                onNotificationListener.onUserImageClicked(new ObjectId(userRecognized.getUserID())));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void commentButtonFunction(){
        notificationCommentButton.setOnTouchListener((v, event) -> {
            GestureDetector gestureDetector =
                    new GestureDetector(getContext(), new SingleTapConfirm());

            if(gestureDetector.onTouchEvent(event)) {
                notificationCommentButton.setAlpha((float) 1);
                notificationCommentInput.setSelected(true);
            } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                notificationCommentButton.setAlpha((float) 0.5);
            } else if (event.getAction() == MotionEvent.ACTION_UP){
                notificationCommentButton.setAlpha((float) 1);
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                notificationCommentButton.setAlpha((float) 1);
            }
            return true;
        });
    }

    private void likeNews(int likesCount){
        notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
        notificationLikeCounter.setText(String.valueOf(likesCount));
    }

    private void dislikeNews(int likesCount){
        if (Preferences.getDarkMode())
            notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
        else
            notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);

        notificationLikeCounter.setText(String.valueOf(likesCount));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void likeButtonFunction(){
        notificationLikeButton.setOnTouchListener((v, event) -> {
            GestureDetector gestureDetector =
                    new GestureDetector(getContext(), new SingleTapConfirm());

            if(gestureDetector.onTouchEvent(event)) {
                notificationLikeButton.setAlpha((float) 1);
                newsDataManager.likeNews(newsID, new OnNewsLikedListener() {
                    @Override
                    public void liked(int likesCount) {
                        likeNews(likesCount);
                    }

                    @Override
                    public void disliked(int likesCount) {
                        dislikeNews(likesCount);
                    }
                });

            } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                notificationLikeButton.setAlpha((float) 0.5);
            } else if (event.getAction() == MotionEvent.ACTION_UP){
                notificationLikeButton.setAlpha((float) 1);
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                notificationLikeButton.setAlpha((float) 1);
            }
            return true;
        });
    }

    private void commentSendButtonFunction(){
        notificationCommentSendButton.setOnClickListener(v -> {
            String commentContent = notificationCommentInput.getText().toString();
            if(commentContent.length() > 0){
                notificationCommentInput.setText("");
                KeyboardManager.hide();
                commentDataManager.insertComment(newsID, commentContent, new OnNewsCommentedListener() {
                    @Override
                    public void onNewsCommented(int commentsCount) {

                    }
                });
            }
        });
    }

    private void commentListFunction(){
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        notificationCommentList.setLayoutManager(verticalLayoutManager);
        notificationCommentListAdapter = new CommentListAdapter(getContext(), commentList, onNotificationListener);
        notificationCommentList.setAdapter(notificationCommentListAdapter);
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void loadComponents(){
        notificationUsername             = requireView().findViewById(R.id.on_notification_username);
        notificationUserImage            = requireView().findViewById(R.id.on_notification_user_image);
        notificationPostImage            = requireView().findViewById(R.id.on_notification_post_image);
        notificationPostContent          = requireView().findViewById(R.id.on_notification_post_content);
        notificationLikeButton           = requireView().findViewById(R.id.on_notification_like_button);
        notificationCommentButton        = requireView().findViewById(R.id.on_notification_comment_button);
        notificationPostLayout           = requireView().findViewById(R.id.on_notification_post_layout);
        notificationRecognitionLayout    = requireView().findViewById(R.id.on_notification_recognition_layout);
        notificationContainer            = requireView().findViewById(R.id.on_notification_container);
        notificationRecognitionUserImage = requireView().findViewById(R.id.on_notification_recognition_user_image);
        notificationLikeCounter          = requireView().findViewById(R.id.on_notification_like_counter);
        notificationStar                 = requireView().findViewById(R.id.on_notification_star);
        notificationCommentCounter       = requireView().findViewById(R.id.on_notification_comment_counter);
        notificationCommentInput         = requireView().findViewById(R.id.on_notification_input);
        notificationCommentSendButton    = requireView().findViewById(R.id.on_notification_send_button);
        notificationCommentList          = requireView().findViewById(R.id.on_notification_comment_list);
        notificationNestedScroll         = requireView().findViewById(R.id.on_notification_nested_scroll);
        notificationTime                 = requireView().findViewById(R.id.on_notification_time);

        refreshPostData();
        commentButtonFunction();
        commentSendButtonFunction();
        containerFunction();
        likeButtonFunction();
        userImageFunction();
        userRecognizedImageFunction();
        commentListFunction();
    }

    public interface OnNotificationListener {
        void onUserImageClicked(ObjectId userID);
    }

     public void addOnNotificationListener(OnNotificationListener onNotificationListener){
        this.onNotificationListener = onNotificationListener;
     }
}