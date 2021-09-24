package com.globapp.globapp.fragmentmain.fragmentnotifications;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.Comment;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.globapp.globapp.fragmentmain.fragmentme.FragmentMe;
import com.globapp.globapp.fragmentmain.fragmentnews.CommentListAdapter;
import com.globapp.globapp.fragmentmain.fragmentuser.FragmentUser;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class FragmentOnNotification extends Fragment {
    // DATA
    News notificationNews;

    // COMPONENTS UI
    TextView           notificationUsername;
    ImageView          notificationUserImage;
    ImageView          notificationPostImage;
    TextView           notificationPostContent;
    ImageButton        notificationLikeButton;
    ImageButton        notificationCommentButton;
    ConstraintLayout   notificationPostLayout;
    ConstraintLayout   notificationRecognitionLayout;
    ConstraintLayout   notificationContainer;
    ImageView          notificationRecognitionUserImage;
    TextView           notificationLikeCounter;
    GifImageView       notificationStar;
    TextView           notificationCommentCounter;
    CommentListAdapter notificationCommentListAdapter;
    TextInputEditText  notificationCommentInput;
    ImageButton        notificationCommentSendButton;
    RecyclerView       notificationCommentList;

    public FragmentOnNotification(News notificationNews){
        this.notificationNews = notificationNews;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(((MainActivity)getContext()).isDarkMode){
            return inflater.inflate(R.layout.fragment_on_notification_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_on_notification, null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComponents();
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void loadComponents(){
        notificationUsername             = getView().findViewById(R.id.on_notification_username);
        notificationUserImage            = getView().findViewById(R.id.on_notification_user_image);
        notificationPostImage            = getView().findViewById(R.id.on_notification_post_image);
        notificationPostContent          = getView().findViewById(R.id.on_notification_post_content);
        notificationLikeButton           = getView().findViewById(R.id.on_notification_like_button);
        notificationCommentButton        = getView().findViewById(R.id.on_notification_comment_button);
        notificationPostLayout           = getView().findViewById(R.id.on_notification_post_layout);
        notificationRecognitionLayout    = getView().findViewById(R.id.on_notification_recognition_layout);
        notificationContainer            = getView().findViewById(R.id.on_notification_container);
        notificationRecognitionUserImage = getView().findViewById(R.id.on_notification_recognition_user_image);
        notificationLikeCounter          = getView().findViewById(R.id.on_notification_like_counter);
        notificationStar                 = getView().findViewById(R.id.on_notification_star);
        notificationCommentCounter       = getView().findViewById(R.id.on_notification_comment_counter);
        notificationCommentInput         = getView().findViewById(R.id.on_notification_input);
        notificationCommentSendButton    = getView().findViewById(R.id.on_notification_send_button);
        notificationCommentList          = getView().findViewById(R.id.on_notification_comment_list);


        notificationPostContent.setText(notificationNews.getNewsContent());
        notificationLikeCounter.setText(String.valueOf(notificationNews.getNewsLikes()));
        notificationCommentCounter.setText(String.valueOf(notificationNews.getNewsComments().size()));
        notificationPostImage.setImageURI(notificationNews.getNewsImage());
        if(notificationNews.getNewsUserOwner().getMeImage() != null)
            notificationUserImage.setImageURI(notificationNews.getNewsUserOwner().getMeImage());
        else
            notificationUserImage.setImageResource(R.drawable.user);

        if(notificationNews instanceof NewsRecognition){
            notificationRecognitionLayout.setVisibility(View.VISIBLE);
            notificationUsername.setText(notificationNews.getNewsUserOwner().getMeName() +
                    " congratulated " +
                    ((NewsRecognition)notificationNews).getNewsUserRecognized().getMeName());
            if(((NewsRecognition) notificationNews).getNewsUserRecognized().getMeImage() != null)
                notificationRecognitionUserImage.setImageURI(((NewsRecognition) notificationNews).getNewsUserRecognized().getMeImage());
            else
                notificationRecognitionUserImage.setImageResource(R.drawable.user);

        } else {
            notificationRecognitionLayout.setVisibility(View.GONE);
            notificationUsername.setText(notificationNews.getNewsUserOwner().getMeName());
        }

        ((GifDrawable)notificationStar.getDrawable()).stop();
        notificationStar.setOnClickListener(v -> {
            ((GifDrawable)notificationStar.getDrawable()).start();
            ((GifDrawable)notificationStar.getDrawable()).addAnimationListener(i -> {
                ((GifDrawable)notificationStar.getDrawable()).stop();
            });
        });

        GestureDetector gestureDetector = new GestureDetector(getContext(), new MainActivity.SingleTapConfirm());

        notificationContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorDouble.onTouchEvent(event);
                return false;
            }

            private GestureDetector gestureDetectorDouble = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    notificationNews.addLike();
                    notificationLikeCounter.setText(String.valueOf(notificationNews.getNewsLikes()));
                    return super.onDoubleTap(e);
                }
            });
        });

        notificationUserImage.setOnClickListener(v -> {
            if(((MainActivity)getContext()).me.equals(notificationNews.getNewsUserOwner())){
                if(((MainActivity)getContext()).getSupportFragmentManager().getBackStackEntryCount() == 1){
                    ((MainActivity)getContext()).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                } else {
                    ((MainActivity)getContext()).addFragmentLeft(
                            new FragmentMe(notificationNews.getNewsUserOwner()));
                }
            } else {
                ((MainActivity)getContext()).addFragmentLeft(
                        new FragmentUser(notificationNews.getNewsUserOwner()));
            }
        });

        notificationRecognitionUserImage.setOnClickListener(v -> {
            if(((MainActivity)getContext()).me.equals(((NewsRecognition)notificationNews).getNewsUserRecognized())){
                if(((MainActivity)getContext()).getSupportFragmentManager().getBackStackEntryCount() == 1){
                    ((MainActivity)getContext()).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                } else {
                    ((MainActivity)getContext()).addFragmentRight(
                            new FragmentMe(((NewsRecognition)notificationNews).getNewsUserRecognized()));
                }
            } else {
                ((MainActivity)getContext()).addFragmentRight(
                        new FragmentUser(((NewsRecognition)notificationNews).getNewsUserRecognized()));
            }
        });

        notificationCommentButton.setOnTouchListener((v, event) -> {
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

        notificationLikeButton.setOnTouchListener((v, event) -> {
            if(gestureDetector.onTouchEvent(event)) {
                notificationLikeButton.setAlpha((float) 1);
                notificationNews.addLike();
                notificationLikeCounter.setText(String.valueOf(notificationNews.getNewsLikes()));
            } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                notificationLikeButton.setAlpha((float) 0.5);
            } else if (event.getAction() == MotionEvent.ACTION_UP){
                notificationLikeButton.setAlpha((float) 1);
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                notificationLikeButton.setAlpha((float) 1);
            }
            return true;
        });

        notificationCommentListAdapter = new CommentListAdapter(getContext(), notificationNews.getNewsComments());
        notificationCommentList.setLayoutManager(new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        notificationCommentList.setAdapter(notificationCommentListAdapter);

        notificationCommentSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationCommentInput.getText().toString().length() > 0){
                    notificationNews.addComment(new Comment("0", notificationCommentInput.getText().toString(), ((MainActivity)getContext()).me));
                    notificationCommentCounter.setText(String.valueOf(notificationNews.getNewsComments().size()));
                    notificationCommentInput.setText("");
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(notificationCommentInput.getWindowToken(), 0);
                    notificationCommentListAdapter.notifyDataSetChanged();
                    notificationCommentList.scrollToPosition(notificationNews.getNewsComments().size()-1);
                }
            }
        });
    }
}