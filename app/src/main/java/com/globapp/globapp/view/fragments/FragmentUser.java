package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.ImageConverter;
import com.globapp.globapp.util.UserNameGetter;
import com.globapp.globapp.view.adapters.MePagerAdapter;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FragmentUser extends Fragment {
    // Data
    private final ObjectId userID;
    private User     user;

    // UI Components
    private RecyclerView     recognitionPager;
    private MePagerAdapter   recognitionPagerAdapter;
    private ConstraintLayout userStarButton;
    private TextView         userName;
    private TextView         userStars;
    private TextView         userDescription;
    private ImageView        userCoverImage;
    private ImageView        userImage;

    // Listener
    private OnUserListener onUserListener;

    public FragmentUser(ObjectId userID){
        this.userID = userID;
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_user, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserData();
    }

    private void loadUserData(){
        DataRepository.getUser(userID, user -> {
            FragmentUser.this.user = user;
            loadComponents();
        });
    }


    private void loadComponents(){
        userStarButton   = requireView().findViewById(R.id.user_star_button);
        userName         = requireView().findViewById(R.id.user_name);
        userDescription  = requireView().findViewById(R.id.user_description);
        userCoverImage   = requireView().findViewById(R.id.user_cover_image);
        userImage        = requireView().findViewById(R.id.user_image);
        recognitionPager = requireView().findViewById(R.id.user_recognitions);
        userStars        = requireView().findViewById(R.id.user_stars);

        updateUserData();
        starButtonFunction();
        recognitionListFunction();
    }

    private void recognitionListFunction(){
        recognitionPagerAdapter = new MePagerAdapter(getContext(), new ArrayList<>());
        recognitionPager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recognitionPager.setAdapter(recognitionPagerAdapter);
    }

    private void updateUserData(){
        userStars.setText(String.valueOf(user.getUserStars()));
        userName.setText(UserNameGetter.getUserName(user));
        userImage.setImageBitmap(ImageConverter.ByteArrayToBitmap(user.getUserImage()));

        userDescription.setText(user.getUserDescription());
        if(user.getUserCoverImage() != null){
            userCoverImage.setImageBitmap(ImageConverter.ByteArrayToBitmap(user.getUserCoverImage()));
        } else {
            userCoverImage.setImageResource(R.drawable.user);
        }

        if(user.getUserImage() != null){
            userImage.setImageBitmap(ImageConverter.ByteArrayToBitmap(user.getUserImage()));
        } else {
            userImage.setImageResource(R.drawable.user);
        }
    }

    private void starButtonFunction(){
        userStarButton.setOnClickListener(v -> onUserListener.giveStar(userID));
    }

    public interface OnUserListener {
        void giveStar(ObjectId userID);
    }

    public void addOnUserListener(OnUserListener onUserListener){
        this.onUserListener = onUserListener;
    }
}
