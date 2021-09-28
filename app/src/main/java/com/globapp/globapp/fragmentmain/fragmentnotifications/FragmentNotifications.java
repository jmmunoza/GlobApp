package com.globapp.globapp.fragmentmain.fragmentnotifications;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.classes.Notification;
import com.globapp.globapp.classes.Recognition;
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentmain.fragmentnews.NewsListAdapter;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.realm.mongodb.mongo.iterable.MongoCursor;

public class FragmentNotifications extends Fragment {
    RecyclerView             notificationsList;
    NotificationsListAdapter notificationsListAdapter;
    SwipeRefreshLayout       notificationsRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(((MainActivity)getContext()).isDarkMode){
            return inflater.inflate(R.layout.fragment_notifications_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_notifications, null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComponents();
    }

    private void loadComponents(){

        notificationsRefresh = getView().findViewById(R.id.notification_refresh);
        notificationsRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadNotifications();
                    }
                });
            }
        });

        loadNotifications();
    }

    private void loadNotifications(){
        if(notificationsListAdapter != null) notificationsListAdapter.notifyDataSetChanged();
        notificationsList = getView().findViewById(R.id.notifications_list);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        notificationsListAdapter = new NotificationsListAdapter(getContext(), ((MainActivity)getContext()).notifications);
        notificationsList.setLayoutManager(verticalLayoutManager);
        notificationsList.setAdapter(notificationsListAdapter);
        notificationsRefresh.setRefreshing(false);
    }
}