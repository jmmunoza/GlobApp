package com.globapp.globapp.fragmentmain.fragmentme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.Recognition;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MePagerAdapter extends RecyclerView.Adapter<MePagerAdapter.ViewHolder> {

    private ArrayList<Recognition> recognitionsPager;
    private LayoutInflater inflater;
    private Context context;

    public MePagerAdapter(Context context, ArrayList<Recognition> recognitionsPager) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.recognitionsPager = recognitionsPager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(((MainActivity)context).isDarkMode){
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

    public class ViewHolder extends RecyclerView.ViewHolder {

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
