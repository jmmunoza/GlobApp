package com.globapp.globapp.fragmentmain.fragmentme;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.Me;
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentmain.fragmentnews.NewsListAdapter;
import com.globapp.globapp.fragmentmain.fragmentnews.NewsPagerAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMe extends Fragment {
    // Data
    Me me;

    // UI Components
    RecyclerView    recognitionPager;
    MePagerAdapter  recognitionPagerAdapter;
    ImageView       meCoverImage;
    CircleImageView meImage;
    TextView        meName;
    TextView        meDescription;
    TextView        meCredits;
    TextView        meStars;

    public FragmentMe(Me me){
        this.me = me;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(((MainActivity)getContext()).isDarkMode){
            return inflater.inflate(R.layout.fragment_me_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_me, null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComponents();
    }

    void loadComponents(){
        meName           = getView().findViewById(R.id.me_user_name);
        meDescription    = getView().findViewById(R.id.me_user_description);
        meCredits        = getView().findViewById(R.id.me_user_credits);
        meStars          = getView().findViewById(R.id.me_user_stars);
        meCoverImage     = getView().findViewById(R.id.me_cover_image);
        meImage          = getView().findViewById(R.id.me_user_image);
        recognitionPager = getView().findViewById(R.id.me_user_recognitions);

        meName.setText(me.getMeName());
        meDescription.setText(me.getMeDescription());
        meStars.setText(String.valueOf(me.getMeStars()));
        meCredits.setText(String.valueOf(me.getMeCredits()));

        switch (me.getMeImage()){
            case 1:
                meImage.setImageResource(R.drawable.meimage1);
                break;
        }

        switch (me.getMeCoverImage()){
            case 1:
                meCoverImage.setImageResource(R.drawable.mecoverimage1);
        }
        
        recognitionPagerAdapter = new MePagerAdapter(getContext(), me.getMeRecognitions());
        recognitionPager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recognitionPager.setAdapter(recognitionPagerAdapter);
    }
}