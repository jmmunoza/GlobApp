package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.globapp.globapp.R;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.view.adapters.NotificationsListAdapter;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FragmentNotifications extends Fragment {

    // UI COMPONENTS
    private RecyclerView             notificationsList;
    private NotificationsListAdapter notificationsListAdapter;
    private SwipeRefreshLayout       notificationsRefresh;
    private ShimmerFrameLayout       notificationsPlaceHolder;

    // LISTENER
    private OnNotificationsListListener onNotificationsListListener;

    @SuppressLint("InflateParams") @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(Preferences.getDarkMode()){
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

    private void notificationListFunction(){
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        notificationsList.setLayoutManager(verticalLayoutManager);
        notificationsListAdapter = new NotificationsListAdapter(getContext(), new ArrayList<>());
        notificationsList.setAdapter(notificationsListAdapter);
    }

    private void refreshFunction(){
        notificationsRefresh.setOnRefreshListener(this::loadNotifications);
    }

    private void loadComponents(){
        notificationsPlaceHolder = requireView().findViewById(R.id.notification_placeholder);
        notificationsRefresh     = requireView().findViewById(R.id.notification_refresh);
        notificationsList        = requireView().findViewById(R.id.notifications_list);

        refreshFunction();
        notificationListFunction();
        loadNotifications();
    }

    private void loadNotifications(){
        notificationsPlaceHolder.setVisibility(View.VISIBLE);
        notificationsList.setVisibility(View.INVISIBLE);
        notificationsPlaceHolder.startShimmer();

        // falta acabar las notis

        notificationsList.setVisibility(View.VISIBLE);
        notificationsPlaceHolder.stopShimmer();
        notificationsPlaceHolder.setVisibility(View.GONE);
        notificationsRefresh.setRefreshing(false);

        /*
        notificationsListAdapter = new NotificationsListAdapter(getContext(), ((MainActivity)getContext()).notifications, onNotificationsListListener);
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

         */
    }

    public interface OnNotificationsListListener {
        void onNewsClicked(ObjectId newsID);
    }

    public void addOnNotificationsListListener(OnNotificationsListListener onNotificationsListListener){
        this.onNotificationsListListener = onNotificationsListListener;
    }
}