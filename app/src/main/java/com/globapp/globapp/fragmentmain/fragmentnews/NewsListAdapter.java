package com.globapp.globapp.fragmentmain.fragmentnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.Comment;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.globapp.globapp.fragmentmain.fragmentme.FragmentMe;
import com.globapp.globapp.fragmentmain.fragmentuser.FragmentUser;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private ArrayList<News> newsList;
    private LayoutInflater  inflater;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.newsPostContent.setText(news.getNewsContent());
        holder.newsLikeCounter.setText(String.valueOf(news.getNewsLikes()));
        holder.newsCommentCounter.setText(String.valueOf(news.getNewsComments().size()));
        holder.newsPostImage.setImageURI(news.getNewsImage());
        holder.newsUserImage.setImageURI(news.getNewsUserOwner().getMeImage());

        if(newsList.get(position) instanceof NewsRecognition){
            holder.newsRecognitionLayout.setVisibility(View.VISIBLE);
            holder.newsUsername.setText(news.getNewsUserOwner().getMeName() + " congratulated " + ((NewsRecognition)newsList.get(position)).getNewsUserRecognized().getMeName());
            holder.newsRecognitionUserImage.setImageURI(((NewsRecognition) newsList.get(position)).getNewsUserRecognized().getMeImage());

        } else {
            holder.newsRecognitionLayout.setVisibility(View.GONE);
            holder.newsUsername.setText(news.getNewsUserOwner().getMeName());
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class CommentDialog {
        private CommentListAdapter commentListAdapter;
        private TextInputEditText  commentInput;
        private ImageButton        commentSendButton;
        private RecyclerView       commentList;
        private View               view;
        private News               commentNews;
        private int                commentNewsPosition;

        public CommentDialog(News commentNews, int commentNewsPosition) {
            LayoutInflater inflater = LayoutInflater.from(context);
            if(((MainActivity)context).isDarkMode){
                view = inflater.inflate(R.layout.fragment_news_item_comment_dark, null, false);
            } else {
                view = inflater.inflate(R.layout.fragment_news_item_comment, null, false);
            }

            this.commentNews         = commentNews;
            this.commentNewsPosition = commentNewsPosition;
            commentInput             = view.findViewById(R.id.comment_input);
            commentSendButton        = view.findViewById(R.id.comment_send_button);
            commentList              = view.findViewById(R.id.comment_list);

            commentListAdapter = new CommentListAdapter(view.getContext(), commentNews.getNewsComments());
            commentList.setLayoutManager(new LinearLayoutManager(
                    view.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false));
            commentList.setAdapter(commentListAdapter);

            commentSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(commentInput.getText().toString().length() > 0){
                        commentNews.addComment(new Comment("0", commentInput.getText().toString(), ((MainActivity)context).me));
                        commentInput.setText("");
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(commentInput.getWindowToken(), 0);
                        commentListAdapter.notifyDataSetChanged();
                        commentList.scrollToPosition(commentNews.getNewsComments().size()-1);
                        (NewsListAdapter.this).notifyItemChanged(commentNewsPosition);
                    }
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

        private class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>{

            private ArrayList<Comment> newsComments;
            private LayoutInflater inflater;
            Context context;

            public CommentListAdapter(Context context, ArrayList<Comment> newsComments){
                this.inflater = LayoutInflater.from(context);
                this.context = context;
                this.newsComments = newsComments;
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(((MainActivity)context).isDarkMode){
                    return new ViewHolder(inflater.inflate(R.layout.fragment_news_item_comment_item_dark, parent, false));
                } else {
                    return new ViewHolder(inflater.inflate(R.layout.fragment_news_item_comment_item, parent, false));
                }
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                Comment comment = newsComments.get(position);

                holder.commentContent.setText(comment.getCommentContent());
                holder.commentUsername.setText(comment.getCommentUser().getMeName());
                holder.commentUserImage.setImageURI(comment.getCommentUser().getMeImage());
            }

            @Override
            public int getItemCount() {
                return newsComments.size();
            }

            private class ViewHolder extends RecyclerView.ViewHolder {
                CircleImageView commentUserImage;
                TextView        commentUsername;
                TextView        commentContent;
                TextView        commentTime;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);

                    commentUserImage = itemView.findViewById(R.id.comment_item_user_image);
                    commentUsername  = itemView.findViewById(R.id.comment_item_username);
                    commentContent   = itemView.findViewById(R.id.comment_item_content);
                    commentTime      = itemView.findViewById(R.id.comment_item_time);

                    commentUserImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((MainActivity)context).me.equals(newsComments.get(getAdapterPosition()).getCommentUser())){
                                ((MainActivity)context).addFragment(new FragmentMe((newsComments.get(getAdapterPosition()).getCommentUser())));
                            } else {
                                ((MainActivity)context).addFragment(
                                        new FragmentUser(newsComments.get(getAdapterPosition()).getCommentUser()));
                            }
                        }
                    });
                }
            }
        }
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
        TextView         newsCommentCounter;

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

            ((GifDrawable)newsStar.getDrawable()).stop();
            newsStar.setOnClickListener(v -> {
                ((GifDrawable)newsStar.getDrawable()).start();
                ((GifDrawable)newsStar.getDrawable()).addAnimationListener(i -> {
                    ((GifDrawable)newsStar.getDrawable()).stop();
                });
            });

            GestureDetector gestureDetector = new GestureDetector(itemView.getContext(), new MainActivity.SingleTapConfirm());

            newsContainer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gestureDetectorDouble.onTouchEvent(event);
                    return false;
                }

                private GestureDetector gestureDetectorDouble = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        newsList.get(getAdapterPosition()).addLike();
                        newsLikeCounter.setText(String.valueOf(newsList.get(getAdapterPosition()).getNewsLikes()));
                        return super.onDoubleTap(e);
                    }
                });
            });

            newsUserImage.setOnClickListener(v -> {
                if(((MainActivity)context).me.equals(newsList.get(getAdapterPosition()).getNewsUserOwner())){
                    if(((MainActivity)context).getSupportFragmentManager().getBackStackEntryCount() == 1){
                        ((MainActivity)context).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                    } else {
                        ((MainActivity)context).addFragment(
                                new FragmentMe(newsList.get(getAdapterPosition()).getNewsUserOwner()));
                    }
                } else {
                    ((MainActivity)context).addFragment(
                            new FragmentUser(newsList.get(getAdapterPosition()).getNewsUserOwner()));
                }
            });

            newsRecognitionUserImage.setOnClickListener(v -> {
                if(((MainActivity)context).me.equals(((NewsRecognition)newsList.get(getAdapterPosition())).getNewsUserRecognized())){
                    if(((MainActivity)context).getSupportFragmentManager().getBackStackEntryCount() == 1){
                        ((MainActivity)context).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                    } else {
                        ((MainActivity)context).addFragment(
                                new FragmentMe(((NewsRecognition)newsList.get(getAdapterPosition())).getNewsUserRecognized()));
                    }
                } else {
                    ((MainActivity)context).addFragment(
                            new FragmentUser(((NewsRecognition)newsList.get(getAdapterPosition())).getNewsUserRecognized()));
                }
            });

            newsCommentButton.setOnTouchListener((v, event) -> {
                if(gestureDetector.onTouchEvent(event)) {
                    newsCommentButton.setAlpha((float) 1);
                    CommentDialog commentDialog = new CommentDialog(newsList.get(getAdapterPosition()), getAdapterPosition());
                    commentDialog.show();
                } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    newsCommentButton.setAlpha((float) 0.5);
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    newsCommentButton.setAlpha((float) 1);
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                    newsCommentButton.setAlpha((float) 1);
                }
                return true;
            });

            newsLikeButton.setOnTouchListener((v, event) -> {
                if(gestureDetector.onTouchEvent(event)) {
                    newsLikeButton.setAlpha((float) 1);
                    newsList.get(getAdapterPosition()).addLike();
                    newsLikeCounter.setText(String.valueOf(newsList.get(getAdapterPosition()).getNewsLikes()));
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
    }
}
