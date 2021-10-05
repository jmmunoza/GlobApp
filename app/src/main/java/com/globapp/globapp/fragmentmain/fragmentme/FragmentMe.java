package com.globapp.globapp.fragmentmain.fragmentme;

import androidx.annotation.NonNull;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.User;
import org.bson.Document;
import java.util.concurrent.TimeUnit;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMe extends Fragment {
    // Data
    User me;

    // UI Components
    RecyclerView       recognitionPager;
    MePagerAdapter     recognitionPagerAdapter;
    ImageView          meCoverImage;
    CircleImageView    meImage;
    TextView           meName;
    TextView           meDescription;
    TextView           meCredits;
    TextView           meStars;
    TextView           meRecognitionText;
    SwipeRefreshLayout meRefresh;

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

    @SuppressLint("SetTextI18n")
    void loadComponents(){
        me                = ((MainActivity)getContext()).me;
        meName            = getView().findViewById(R.id.me_user_name);
        meDescription     = getView().findViewById(R.id.me_user_description);
        meCredits         = getView().findViewById(R.id.me_user_credits);
        meStars           = getView().findViewById(R.id.me_user_stars);
        meCoverImage      = getView().findViewById(R.id.me_cover_image);
        meImage           = getView().findViewById(R.id.me_user_image);
        meRecognitionText = getView().findViewById(R.id.me_user_recognitions_text);
        recognitionPager  = getView().findViewById(R.id.me_user_recognitions);
        meRefresh         = getView().findViewById(R.id.me_refresh);

        meRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Document userQuery = new Document("_id", ((MainActivity)getContext()).me.getUserID());

                        ((MainActivity)getContext()).userCollection.findOne(userQuery).getAsync(result -> {
                            if(result.isSuccess()){
                                ((MainActivity)getContext()).me.setUserStars(result.get().getInteger("stars"));
                                ((MainActivity)getContext()).me.setUserCredits(result.get().getInteger("credits"));
                                ((MainActivity)getContext()).me.setUserDescription(result.get().getString("description"));
                                me = ((MainActivity)getContext()).me;

                                meStars.setText(String.valueOf(me.getUserStars()));
                                meCredits.setText(String.valueOf(me.getUserCredits()));
                                meDescription.setText(me.getUserDescription());
                                if(me.getUserImage() != null) meImage.setImageURI(me.getUserImage());
                                if(me.getUserCoverImage() != null) meCoverImage.setImageURI(me.getUserCoverImage());
                                if(me.getUserRecognitions().size() == 0){
                                    meRecognitionText.setVisibility(View.GONE);
                                } else {
                                    meRecognitionText.setVisibility(View.VISIBLE);
                                }
                                recognitionPagerAdapter.notifyDataSetChanged();
                                meRefresh.setRefreshing(false);
                            } else {
                                ((MainActivity)getContext()).connectDB();
                            }
                        });
                    }
                });
            }
        });

        if(me.getUserSecondName() != null){
            meName.setText(me.getUserFirstName() + " " + me.getUserSecondName() + " " +  me.getUserLastName());
        } else {
            meName.setText(me.getUserFirstName() + " " +  me.getUserLastName());
        }

        meDescription.setText(me.getUserDescription());
        meStars.setText(String.valueOf(me.getUserStars()));
        meCredits.setText(String.valueOf(me.getUserCredits()));
        meImage.setImageURI(me.getUserImage());
        meCoverImage.setImageURI(me.getUserCoverImage());

        if(me.getUserRecognitions().size() == 0){
            meRecognitionText.setVisibility(View.GONE);
        } else {
            meRecognitionText.setVisibility(View.VISIBLE);
        }

        recognitionPagerAdapter = new MePagerAdapter(getContext(), me.getUserRecognitions());
        recognitionPager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recognitionPager.setAdapter(recognitionPagerAdapter);
    }
}