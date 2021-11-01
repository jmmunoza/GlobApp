package com.globapp.globapp.view.viewholders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.listeners.OnNewsLikedListener;
import com.globapp.globapp.data.listeners.OnUserImageClickedListener;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.util.SingleTapConfirm;
import com.globapp.globapp.view.MainActivity;
import com.globapp.globapp.view.dialogs.CommentDialog;

import org.bson.types.ObjectId;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class NewsListViewHolder extends RecyclerView.ViewHolder {
    // UI COMPONENTS
    public TextView         newsUsername;
    public ImageView        newsUserImage;
    public ImageView        newsPostImage;
    public TextView         newsPostContent;
    public ImageButton      newsLikeButton;
    public ImageButton      newsCommentButton;
    public ConstraintLayout newsPostLayout;
    public ConstraintLayout newsRecognitionLayout;
    public ConstraintLayout newsContainer;
    public ImageView        newsRecognitionUserImage;
    public TextView         newsLikeCounter;
    public GifImageView     newsStar;
    public TextView         newsTime;
    public TextView         newsCommentCounter;

    // data
    private ObjectId              newsID;
    private ObjectId              userOwnerID;
    private ObjectId              userRecognizedID;
    private final GestureDetector gestureDetector;
    private final Context         context;

    // Listeners
    private final OnUserImageClickedListener onUserImageClickedListener;

    @SuppressLint("ClickableViewAccessibility")
    public NewsListViewHolder(View itemView, Context context, OnUserImageClickedListener onUserImageClickedListener) {
        super(itemView);
        this.onUserImageClickedListener = onUserImageClickedListener;
        this.context = context;

        newsUsername             = itemView.findViewById(R.id.news_item_username);
        newsPostContent          = itemView.findViewById(R.id.news_item_post_content);
        newsUserImage            = itemView.findViewById(R.id.news_item_user_image);
        newsPostImage            = itemView.findViewById(R.id.news_item_post_image);
        newsLikeButton           = itemView.findViewById(R.id.news_item_like_button);
        newsCommentButton        = itemView.findViewById(R.id.news_item_comment_button);
        newsRecognitionLayout    = itemView.findViewById(R.id.news_item_recognition_layout);
        newsRecognitionUserImage = itemView.findViewById(R.id.news_item_recognition_user_image);
        newsPostLayout           = itemView.findViewById(R.id.news_item_post_layout);
        newsContainer            = itemView.findViewById(R.id.news_item_container);
        newsLikeCounter          = itemView.findViewById(R.id.news_item_like_counter);
        newsCommentCounter       = itemView.findViewById(R.id.news_item_comment_counter);
        newsStar                 = itemView.findViewById(R.id.news_item_star);
        newsTime                 = itemView.findViewById(R.id.news_item_time);

        gestureDetector = new GestureDetector(itemView.getContext(), new SingleTapConfirm());

        starImageFunction();
        commentButtonFunction();
        containerFunction();
        likeButtonFunction();
        userImageFunction();
        recognitionUserImageFunction();

    }

    private void starImageFunction(){
        ((GifDrawable)newsStar.getDrawable()).stop();
        newsStar.setOnClickListener(v -> {
            ((GifDrawable)newsStar.getDrawable()).start();
            ((GifDrawable)newsStar.getDrawable()).addAnimationListener(i -> ((GifDrawable)newsStar.getDrawable()).stop());
        });
    }

    public void setUserOwnerID(ObjectId userOwnerID){
        this.userOwnerID = userOwnerID;
    }

    public void setUserRecognizedID(ObjectId userRecognizedID){
        this.userRecognizedID = userRecognizedID;
    }

    public void setNewsID(ObjectId newsID){
        this.newsID = newsID;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void containerFunction(){
        newsContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorDouble.onTouchEvent(event);
                return false;
            }

            private final GestureDetector gestureDetectorDouble = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    DataRepository.likeNews(newsID, new OnNewsLikedListener() {
                        @Override
                        public void liked(int likesCount) {
                            newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                            newsLikeCounter.setText(String.valueOf(likesCount));
                        }

                        @Override
                        public void disliked(int likesCount) {
                            if (Preferences.getDarkMode())
                                newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
                            else
                                newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            newsLikeCounter.setText(String.valueOf(likesCount));
                        }
                    });
                    return super.onDoubleTap(e);
                }
            });
        });
    }

    private void userImageFunction(){
        newsUserImage.setOnClickListener(v -> onUserImageClickedListener.onUserImageClicked(userOwnerID));
    }

    private void recognitionUserImageFunction(){
        newsRecognitionUserImage.setOnClickListener(v -> onUserImageClickedListener.onUserImageClicked(userRecognizedID));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void likeButtonFunction(){
        newsLikeButton.setOnTouchListener((v, event) -> {
            if(gestureDetector.onTouchEvent(event)) {
                newsLikeButton.setAlpha((float) 1);
                DataRepository.likeNews(newsID, new OnNewsLikedListener() {
                    @Override
                    public void liked(int likesCount) {
                        newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                        newsLikeCounter.setText(String.valueOf(likesCount));
                    }

                    @Override
                    public void disliked(int likesCount) {
                        if (Preferences.getDarkMode())
                            newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
                        else
                            newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        newsLikeCounter.setText(String.valueOf(likesCount));
                    }
                });

            } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                newsLikeButton.setAlpha((float) 0.5);
            } else if (event.getAction() == MotionEvent.ACTION_UP){
                newsLikeButton.setAlpha((float) 1);
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                newsLikeButton.setAlpha((float) 1);
            }
            return true;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void commentButtonFunction(){
        newsCommentButton.setOnTouchListener((v, event) -> {
            if(gestureDetector.onTouchEvent(event)) {
                newsCommentButton.setAlpha((float) 1);
                CommentDialog commentDialog = new CommentDialog(newsID);
                commentDialog.addOnUserImageClickedListener(onUserImageClickedListener);
                commentDialog.show(((MainActivity)context).getSupportFragmentManager(), commentDialog.getTag());
            } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                newsCommentButton.setAlpha((float) 0.5);
            } else if (event.getAction() == MotionEvent.ACTION_UP){
                newsCommentButton.setAlpha((float) 1);
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                newsCommentButton.setAlpha((float) 1);
            }
            return true;
        });
    }
}