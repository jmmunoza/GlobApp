package com.globapp.globapp.fragmentmain.fragmentnotifications;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;

import java.util.ArrayList;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {

    private ArrayList<String> notificationsList;
    private LayoutInflater inflater;
    Context context;

    public NotificationsListAdapter(Context context, ArrayList<String> notificationsList){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(((MainActivity)context).isDarkMode){
            return new ViewHolder(inflater.inflate(R.layout.fragment_notification_item_dark, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.fragment_notification_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notificationText;
        ImageView notificationUserImage;
        ConstraintLayout notificationBackground;

        ViewHolder(View itemView) {
            super(itemView);

            notificationText       = (TextView) itemView.findViewById(R.id.notification_item_text);
            notificationUserImage  = (ImageView) itemView.findViewById(R.id.notification_item_user_image);
            notificationBackground = (ConstraintLayout) itemView.findViewById(R.id.notification_item_background);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
