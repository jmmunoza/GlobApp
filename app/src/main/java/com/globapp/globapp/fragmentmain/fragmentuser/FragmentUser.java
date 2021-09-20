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
    RecyclerView    recognitionPager;
    MePagerAdapter  recognitionPagerAdapter;
    ConstraintLayout userStarButton;
    TextView        userName;
    TextView        userDescription;
    ImageView       userCoverImage;
    ImageView       userImage;

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
        userDescription.setText(user.getMeDescription());

        switch (user.getMeImage()){
            case 1:
                userImage.setImageResource(R.drawable.meimage1);
                break;
            case 2:
                userImage.setImageResource(R.drawable.user2);
                break;
            case 3:
                userImage.setImageResource(R.drawable.user3);
                break;
            case 4:
                userImage.setImageResource(R.drawable.user4);
                break;
            case 5:
                userImage.setImageResource(R.drawable.user5);
                break;
            case 6:
                userImage.setImageResource(R.drawable.user6);
                break;
            case 7:
                userImage.setImageResource(R.drawable.user7);
                break;
            case 8:
                userImage.setImageResource(R.drawable.user8);
                break;
        }

        switch (user.getMeCoverImage()){
            case 1:
                userCoverImage.setImageResource(R.drawable.mecoverimage1);
        }


        recognitionPagerAdapter = new MePagerAdapter(getContext(), user.getMeRecognitions());

        RecyclerView.OnItemTouchListener listener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                if (recognitionPager.canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
                    switch (action){
                        case MotionEvent.ACTION_MOVE:
                            rv.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                } else {
                    switch (action){
                        case MotionEvent.ACTION_MOVE:
                            rv.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    recognitionPager.removeOnItemTouchListener(this);
                    return true;
                }
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        };

        recognitionPager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recognitionPager.setAdapter(recognitionPagerAdapter);
        recognitionPager.addOnItemTouchListener(listener);
    }
}
