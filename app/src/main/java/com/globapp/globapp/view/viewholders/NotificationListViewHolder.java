package com.globapp.globapp.view.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.view.fragments.FragmentNotifications;

import org.bson.types.ObjectId;

public class NotificationListViewHolder extends RecyclerView.ViewHolder {
    public TextView         notificationText;
    public ImageView        notificationUserImage;
    public ConstraintLayout notificationBackground;
    public TextView         notificationDate;

    // Listener
    private final FragmentNotifications.OnNotificationsListListener onNotificationsListListener;

    // Data
    private ObjectId newsID;

    public NotificationListViewHolder(View itemView, FragmentNotifications.OnNotificationsListListener onNotificationsListListener) {
        super(itemView);

        this.onNotificationsListListener = onNotificationsListListener;

        notificationText        =  itemView.findViewById(R.id.notification_item_text);
        notificationUserImage   =  itemView.findViewById(R.id.notification_item_user_image);
        notificationBackground  =  itemView.findViewById(R.id.notification_item_background);
        notificationDate        =  itemView.findViewById(R.id.notification_item_time);

        backgroundFunction();
    }

    public void setNewsID(ObjectId newsID){
        this.newsID = newsID;
    }

    private void backgroundFunction(){
        notificationBackground.setOnClickListener(v -> onNotificationsListListener.onNewsClicked(newsID));
    }
}