package com.globapp.globapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.util.DateTextGetter;
import com.globapp.globapp.util.ImageConverter;
import com.globapp.globapp.util.NotificationTextGetter;
import com.globapp.globapp.view.fragments.FragmentNotifications;
import com.globapp.globapp.view.viewholders.NotificationListViewHolder;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationListViewHolder> {

    private final ArrayList<Notification> notificationsList;
    private final LayoutInflater          inflater;
    private int                           loadedNotifications;
    private final Context                 context;
    private final FragmentNotifications.OnNotificationsListListener onNotificationsListListener;

    public NotificationsListAdapter(Context context, ArrayList<Notification> notificationsList, FragmentNotifications.OnNotificationsListListener onNotificationsListListener){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.loadedNotifications = 0;
        this.notificationsList = notificationsList;
        this.onNotificationsListListener = onNotificationsListListener;
    }

    @NonNull @Override
    public NotificationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NotificationListViewHolder(
                    inflater.inflate(R.layout.fragment_notification_item, parent, false),
                    onNotificationsListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        //-------------------
        holder.notificationDate.setText(DateTextGetter.getDateText(notification.getNotificationDate()));
        holder.setNewsID(notification.getNotificationNews());
        DataRepository.getNews(notification.getNotificationNews(), news -> DataRepository.getUser(news.getNewsUserOwner(), userOwner -> {

            if(userOwner.getUserImage() != null){
                holder.notificationUserImage.setImageBitmap(ImageConverter.ByteArrayToBitmap(userOwner.getUserImage()));
            } else {
                holder.notificationUserImage.setImageResource(R.drawable.user);
            }

            if(news.getNewsUserRecognized() != null){
                DataRepository.getUser(news.getNewsUserRecognized(), userRecognized -> {
                    if (userRecognized.getUserID().equals(UserSessionController.getUserSessionID())) {
                        holder.notificationText.setText(NotificationTextGetter.ifUserRecognizedYou(userOwner));
                    } else {
                        holder.notificationText.setText(NotificationTextGetter.ifUserRecognizedSomeoneElse(userOwner, userRecognized));
                    }
                });
            } else {
                if(userOwner.getUserImage() != null){
                    holder.notificationText.setText(NotificationTextGetter.ifUserPostedAnImage(userOwner));
                } else {
                    holder.notificationText.setText(NotificationTextGetter.ifUserPosted(userOwner));
                }
            }
        }));
    }

    public void insertNotification(Notification notification){
        notificationsList.add(0, notification);
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }
}