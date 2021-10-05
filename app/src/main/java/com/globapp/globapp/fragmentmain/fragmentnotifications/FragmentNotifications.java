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

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.realm.mongodb.mongo.iterable.MongoCursor;

public class FragmentNotifications extends Fragment {
    RecyclerView             notificationsList;
    NotificationsListAdapter notificationsListAdapter;
    SwipeRefreshLayout       notificationsRefresh;
    ShimmerFrameLayout       notificationsPlaceHolder;

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
        notificationsPlaceHolder = getView().findViewById(R.id.notification_placeholder);
        notificationsRefresh     = getView().findViewById(R.id.notification_refresh);
        notificationsList        = getView().findViewById(R.id.notifications_list);


        notificationsRefresh.setOnRefreshListener(() -> ((MainActivity)getContext()).runOnUiThread(this::loadNotifications));

        loadNotifications();
    }

    private void loadNotifications(){
        notificationsPlaceHolder.setVisibility(View.VISIBLE);
        notificationsList.setVisibility(View.INVISIBLE);
        notificationsPlaceHolder.startShimmer();

        if(notificationsListAdapter != null) notificationsListAdapter.notifyDataSetChanged();

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        notificationsList.setLayoutManager(verticalLayoutManager);
        notificationsListAdapter = new NotificationsListAdapter(getContext(), ((MainActivity)getContext()).notifications);
        notificationsListAdapter.addDataLoadedListener(new NotificationsListAdapter.DataLoadedListener() {
            @Override
            public void onDataLoaded() {
                notificationsList.setVisibility(View.VISIBLE);
                notificationsPlaceHolder.stopShimmer();
                notificationsPlaceHolder.setVisibility(View.GONE);
                notificationsRefresh.setRefreshing(false);
            }
        });
        notificationsList.setAdapter(notificationsListAdapter);
    }
}