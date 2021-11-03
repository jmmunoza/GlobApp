package com.globapp.globapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.util.NotificationTextGetter;
import com.globapp.globapp.view.fragments.FragmentNotifications;
import com.globapp.globapp.view.viewholders.NotificationListViewHolder;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationListViewHolder> {

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
    public NotificationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new NotificationListViewHolder(
                    inflater.inflate(R.layout.fragment_notification_item_dark, parent, false),
                    onNotificationsListListener);
        } else {
            return new NotificationListViewHolder(
                    inflater.inflate(R.layout.fragment_notification_item, parent, false),
                    onNotificationsListListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        holder.notificationDate.setText(notification.getNotificationDate().toString());
        holder.setNewsID(notification.getNotificationNews());
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

    public void insertNotification(Notification notification){
        notificationsList.add(0, notification);
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
}