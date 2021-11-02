package com.globapp.globapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.util.NotificationTextGetter;
import com.globapp.globapp.view.fragments.FragmentNotifications;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {

    private final ArrayList<Notification> notificationsList;
    private final LayoutInflater          inflater;
    private int                           loadedNotifications;
    private DataLoadedListener            dataLoadedListener;
    private final FragmentNotifications.OnNotificationsListListener onNotificationsListListener;

    public NotificationsListAdapter(Context context, ArrayList<Notification> notificationsList, FragmentNotifications.OnNotificationsListListener onNotificationsListListener){
        this.inflater = LayoutInflater.from(context);
        this.loadedNotifications = 0;
        this.notificationsList = notificationsList;
        this.onNotificationsListListener = onNotificationsListListener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new ViewHolder(inflater.inflate(R.layout.fragment_notification_item_dark, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.fragment_notification_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        holder.notificationDate.setText(notification.getNotificationDate().toString());
        DataRepository.getNews(notification.getNotificationNews(), news -> DataRepository.getUser(new ObjectId(news.getNewsUserOwner()), userOwner -> {
            if(userOwner.getUserImage() != null){
                holder.notificationUserImage.setImageURI(userOwner.getUserImage());
            } else {
                holder.notificationUserImage.setImageResource(R.drawable.user);
            }

            if(news.getNewsUserRecognized() != null){
                DataRepository.getUser(new ObjectId(news.getNewsUserRecognized()), userRecognized -> {
                    if (userRecognized.getUserID().equals(UserSessionController.getUserSessionID())) {
                        holder.notificationText.setText(NotificationTextGetter.ifUserRecognizedYou(userOwner));
                    } else {
                        holder.notificationText.setText(NotificationTextGetter.ifUserRecognizedSomeoneElse(userOwner, userRecognized));
                    }
                    loadedNotifications++;
                    isDataLoaded();
                });
            } else {
                if(userOwner.getUserImage() != null){
                    holder.notificationText.setText(NotificationTextGetter.ifUserPostedAnImage(userOwner));
                } else {
                    holder.notificationText.setText(NotificationTextGetter.ifUserPosted(userOwner));
                }

                loadedNotifications++;
                isDataLoaded();
            }
        }));
    }

    public void addDataLoadedListener(DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
    }

    public interface DataLoadedListener {
        void onDataLoaded();
    }

    private void isDataLoaded() {
        if(notificationsList.size() == loadedNotifications){
            if(dataLoadedListener != null)
                dataLoadedListener.onDataLoaded();
        }
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView           notificationText;
        ImageView          notificationUserImage;
        ConstraintLayout   notificationBackground;
        TextView           notificationDate;

        ViewHolder(View itemView) {
            super(itemView);

            notificationText        =  itemView.findViewById(R.id.notification_item_text);
            notificationUserImage   =  itemView.findViewById(R.id.notification_item_user_image);
            notificationBackground  =  itemView.findViewById(R.id.notification_item_background);
            notificationDate        =  itemView.findViewById(R.id.notification_item_time);

            notificationBackground.setOnClickListener(v -> onNotificationsListListener.onNewsClicked(notificationsList.get(getAdapterPosition()).getNotificationNews()));
        }
    }
}