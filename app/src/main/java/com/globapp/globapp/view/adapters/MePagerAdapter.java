package com.globapp.globapp.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.model.Recognition;
import com.globapp.globapp.view.viewholders.MePagerViewHolder;

import java.util.ArrayList;

public class MePagerAdapter extends RecyclerView.Adapter<MePagerViewHolder> {

    private final ArrayList<Recognition> recognitionsPager;
    private final LayoutInflater inflater;

    public MePagerAdapter(Context context, ArrayList<Recognition> recognitionsPager) {
        this.inflater = LayoutInflater.from(context);
        this.recognitionsPager = recognitionsPager;
    }

    @SuppressLint("InflateParams") @NonNull @Override
    public MePagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new MePagerViewHolder(inflater.inflate(R.layout.fragment_me_recognition_dark, null));
        } else {
            return new MePagerViewHolder(inflater.inflate(R.layout.fragment_me_recognition, null));
        }
    }

    @Override
    public void onBindViewHolder(MePagerViewHolder holder, int position) {
        Recognition recognition = recognitionsPager.get(position);
        holder.pagerTitle.setText(recognition.getRecognitionTitle());
    }

    @Override
    public int getItemCount() {
        return recognitionsPager.size();
    }
}
