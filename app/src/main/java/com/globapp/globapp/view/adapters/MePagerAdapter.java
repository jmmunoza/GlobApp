package com.globapp.globapp.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.R;
import com.globapp.globapp.model.Recognition;

import java.util.ArrayList;

public class MePagerAdapter extends RecyclerView.Adapter<MePagerAdapter.ViewHolder> {

    private final ArrayList<Recognition> recognitionsPager;
    private final LayoutInflater inflater;

    public MePagerAdapter(Context context, ArrayList<Recognition> recognitionsPager) {
        this.inflater = LayoutInflater.from(context);
        this.recognitionsPager = recognitionsPager;
    }

    @SuppressLint("InflateParams") @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new ViewHolder(inflater.inflate(R.layout.fragment_me_recognition_dark, null));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.fragment_me_recognition, null));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recognition recognition = recognitionsPager.get(position);
        holder.pagerTitle.setText(recognition.getRecognitionTitle());
    }

    @Override
    public int getItemCount() {
        return recognitionsPager.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView pagerBackground;
        ImageView pagerImage;
        TextView pagerTitle;

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(View itemView) {
            super(itemView);
            pagerBackground  = itemView.findViewById(R.id.me_recognition_background);
            pagerImage       = itemView.findViewById(R.id.me_recognition_image);
            pagerTitle       = itemView.findViewById(R.id.me_recognition_title);
        }
    }
}
