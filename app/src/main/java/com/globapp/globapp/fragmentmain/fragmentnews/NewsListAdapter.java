package com.globapp.globapp.fragmentmain.fragmentnews;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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
        if(news.getNewsUserOwner().getMeImage() != null)
            holder.newsUserImage.setImageURI(news.getNewsUserOwner().getMeImage());
        else
            holder.newsUserImage.setImageResource(R.drawable.user);

        if(newsList.get(position) instanceof NewsRecognition){
            holder.newsRecognitionLayout.setVisibility(View.VISIBLE);
            holder.newsUsername.setText(news.getNewsUserOwner().getMeName() + " congratulated " + ((NewsRecognition)newsList.get(position)).getNewsUserRecognized().getMeName());
            if(((NewsRecognition) newsList.get(position)).getNewsUserRecognized().getMeImage() != null)
                holder.newsRecognitionUserImage.setImageURI(((NewsRecognition) newsList.get(position)).getNewsUserRecognized().getMeImage());
            else
                holder.newsRecognitionUserImage.setImageResource(R.drawable.user);

        } else {
            holder.newsRecognitionLayout.setVisibility(View.GONE);
            holder.newsUsername.setText(news.getNewsUserOwner().getMeName());
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class CommentDialog extends BottomSheetDialogFragment {
        private CommentListAdapter  commentListAdapter;
        private TextInputEditText   commentInput;
        private ImageButton         commentSendButton;
        private RecyclerView        commentList;
        private News                commentNews;
        private RelativeLayout      commentRoot;
        private int                 commentNewsPosition;
        private BottomSheetBehavior commentBehavior;
        private View                view;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            setStyle(STYLE_NORMAL, R.style.SheetDialog);
            if(((MainActivity)getContext()).isDarkMode){
                view = View.inflate(getContext(), R.layout.fragment_news_item_comment_dark, null);
            } else {
                view = View.inflate(getContext(), R.layout.fragment_news_item_comment, null);
            }

            commentRoot              = view.findViewById(R.id.comment_root);
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
                        commentNews.addComment(new Comment("0", commentInput.getText().toString(), ((MainActivity)getContext()).me));
                        commentInput.setText("");
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(commentInput.getWindowToken(), 0);
                        commentListAdapter.notifyDataSetChanged();
                        commentList.scrollToPosition(commentNews.getNewsComments().size()-1);

                        if(((MainActivity)getContext()).getSupportFragmentManager().getFragments().get(0) instanceof FragmentNews){
                            ((FragmentNews)((MainActivity)getContext()).getSupportFragmentManager().getFragments().get(0)).newsListAdapter.notifyItemChanged(commentNewsPosition);
                        } else if(((MainActivity)getContext()).getSupportFragmentManager().getFragments().get(0) instanceof FragmentMain) {
                            ((FragmentMain)((MainActivity)getContext()).getSupportFragmentManager().getFragments().get(0)).fragmentNews.newsListAdapter.notifyItemChanged(commentNewsPosition);
                        }
                    }
                }
            });

            BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
            dialog.setContentView(view);
            commentBehavior = BottomSheetBehavior.from((View) view.getParent());
            return dialog;
        }

        public CommentDialog(News commentNews, int commentNewsPosition) {
            this.commentNews         = commentNews;
            this.commentNewsPosition = commentNewsPosition;
        }

        @Override
        public void onStart() {
            super.onStart();
            commentBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
                        ((MainActivity)context).addFragmentLeft(
                                new FragmentMe(newsList.get(getAdapterPosition()).getNewsUserOwner()));
                    }
                } else {
                    ((MainActivity)context).addFragmentLeft(
                            new FragmentUser(newsList.get(getAdapterPosition()).getNewsUserOwner()));
                }
            });

            newsRecognitionUserImage.setOnClickListener(v -> {
                if(((MainActivity)context).me.equals(((NewsRecognition)newsList.get(getAdapterPosition())).getNewsUserRecognized())){
                    if(((MainActivity)context).getSupportFragmentManager().getBackStackEntryCount() == 1){
                        ((MainActivity)context).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                    } else {
                        ((MainActivity)context).addFragmentRight(
                                new FragmentMe(((NewsRecognition)newsList.get(getAdapterPosition())).getNewsUserRecognized()));
                    }
                } else {
                    ((MainActivity)context).addFragmentRight(
                            new FragmentUser(((NewsRecognition)newsList.get(getAdapterPosition())).getNewsUserRecognized()));
                }
            });

            newsCommentButton.setOnTouchListener((v, event) -> {
                if(gestureDetector.onTouchEvent(event)) {
                    newsCommentButton.setAlpha((float) 1);
                    CommentDialog commentDialog = new CommentDialog(newsList.get(getAdapterPosition()), getAdapterPosition());
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
