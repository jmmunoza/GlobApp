package com.globapp.globapp.view.adapters;

import android.annotation.SuppressLint;
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
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.model.Notification;
import com.globapp.globapp.view.fragments.FragmentNotifications;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*
        Notification notification = notificationsList.get(position);
        holder.notificationDate.setText(notification.getNotificationDate().toString());

        Document newsQuery = new Document("_id", notification.getNotificationNews());
        ((MainActivity)context).newsCollection.findOne(newsQuery).getAsync(newsData -> {
            if(newsData.isSuccess()){
                Document userOwnerQuery = new Document("_id", newsData.get().getObjectId("user_owner_id"));
                ((MainActivity)context).userCollection.findOne(userOwnerQuery).getAsync(userOwnerData -> {
                    if (userOwnerData.isSuccess()) {
                        User userOwner = new User(
                                userOwnerData.get().getObjectId("_id"),
                                userOwnerData.get().getString("firstName"),
                                userOwnerData.get().getString("secondName"),
                                userOwnerData.get().getString("lastName"),
                                userOwnerData.get().getString("description"),
                                null,
                                null,
                                new ArrayList<>(),
                                userOwnerData.get().getInteger("credits", 0),
                                userOwnerData.get().getInteger("stars", 0));

                        if(userOwner.getUserImage() != null){
                            holder.notificationUserImage.setImageURI(userOwner.getUserImage());
                        } else {
                            holder.notificationUserImage.setImageResource(R.drawable.user);
                        }

                        if (newsData.get().getObjectId("user_recognized_id") != null) {
                            Document userRecognizedQuery = new Document("_id", newsData.get().getObjectId("user_recognized_id"));
                            ((MainActivity) context).userCollection.findOne(userRecognizedQuery).getAsync(userRecognizedData -> {
                                if (userOwnerData.isSuccess()) {
                                    User userRecognized = new User(
                                            userRecognizedData.get().getObjectId("_id"),
                                            userRecognizedData.get().getString("firstName"),
                                            userRecognizedData.get().getString("secondName"),
                                            userRecognizedData.get().getString("lastName"),
                                            userRecognizedData.get().getString("description"),
                                            null,
                                            null,
                                            new ArrayList<>(),
                                            userRecognizedData.get().getInteger("credits", 0),
                                            userRecognizedData.get().getInteger("stars", 0));

                                    if (userRecognized.getUserID().equals(((MainActivity) context).me.getUserID())) {
                                        holder.notificationText.setText(userOwner.getUserFirstName() + " " +
                                                context.getString(R.string.notification_news_recognition_you));
                                    } else {
                                        holder.notificationText.setText(userOwner.getUserFirstName() + " " +
                                                context.getString(R.string.notification_news_recognition_1) + " " +
                                                userRecognized.getUserFirstName() + " " +
                                                context.getString(R.string.notification_news_recognition_2));
                                    }
                                    loadedNotifications++;
                                    isDataLoaded();
                                }
                            });
                        } else {
                            if(userOwner.getUserImage() != null){
                                holder.notificationText.setText(
                                        userOwner.getUserFirstName() + " " +
                                        userOwner.getUserLastName()  + " " +
                                        context.getString(R.string.notification_news_image));
                            } else {
                                holder.notificationText.setText(
                                        userOwner.getUserFirstName() + " " +
                                        userOwner.getUserLastName() + " " +
                                        context.getString(R.string.notification_news_text));
                            }

                            loadedNotifications++;
                            isDataLoaded();
                        }
                    }
                });
            }
        });

         */
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

            notificationBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNotificationsListListener.onNewsClicked(notificationsList.get(getAdapterPosition()).getNotificationNews());
                }
            });
        }
    }
}
