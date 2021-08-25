package com.globapp.globapp.fragmentmain.fragmentuser;

import androidx.annotation.NonNull;
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
    RecyclerView    userActivityList;
    NewsListAdapter userActivityListAdapter;
    ImageButton     userStarButton;
    TextView        userName;
    TextView        userDescription;
    ImageView       userCoverImage;
    ImageView       userImage;

    public FragmentUser(){

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
        userActivityList = getView().findViewById(R.id.user_activity);
        recognitionPager = getView().findViewById(R.id.user_recognitions);

        // Temporal ----------------------
        ArrayList<String> recognitions = new ArrayList<>();
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        recognitions.add("NOTIFICATION");
        // -------------------------------------



        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        userActivityListAdapter = new NewsListAdapter(getContext(), ((MainActivity)getContext()).me.getMeNews());
        userActivityList.setLayoutManager(verticalLayoutManager);
        userActivityList.setAdapter(userActivityListAdapter);


        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recognitionPagerAdapter = new MePagerAdapter(getContext(), ((MainActivity)getContext()).me.getMeRecognitions());

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

        recognitionPager.setLayoutManager(horizontalLayoutManager);
        recognitionPager.setAdapter(recognitionPagerAdapter);
        recognitionPager.addOnItemTouchListener(listener);
    }
}
