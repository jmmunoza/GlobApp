package com.globapp.globapp.view.fragments;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.view.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.model.User;
import com.globapp.globapp.view.adapters.MePagerAdapter;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FragmentUser extends Fragment {
    // Data
    private ObjectId userID;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(Preferences.getDarkMode()){
            return inflater.inflate(R.layout.fragment_user_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_user, null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserData();
    }

    private void loadUserData(){
        Document userQuery = new Document("_id", userID);
        ((MainActivity)getContext()).userCollection.findOne(userQuery).getAsync(userData -> {
            if(userData.isSuccess()){
                User user = new User(
                        userData.get().getObjectId("_id"),
                        userData.get().getString("firstName"),
                        userData.get().getString("secondName"),
                        userData.get().getString("lastName"),
                        userData.get().getString("description"),
                        null,
                        null,
                        new ArrayList<>(),
                        userData.get().getInteger("credits",0),
                        userData.get().getInteger("stars",0));
                loadComponents(user);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadComponents(User user){
        userStarButton   = requireView().findViewById(R.id.user_star_button);
        userName         = requireView().findViewById(R.id.user_name);
        userDescription  = requireView().findViewById(R.id.user_description);
        userCoverImage   = requireView().findViewById(R.id.user_cover_image);
        userImage        = requireView().findViewById(R.id.user_image);
        recognitionPager = requireView().findViewById(R.id.user_recognitions);
        userStars        = requireView().findViewById(R.id.user_stars);

        descriptionFunction();
        starButtonFunction();
        starTextFunction();
        usernameFunction();
        userImageFunction();
        userCoverFunction();
        recognitionListFunction();
    }

    private void usernameFunction(){
        if(user.getUserSecondName() != null){
            userName.setText(user.getUserFirstName() + " " + user.getUserSecondName() + " " +  user.getUserLastName());
        } else {
            userName.setText(user.getUserFirstName() + " " + user.getUserLastName());
        }
    }

    private void starTextFunction(){
        userStars.setText(String.valueOf(user.getUserStars()));
    }

    private void recognitionListFunction(){
        recognitionPagerAdapter = new MePagerAdapter(getContext(), user.getUserRecognitions());
        recognitionPager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recognitionPager.setAdapter(recognitionPagerAdapter);
    }

    private void userImageFunction(){
        userImage.setImageURI(user.getUserImage());
    }

    private void userCoverFunction(){
        userCoverImage.setImageURI(user.getUserCoverImage());
    }

    private void descriptionFunction(){
        userDescription.setText(user.getUserDescription());
    }

    private void starButtonFunction(){
        userStarButton.setOnClickListener(v -> ((MainActivity)getContext()).addFragmentUp(new FragmentGiveStar(user)));
    }

    public interface OnUserListener {
        void giveStar(ObjectId userID);
    }

    public void addOnUserListener(OnUserListener onUserListener){
        this.onUserListener = onUserListener;
    }
}
