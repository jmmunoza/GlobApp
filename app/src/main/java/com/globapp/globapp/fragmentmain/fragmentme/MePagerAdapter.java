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
import com.globapp.globapp.R;
import com.globapp.globapp.classes.Recognition;

import java.util.ArrayList;

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
        return new ViewHolder(inflater.inflate(R.layout.fragment_me_recognition, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recognition recognition = recognitionsPager.get(position);

        holder.pagerTitle.setText(recognition.getRecognitionTitle());
        switch (recognition.getRecognitionImage()){
            case 1:
                holder.pagerImage.setImageResource(R.drawable.merecognition1);
                break;
            case 2:
                holder.pagerImage.setImageResource(R.drawable.merecognition2);
                break;
            case 3:
                holder.pagerImage.setImageResource(R.drawable.merecognition3);
                break;
            case 4:
                holder.pagerImage.setImageResource(R.drawable.merecognition4);
                break;
            case 5:
                holder.pagerImage.setImageResource(R.drawable.merecognition5);
                break;
        }
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
