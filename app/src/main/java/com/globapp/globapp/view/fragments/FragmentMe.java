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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.UserNameGetter;
import com.globapp.globapp.view.adapters.MePagerAdapter;

import org.bson.types.ObjectId;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMe extends Fragment {
    // Data
    private User me;

    // UI Components
    private RecyclerView       recognitionPager;
    private MePagerAdapter     recognitionPagerAdapter;
    private ImageView          meCoverImage;
    private CircleImageView    meImage;
    private TextView           meName;
    private TextView           meDescription;
    private TextView           meCredits;
    private TextView           meStars;
    private TextView           meRecognitionText;
    private SwipeRefreshLayout meRefresh;

    @SuppressLint("InflateParams") @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(Preferences.getDarkMode()){
            return inflater.inflate(R.layout.fragment_me_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_me, null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserData();
    }

    private void loadUserData(){
        ObjectId userSessionID = new ObjectId(UserSessionController.getUserSessionID());
        DataRepository.getUser(userSessionID, user -> {
            me = user;
            loadComponents();
        });
    }

    private void updateUserData(){
        meName.setText(UserNameGetter.getUserName(me));
        meDescription.setText(me.getUserDescription());
        meStars.setText(String.valueOf(me.getUserStars()));
        meCredits.setText(String.valueOf(me.getUserCredits()));
        meImage.setImageURI(me.getUserImage());
        meCoverImage.setImageURI(me.getUserCoverImage());
    }

    private void refreshFunction(){
        meRefresh.setOnRefreshListener(this::loadUserData);
        meRefresh.setRefreshing(false);
    }

    @SuppressLint("SetTextI18n")
    void loadComponents(){
        meName            = requireView().findViewById(R.id.me_user_name);
        meDescription     = requireView().findViewById(R.id.me_user_description);
        meCredits         = requireView().findViewById(R.id.me_user_credits);
        meStars           = requireView().findViewById(R.id.me_user_stars);
        meCoverImage      = requireView().findViewById(R.id.me_cover_image);
        meImage           = requireView().findViewById(R.id.me_user_image);
        meRecognitionText = requireView().findViewById(R.id.me_user_recognitions_text);
        recognitionPager  = requireView().findViewById(R.id.me_user_recognitions);
        meRefresh         = requireView().findViewById(R.id.me_refresh);

        updateUserData();
        refreshFunction();


        /*
        if(me.getUserRecognitions().size() == 0){
            meRecognitionText.setVisibility(View.GONE);
        } else {
            meRecognitionText.setVisibility(View.VISIBLE);
        }

        recognitionPagerAdapter = new MePagerAdapter(getContext(), me.getUserRecognitions());
        recognitionPager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recognitionPager.setAdapter(recognitionPagerAdapter);

         */
    }
}