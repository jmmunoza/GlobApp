package com.globapp.globapp.fragmentmain.fragmentuser;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FragmentUser extends Fragment {
    // Data
    User user;

    // UI Components
    RecyclerView     recognitionPager;
    MePagerAdapter   recognitionPagerAdapter;
    ConstraintLayout userStarButton;
    TextView         userName;
    TextView         userDescription;
    ImageView        userCoverImage;
    ImageView        userImage;

    public FragmentUser(User user){
        this.user = user;
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
        loadComponents();
    }

    void loadComponents(){
        userStarButton   = getView().findViewById(R.id.user_star_button);
        userName         = getView().findViewById(R.id.user_name);
        userDescription  = getView().findViewById(R.id.user_description);
        userCoverImage   = getView().findViewById(R.id.user_cover_image);
        userImage        = getView().findViewById(R.id.user_image);
        recognitionPager = getView().findViewById(R.id.user_recognitions);

        userName.setText(user.getMeName());
        userImage.setImageURI(user.getMeImage());
        userCoverImage.setImageURI(user.getMeCoverImage());
        userDescription.setText(user.getMeDescription());
        userStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).addFragmentUp(new FragmentGiveStar(user));
            }
        });

        recognitionPagerAdapter = new MePagerAdapter(getContext(), user.getMeRecognitions());
        recognitionPager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recognitionPager.setAdapter(recognitionPagerAdapter);
    }
}
