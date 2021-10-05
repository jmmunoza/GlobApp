package com.globapp.globapp.fragmentmain.fragmentuser;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentmain.fragmentme.MePagerAdapter;
import com.globapp.globapp.fragmentmain.fragmentnews.NewsListAdapter;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FragmentUser extends Fragment {
    // Data
    ObjectId userID;

    // UI Components
    RecyclerView     recognitionPager;
    MePagerAdapter   recognitionPagerAdapter;
    ConstraintLayout userStarButton;
    TextView         userName;
    TextView         userStars;
    TextView         userDescription;
    ImageView        userCoverImage;
    ImageView        userImage;

    public FragmentUser(ObjectId userID){
        this.userID = userID;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(((MainActivity)getContext()).isDarkMode){
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
    void loadComponents(User user){
        userStarButton   = getView().findViewById(R.id.user_star_button);
        userName         = getView().findViewById(R.id.user_name);
        userDescription  = getView().findViewById(R.id.user_description);
        userCoverImage   = getView().findViewById(R.id.user_cover_image);
        userImage        = getView().findViewById(R.id.user_image);
        recognitionPager = getView().findViewById(R.id.user_recognitions);
        userStars        = getView().findViewById(R.id.user_stars);

        userStars.setText(String.valueOf(user.getUserStars()));

        if(user.getUserSecondName() != null){
            userName.setText(user.getUserFirstName() + " " + user.getUserSecondName() + " " +  user.getUserLastName());
        } else {
            userName.setText(user.getUserFirstName() + " " + user.getUserLastName());
        }

        userImage.setImageURI(user.getUserImage());
        userCoverImage.setImageURI(user.getUserCoverImage());
        userDescription.setText(user.getUserDescription());
        userStarButton.setOnClickListener(v -> ((MainActivity)getContext()).addFragmentUp(new FragmentGiveStar(user)));

        recognitionPagerAdapter = new MePagerAdapter(getContext(), user.getUserRecognitions());
        recognitionPager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recognitionPager.setAdapter(recognitionPagerAdapter);
    }
}
