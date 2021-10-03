package com.globapp.globapp.fragmentmain.fragmentnotifications;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.Comment;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.globapp.globapp.fragmentmain.fragmentme.FragmentMe;
import com.globapp.globapp.fragmentmain.fragmentnews.CommentListAdapter;
import com.globapp.globapp.fragmentmain.fragmentuser.FragmentUser;
import com.google.android.material.textfield.TextInputEditText;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.realm.mongodb.mongo.iterable.MongoCursor;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class FragmentOnNotification extends Fragment {
    // DATA
    ObjectId notificationNewsID;
    News     notificationNews;
    User     notificationUserOwner;
    User     notificationUserRecognized;
    ArrayList<Comment> comments;

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
    GifImageView       notificationLoading;
    NestedScrollView   notificationNestedScroll;
    TextView           notificationTime;

    public FragmentOnNotification(ObjectId notificationNewsID){
        this.notificationNewsID = notificationNewsID;
        this.comments           = new ArrayList<>();
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        loadNewsData();
    }

    private void loadNewsData(){
        Document newsQuery = new Document().append("_id", notificationNewsID);
        ((MainActivity)getContext()).newsCollection.findOne(newsQuery).getAsync(newsData -> {
            if(newsData.isSuccess()){
                if(newsData.get().getObjectId("user_recognized_id") != null){
                    notificationNews = new NewsRecognition(
                            newsData.get().getObjectId("_id"),
                            newsData.get().getString("content"),
                            newsData.get().getDate("date"),
                            null,
                            newsData.get().getInteger("likes"),
                            newsData.get().getInteger("comments"),
                            newsData.get().getList("users_likes_id", ObjectId.class, new ArrayList<>()).contains(((MainActivity)getContext()).me.getUserID()),
                            newsData.get().getObjectId("user_owner_id"),
                            newsData.get().getObjectId("user_recognized_id"));

                } else {
                    notificationNews = new News(
                            newsData.get().getObjectId("_id"),
                            newsData.get().getString("content"),
                            newsData.get().getDate("date"),
                            null,
                            newsData.get().getInteger("likes"),
                            newsData.get().getInteger("comments"),
                            newsData.get().getList("users_likes_id", ObjectId.class, new ArrayList<>()).contains(((MainActivity)getContext()).me.getUserID()),
                            newsData.get().getObjectId("user_owner_id"));

                }
                loadComponents();
            }
        });
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
        notificationNestedScroll         = getView().findViewById(R.id.on_notification_nested_scroll);
        notificationLoading              = getView().findViewById(R.id.on_notification_loading);
        notificationTime                 = getView().findViewById(R.id.on_notification_time);

        // POST DATA
        if(notificationNews.getNewsUserLiked()) {
            notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
        } else {
            if (((MainActivity) getContext()).isDarkMode)
                notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
            else
                notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        notificationTime.setText(notificationNews.getNewsDate().toString());
        notificationPostContent.setText(notificationNews.getNewsContent());
        notificationLikeCounter.setText(String.valueOf(notificationNews.getNewsLikes()));
        notificationCommentCounter.setText(String.valueOf(notificationNews.getNewsComments()));
        notificationPostImage.setImageURI(notificationNews.getNewsImage());
        if(notificationNews instanceof NewsRecognition){
            notificationRecognitionLayout.setVisibility(View.VISIBLE);

        } else {
            notificationRecognitionLayout.setVisibility(View.GONE);
        }

        // USERS POST DATA
        Document userOwnerQuery = new Document("_id", notificationNews.getNewsUserOwner());
        ((MainActivity)getContext()).userCollection.findOne(userOwnerQuery).getAsync(userOwnerData -> {
            if(userOwnerData.isSuccess()){
                notificationUserOwner = new User(
                        userOwnerData.get().getObjectId("_id"),
                        userOwnerData.get().getString("firstName"),
                        userOwnerData.get().getString("secondName"),
                        userOwnerData.get().getString("lastName"),
                        userOwnerData.get().getString("description"),
                        null,
                        null,
                        new ArrayList<>(),
                        userOwnerData.get().getInteger("credits",0),
                        userOwnerData.get().getInteger("stars",0));

                if(notificationUserOwner.getUserImage() != null)
                    notificationUserImage.setImageURI(notificationUserOwner.getUserImage());
                else
                    notificationUserImage.setImageResource(R.drawable.user);

                if(notificationNews instanceof NewsRecognition){
                    Document userRecognizedQuery = new Document("_id", ((NewsRecognition)notificationNews).getNewsUserRecognized());
                    ((MainActivity)getContext()).userCollection.findOne(userRecognizedQuery).getAsync(userRecognizedData -> {
                        if(userRecognizedData.isSuccess()){
                            notificationUserRecognized = new User(
                                    userRecognizedData.get().getObjectId("_id"),
                                    userRecognizedData.get().getString("firstName"),
                                    userRecognizedData.get().getString("secondName"),
                                    userRecognizedData.get().getString("lastName"),
                                    userRecognizedData.get().getString("description"),
                                    null,
                                    null,
                                    new ArrayList<>(),
                                    userRecognizedData.get().getInteger("credits",0),
                                    userRecognizedData.get().getInteger("stars",0));


                            notificationUsername.setText(
                                    notificationUserOwner.getUserFirstName()      + " " +
                                    notificationUserOwner.getUserLastName()       + " congratulated " +
                                    notificationUserRecognized.getUserFirstName() + " " +
                                    notificationUserRecognized.getUserLastName());

                            if(notificationUserRecognized.getUserImage() != null)
                                notificationRecognitionUserImage.setImageURI(notificationUserRecognized.getUserImage());
                            else
                                notificationRecognitionUserImage.setImageResource(R.drawable.user);
                        }
                    });
                } else {
                    notificationUsername.setText(
                            notificationUserOwner.getUserFirstName()  + " " +
                            notificationUserOwner.getUserSecondName() + " " +
                            notificationUserOwner.getUserLastName());
                }
            }
        });

        ((GifDrawable)notificationStar.getDrawable()).stop();
        ((GifDrawable)notificationStar.getDrawable()).seekTo(0);
        notificationStar.setOnClickListener(v -> {
            ((GifDrawable)notificationStar.getDrawable()).start();
            ((GifDrawable)notificationStar.getDrawable()).addAnimationListener(i ->
                    ((GifDrawable)notificationStar.getDrawable()).stop());
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
                    if(notificationNews.getNewsUserLiked()){
                        if (((MainActivity) getContext()).isDarkMode)
                            notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
                        else
                            notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        notificationNews.removeLike();

                    } else {
                        notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                        notificationNews.addLike();
                    }
                    notificationLikeCounter.setText(String.valueOf(notificationNews.getNewsLikes()));
                    likeNews();
                    return super.onDoubleTap(e);
                }
            });
        });

        notificationUserImage.setOnClickListener(v -> {
            if(((MainActivity)getContext()).me.getUserID().equals(notificationNews.getNewsUserOwner())){
                if(((MainActivity)getContext()).getSupportFragmentManager().getBackStackEntryCount() == 1){
                    ((MainActivity)getContext()).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                } else {
                    ((MainActivity)getContext()).addFragmentLeft(
                            new FragmentMe());
                }
            } else {
                ((MainActivity)getContext()).addFragmentLeft(
                        new FragmentUser(notificationNews.getNewsUserOwner()));
            }
        });

        notificationRecognitionUserImage.setOnClickListener(v -> {
            if(((MainActivity)getContext()).me.getUserID().equals(((NewsRecognition)notificationNews).getNewsUserRecognized())){
                if(((MainActivity)getContext()).getSupportFragmentManager().getBackStackEntryCount() == 1){
                    ((MainActivity)getContext()).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                } else {
                    ((MainActivity)getContext()).addFragmentRight(new FragmentMe());
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

                if(notificationNews.getNewsUserLiked()){
                    if (((MainActivity) getContext()).isDarkMode)
                        notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_white_24);
                    else
                        notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    notificationNews.removeLike();

                } else {
                    notificationLikeButton.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                    notificationNews.addLike();
                }
                notificationLikeCounter.setText(String.valueOf(notificationNews.getNewsLikes()));
                likeNews();

            } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                notificationLikeButton.setAlpha((float) 0.5);
            } else if (event.getAction() == MotionEvent.ACTION_UP){
                notificationLikeButton.setAlpha((float) 1);
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                notificationLikeButton.setAlpha((float) 1);
            }
            return true;
        });

        loadComments();

        notificationCommentSendButton.setOnClickListener(v -> {
            if(notificationCommentInput.getText().toString().length() > 0){
                ((MainActivity)getContext()).newsCollection.findOne(new Document().append("_id", notificationNews.getNewsID())).getAsync(result -> {
                    if(result.isSuccess()){
                        ((MainActivity)getContext()).newsCollection.findOneAndUpdate(new Document().append("_id", notificationNews.getNewsID()), result.get().append("comments", result.get().getInteger("comments")+1)).getAsync(result1 -> {
                        });
                    }
                });

                Document commentInsert = new Document()
                        .append("content", notificationCommentInput.getText().toString())
                        .append("news_id", notificationNews.getNewsID())
                        .append("date", Calendar.getInstance().getTime())
                        .append("user_id", ((MainActivity)getContext()).me.getUserID());

                String commentContent = notificationCommentInput.getText().toString();

                ((MainActivity)getContext()).commentsCollection.insertOne(commentInsert).getAsync(result -> {
                    if(result.isSuccess()){
                        Toast.makeText(getContext(), "COMENTARIO PUBLICADO", Toast.LENGTH_LONG).show();
                        comments.add(new Comment(
                                result.get().getInsertedId().asObjectId().getValue(),
                                commentContent,
                                Calendar.getInstance().getTime(),
                                ((MainActivity)getContext()).me.getUserID()));
                        notificationCommentListAdapter.notifyItemInserted(comments.size()-1);
                        notificationNestedScroll.post(() -> notificationNestedScroll.fullScroll(RecyclerView.FOCUS_DOWN));
                    }
                });



                notificationCommentInput.setText("");
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(notificationCommentInput.getWindowToken(), 0);
            }
        });

        notificationLoading.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((GifDrawable)notificationLoading.getDrawable()).stop();
                        notificationLoading.setClickable(false);
                        notificationLoading.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void likeNews(){

        if(((MainActivity)getContext()).databaseConnection != null){
            Document newsQuery = new Document().append("_id", notificationNews.getNewsID());

            ((MainActivity)getContext()).newsCollection.findOne(newsQuery).getAsync(result -> {
                if(result.isSuccess()){

                    ArrayList<ObjectId> listLiked = new ArrayList<>(result.get().getList("users_likes_id", ObjectId.class, new ArrayList<>()));

                    if(notificationNews.getNewsUserLiked()){
                        if(listLiked.contains(((MainActivity)getContext()).me.getUserID())){
                            listLiked.remove(((MainActivity)getContext()).me.getUserID());
                            notificationNews.setNewsUserLiked(false);
                            result.get().append("likes", result.get().getInteger("likes")-1);
                        }
                    } else {
                        if(!listLiked.contains(((MainActivity)getContext()).me.getUserID())){
                            listLiked.add(((MainActivity)getContext()).me.getUserID());
                            notificationNews.setNewsUserLiked(true);
                            result.get().append("likes", result.get().getInteger("likes")+1);
                        }

                    }

                    Document newsUpdate = result.get().append("users_likes_id", listLiked);
                    ((MainActivity)getContext()).newsCollection.findOneAndUpdate(newsQuery, newsUpdate).getAsync(result1 -> {

                    });
                }
            });
        } else {
            ((MainActivity)getContext()).connectDB();
        }
    }

    private void loadComments(){
        comments.clear();
        if(notificationCommentListAdapter != null) notificationCommentListAdapter.notifyDataSetChanged();
        notificationCommentList = getView().findViewById(R.id.on_notification_comment_list);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        notificationCommentListAdapter = new CommentListAdapter(getContext(), comments);
        notificationCommentList.setLayoutManager(verticalLayoutManager);
        notificationCommentList.setAdapter(notificationCommentListAdapter);

        ((MainActivity)getContext()).commentsCollection.find(new Document().append("news_id", notificationNews.getNewsID())).iterator().getAsync(result -> {
            if(result.isSuccess()){
                try {
                    gettingDataComments(result.get());
                } catch (Exception ignored){

                }
            }
        });
    }

    private void gettingDataComments(MongoCursor<Document> data) throws Exception {
        if(!data.hasNext()){
            //newsRefresh.setRefreshing(false);
        } else {
            Document document = data.next();
            comments.add(new Comment(
                    document.getObjectId("_id"),
                    document.getString("content"),
                    document.getDate("date"),
                    document.getObjectId("user_id")
            ));

            notificationCommentListAdapter.notifyItemInserted(comments.size()-1);
            try {
                gettingDataComments(data);
            } catch (Exception ignored){

            }
        }
    }
}