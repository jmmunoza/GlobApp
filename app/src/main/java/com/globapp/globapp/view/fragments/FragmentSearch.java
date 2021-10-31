package com.globapp.globapp.view.fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.view.MainActivity;
import com.globapp.globapp.R;

import java.util.concurrent.TimeUnit;

public class FragmentSearch extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(Preferences.getDarkMode()){
            return inflater.inflate(R.layout.fragment_search_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_search, null);
        }
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

    }
}