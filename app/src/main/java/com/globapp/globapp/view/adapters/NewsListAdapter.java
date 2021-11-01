package com.globapp.globapp.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.listeners.OnNewsLikedListener;
import com.globapp.globapp.data.listeners.OnUserImageClickedListener;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.model.News;
import com.globapp.globapp.util.SingleTapConfirm;
import com.globapp.globapp.util.UserNameGetter;
import com.globapp.globapp.view.MainActivity;
import com.globapp.globapp.view.dialogs.CommentDialog;

import org.bson.types.ObjectId;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final ArrayList<News>    newsList;
    private final LayoutInflater     inflater;
    private DataLoadedListener       dataLoadedListener;
    private int                      loadedNews;
    Context context;

    // LISTENERS
    private final OnUserImageClickedListener onUserImageClickedListener;

    public NewsListAdapter(Context context, ArrayList<News> newsList, OnUserImageClickedListener onUserImageClickedListener){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.newsList = newsList;
        this.onUserImageClickedListener = onUserImageClickedListener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new ViewHolder(inflater.inflate(R.layout.fragment_news_item_dark, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.fragment_news_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);

        DataRepository.getUser(new ObjectId(news.getNewsID()), userOwner -> {
            holder.newsPostContent.setText(news.getNewsContent());
            holder.newsLikeCounter.setText(String.valueOf(news.getNewsLikes()));
           // holder.newsCommentCounter.setText(String.valueOf(news.getNewsComments().size()));
            holder.newsTime.setText(news.getNewsDate().toString());
            if(news.getNewsImage() != null) holder.newsPostImage.setImageURI(news.getNewsImage());
            if(userOwner.getUserImage() != null)
                holder.newsUserImage.setImageURI(userOwner.getUserImage());
            else
                holder.newsUserImage.setImageResource(R.drawable.user);

            if(news.getNewsUserRecognized() != null){
                holder.newsRecognitionLayout.setVisibility(View.VISIBLE);
                DataRepository.getUser(new ObjectId(news.getNewsUserRecognized()), userRecognized -> {
                    holder.newsUsername.setText(UserNameGetter.getUserNameRecognition(userOwner, userRecognized));

                    if(userRecognized.getUserImage() != null)
                        holder.newsRecognitionUserImage.setImageURI(userRecognized.getUserImage());
                    else
                        holder.newsRecognitionUserImage.setImageResource(R.drawable.user);

                    loadedNews++;
                    isDataLoaded();
                });
            } else {
                holder.newsRecognitionLayout.setVisibility(View.GONE);
                holder.newsUsername.setText(UserNameGetter.getUserName(userOwner));
                loadedNews++;
                isDataLoaded();
            }
        });

        DataRepository.getIsLiked(new ObjectId(news.getNewsID()), new OnNewsLikedListener() {
            @Override
            public void liked(int likesCount) {
                holder.newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
            }

            @Override
            public void disliked(int likesCount) {
                if (Preferences.getDarkMode())
                    holder.newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
                else
                    holder.newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        });
    }

    public void addDataLoadedListener(DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
    }

    public interface DataLoadedListener {
        void onDataLoaded();
    }

    private void isDataLoaded() {
        if(newsList.size() == loadedNews){
            if(dataLoadedListener != null)
                dataLoadedListener.onDataLoaded();
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView         newsUsername;
        ImageView        newsUserImage;
        ImageView        newsPostImage;
        TextView         newsPostContent;
        ImageButton      newsLikeButton;
        ImageButton      newsCommentButton;
        ConstraintLayout newsPostLayout;
        ConstraintLayout newsRecognitionLayout;
        ConstraintLayout newsContainer;
        ImageView        newsRecognitionUserImage;
        TextView         newsLikeCounter;
        GifImageView     newsStar;
        TextView         newsTime;
        TextView         newsCommentCounter;
        private GestureDetector gestureDetector;

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(View itemView) {
            super(itemView);

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
                ((GifDrawable)newsStar.getDrawable()).addAnimationListener(i -> {
                    ((GifDrawable)newsStar.getDrawable()).stop();
                });
            });
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
                        DataRepository.likeNews(new ObjectId(newsList.get(getAdapterPosition()).getNewsID()), new OnNewsLikedListener() {
                            @Override
                            public void liked(int likesCount) {
                                newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                            }

                            @Override
                            public void disliked(int likesCount) {
                                if (Preferences.getDarkMode())
                                    newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
                                else
                                    newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            }
                        });
                        return super.onDoubleTap(e);
                    }
                });
            });
        }

        private void userImageFunction(){
            newsUserImage.setOnClickListener(v -> {
                onUserImageClickedListener.onUserImageClicked(new ObjectId(newsList.get(getAdapterPosition()).getNewsUserOwner()));
            });
        }

        private void recognitionUserImageFunction(){
            newsRecognitionUserImage.setOnClickListener(v -> {
                onUserImageClickedListener.onUserImageClicked(new ObjectId(newsList.get(getAdapterPosition()).getNewsUserRecognized()));
            });
        }

        @SuppressLint("ClickableViewAccessibility")
        private void likeButtonFunction(){
            newsLikeButton.setOnTouchListener((v, event) -> {
                if(gestureDetector.onTouchEvent(event)) {
                    newsLikeButton.setAlpha((float) 1);
                    DataRepository.likeNews(new ObjectId(newsList.get(getAdapterPosition()).getNewsID()), new OnNewsLikedListener() {
                        @Override
                        public void liked(int likesCount) {
                            newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                        }

                        @Override
                        public void disliked(int likesCount) {
                            if (Preferences.getDarkMode())
                                newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
                            else
                                newsLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
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
                    CommentDialog commentDialog = new CommentDialog(new ObjectId(newsList.get(getAdapterPosition()).getNewsID()));
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
}
