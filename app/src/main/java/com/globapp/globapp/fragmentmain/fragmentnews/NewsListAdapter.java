package com.globapp.globapp.fragmentmain.fragmentnews;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.fragmentmain.fragmentuser.FragmentUser;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private ArrayList<News> newsList;
    private LayoutInflater inflater;
    Context context;

    public NewsListAdapter(Context context, ArrayList<News> newsList){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(((MainActivity)context).isDarkMode){
            return new NewsListAdapter.ViewHolder(inflater.inflate(R.layout.fragment_news_item_dark, parent, false));
        } else {
            return new NewsListAdapter.ViewHolder(inflater.inflate(R.layout.fragment_news_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.newsUsername.setText(news.getNewsUserOwner().getMeName());
        holder.newsPostContent.setText(news.getNewsContent());

        if(newsList.get(position) instanceof NewsRecognition){
            holder.newsPostLayout.setVisibility(View.GONE);
            //holder.newsRecognitionUserNameCongratulated.setText(((NewsRecognition)newsList.get(position)).getNewsUserRecognized().getMeName());
            switch (((NewsRecognition) newsList.get(position)).getNewsUserRecognized().getMeImage()){
                case 1:
                    holder.newsRecognitionSecondUserImage.setImageResource(R.drawable.meimage1);
                    break;
                case 2:
                    holder.newsRecognitionSecondUserImage.setImageResource(R.drawable.user2);
                    break;
                case 3:
                    holder.newsRecognitionSecondUserImage.setImageResource(R.drawable.user3);
                    break;
                case 4:
                    holder.newsRecognitionSecondUserImage.setImageResource(R.drawable.user4);
                    break;
                case 5:
                    holder.newsRecognitionSecondUserImage.setImageResource(R.drawable.user5);
                    break;
                case 6:
                    holder.newsRecognitionSecondUserImage.setImageResource(R.drawable.user6);
                    break;
                case 7:
                    holder.newsRecognitionSecondUserImage.setImageResource(R.drawable.user7);
                    break;
            }

            switch (((NewsRecognition) newsList.get(position)).getNewsUserOwner().getMeImage()){
                case 1:
                    holder.newsRecognitionFirstUserImage.setImageResource(R.drawable.meimage1);
                    break;
                case 2:
                    holder.newsRecognitionFirstUserImage.setImageResource(R.drawable.user2);
                    break;
                case 3:
                    holder.newsRecognitionFirstUserImage.setImageResource(R.drawable.user3);
                    break;
                case 4:
                    holder.newsRecognitionFirstUserImage.setImageResource(R.drawable.user4);
                    break;
                case 5:
                    holder.newsRecognitionFirstUserImage.setImageResource(R.drawable.user5);
                    break;
                case 6:
                    holder.newsRecognitionFirstUserImage.setImageResource(R.drawable.user6);
                    break;
                case 7:
                    holder.newsRecognitionFirstUserImage.setImageResource(R.drawable.user7);
                    break;
            }
        } else {
            holder.newsRecognitionLayout.setVisibility(View.GONE);
        }


        switch (news.getNewsUserOwner().getMeImage()){
            case 1:
                holder.newsUserImage.setImageResource(R.drawable.meimage1);
                break;
            case 2:
                holder.newsUserImage.setImageResource(R.drawable.user2);
                break;
            case 3:
                holder.newsUserImage.setImageResource(R.drawable.user3);
                break;
            case 4:
                holder.newsUserImage.setImageResource(R.drawable.user4);
                break;
            case 5:
                holder.newsUserImage.setImageResource(R.drawable.user5);
                break;
            case 6:
                holder.newsUserImage.setImageResource(R.drawable.user6);
                break;
            case 7:
                holder.newsUserImage.setImageResource(R.drawable.user7);
                break;
        }

        switch (news.getNewsImage()){
            case 1:
                holder.newsPostImage.setImageResource(R.drawable.meactivity1);
                break;
            case 2:
                holder.newsPostImage.setImageResource(R.drawable.meactivity2);
                break;
            case 3:
                holder.newsPostImage.setImageResource(R.drawable.meactivity3);
                break;
            case 6:
                holder.newsPostImage.setImageResource(R.drawable.news6);
                break;
            case 7:
                holder.newsPostImage.setImageResource(R.drawable.news7);
                break;
            case 8:
                holder.newsPostImage.setImageResource(R.drawable.news8);
                break;
            case 9:
                holder.newsPostImage.setImageResource(R.drawable.news9);
                break;
            case 10:
                holder.newsPostImage.setImageResource(R.drawable.news10);
                break;
            case 11:
                holder.newsPostImage.setImageResource(R.drawable.news11);
                break;
            case 12:
                holder.newsPostImage.setImageResource(R.drawable.news12);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class CommentDialog {
        private TextInputEditText commentInput;
        private ImageButton commentSendButton;
        private View view;

        public CommentDialog() {
            LayoutInflater inflater = LayoutInflater.from(context);

            if(((MainActivity)context).isDarkMode){
                view = inflater.inflate(R.layout.fragment_news_item_comment_dark, null, false);
            } else {
                view = inflater.inflate(R.layout.fragment_news_item_comment, null, false);
            }

            commentInput = view.findViewById(R.id.comment_input);
            commentSendButton = view.findViewById(R.id.comment_send_button);
            commentSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(commentInput.getText());
                }
            });
        }

        public void show(){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            mBuilder.setView(view);
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.show();
        }
    }

    public class ReactionsDialog {

        private View view;

        public ReactionsDialog() {
            LayoutInflater inflater = LayoutInflater.from(context);

            if(((MainActivity)context).isDarkMode){
                view = inflater.inflate(R.layout.fragment_news_item_reaction_dark, null, false);
            } else {
                view = inflater.inflate(R.layout.fragment_news_item_reaction, null, false);
            }
        }

        public void show(){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            mBuilder.setView(view);
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.show();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsUsername;
        ImageView newsUserImage;
        ImageView newsPostImage;
        TextView newsPostContent;
        ImageButton newsLikeButton;
        ImageButton newsCommentButton;
        ConstraintLayout newsPostLayout;
        ConstraintLayout newsRecognitionLayout;
        ImageView newsRecognitionFirstUserImage;
        ImageView newsRecognitionSecondUserImage;

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(View itemView) {
            super(itemView);

            newsUsername                         = (TextView)         itemView.findViewById(R.id.news_item_username);
            newsPostContent                      = (TextView)         itemView.findViewById(R.id.news_item_post_content);
            newsUserImage                        = (ImageView)        itemView.findViewById(R.id.news_item_user_image);
            newsPostImage                        = (ImageView)        itemView.findViewById(R.id.news_item_post_image);
            newsLikeButton                       = (ImageButton)      itemView.findViewById(R.id.news_item_like_button);
            newsCommentButton                    = (ImageButton)      itemView.findViewById(R.id.news_item_comment_button);
            newsRecognitionLayout                = (ConstraintLayout) itemView.findViewById(R.id.news_item_recognition_layout);
            newsRecognitionFirstUserImage        = (CircleImageView)  itemView.findViewById(R.id.news_item_recognition_first_user_image);
            newsRecognitionSecondUserImage       = (CircleImageView)  itemView.findViewById(R.id.news_item_recognition_second_user_image);
            newsPostLayout                       = (ConstraintLayout) itemView.findViewById(R.id.news_item_post_layout);

            GestureDetector gestureDetector = new GestureDetector(itemView.getContext(), new MainActivity.SingleTapConfirm());

            newsUserImage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(gestureDetector.onTouchEvent(event)) {
                        newsUserImage.setAlpha((float) 1);
                        ((MainActivity)context).addFragment(new FragmentUser(newsList.get(getAdapterPosition()).getNewsUserOwner()));
                    } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                        newsUserImage.setAlpha((float) 0.5);
                    } else if (event.getAction() == MotionEvent.ACTION_UP){
                        newsUserImage.setAlpha((float) 1);
                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                        newsUserImage.setAlpha((float) 1);
                    }
                    return true;
                }
            });

            newsCommentButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(gestureDetector.onTouchEvent(event)) {
                        newsCommentButton.setAlpha((float) 1);
                        CommentDialog commentDialog = new CommentDialog();
                        commentDialog.show();
                    } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                        newsCommentButton.setAlpha((float) 0.5);
                    } else if (event.getAction() == MotionEvent.ACTION_UP){
                        newsCommentButton.setAlpha((float) 1);
                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                        newsCommentButton.setAlpha((float) 1);
                    }
                    return true;
                }
            });

            newsLikeButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(gestureDetector.onTouchEvent(event)) {
                        newsLikeButton.setAlpha((float) 1);
                        ReactionsDialog reactionsDialog = new ReactionsDialog();
                        reactionsDialog.show();
                    } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                        newsLikeButton.setAlpha((float) 0.5);
                    } else if (event.getAction() == MotionEvent.ACTION_UP){
                        newsLikeButton.setAlpha((float) 1);
                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                        newsLikeButton.setAlpha((float) 1);
                    }
                    return true;
                }
            });
        }
    }
}
