package com.globapp.globapp.fragmentmain.fragmentnotifications;

import android.annotation.SuppressLint;
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
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.classes.Notification;

import java.util.ArrayList;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {

    private ArrayList<Notification> notificationsList;
    private LayoutInflater inflater;
    Context context;

    public NotificationsListAdapter(Context context, ArrayList<Notification> notificationsList){
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        holder.notificationUserImage.setImageURI(notification.getNotificationNews().getNewsUserOwner().getMeImage());

        if(notification.getNotificationNews() instanceof NewsRecognition){
            NewsRecognition newsRecognition = (NewsRecognition) notification.getNotificationNews();
            holder.notificationText.setText(newsRecognition.getNewsUserOwner().getMeName() + " " +
                                            context.getString(R.string.notification_news_recognition_1) + " " +
                                            newsRecognition.getNewsUserRecognized().getMeName() + " " +
                                            context.getString(R.string.notification_news_recognition_2));
        } else {
            News news = notification.getNotificationNews();
            if(news.getNewsImage() != null){
                holder.notificationText.setText(news.getNewsUserOwner().getMeName() + " " + context.getString(R.string.notification_news_image));
            } else {
                holder.notificationText.setText(news.getNewsUserOwner().getMeName() + " " + context.getString(R.string.notification_news_text));
            }
        }
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView         notificationText;
        ImageView        notificationUserImage;
        ConstraintLayout notificationBackground;

        ViewHolder(View itemView) {
            super(itemView);

            notificationText       = (TextView) itemView.findViewById(R.id.notification_item_text);
            notificationUserImage  = (ImageView) itemView.findViewById(R.id.notification_item_user_image);
            notificationBackground = (ConstraintLayout) itemView.findViewById(R.id.notification_item_background);

            notificationBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).addFragmentUp(new FragmentOnNotification(notificationsList.get(getAdapterPosition()).getNotificationNews()));
                }
            });
        }
    }
}
