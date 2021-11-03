package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.local.UserSessionController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.bson.types.ObjectId;

import java.util.concurrent.TimeUnit;


public class FragmentMain extends Fragment {
    // Fragments
    private FragmentMe            fragmentMe;
    private FragmentNews          fragmentNews;
    private FragmentNotifications fragmentNotifications;
    private FragmentAdminPane     fragmentAdminPane;

    // Consonants
    public static final int NEWS          = 0;
    public static final int NOTIFICATIONS = 1;
    public static final int ME            = 2;
    public static final int ADMIN_PANE    = 3;

    // Components
    private ViewPager2           mainViewPager;
    private BottomNavigationView bottomNavigationView;
    private ImageButton          searchButton;
    private ImageButton          settingsButton;

    // Listener
    private OnMainListener onMainListener;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(Preferences.getDarkMode()){
            return inflater.inflate(R.layout.fragment_activity_main_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_activity_main, null);
        }
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        loadComponents();
    }

    private void settingButtonFunction(){
        settingsButton.setOnClickListener(v -> onMainListener.settings());
    }

    private void searchButtonFunction(){
        searchButton.setOnClickListener(v -> onMainListener.search());
    }

    private void mainViewPagerFunction(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(requireActivity());
        mainViewPager.setAdapter(viewPagerAdapter);
       // mainViewPager.setOffscreenPageLimit(4);
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case NEWS:
                        bottomNavigationView.setSelectedItemId(R.id.action_news);
                        break;
                    case NOTIFICATIONS:
                        bottomNavigationView.setSelectedItemId(R.id.action_notifications);
                        break;
                    case ME:
                        bottomNavigationView.setSelectedItemId(R.id.action_me);
                        break;
                    case ADMIN_PANE:
                        bottomNavigationView.setSelectedItemId(R.id.action_admin_pane);
                        break;
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void bottomNavigationViewFunction(){
        if(!UserSessionController.isUserAdmin())
            bottomNavigationView.getMenu().removeItem(R.id.action_admin_pane);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_news:
                    mainViewPager.setCurrentItem(NEWS);
                    break;
                case R.id.action_notifications:
                    mainViewPager.setCurrentItem(NOTIFICATIONS);
                    break;
                case R.id.action_me:
                    mainViewPager.setCurrentItem(ME);
                    break;
                case R.id.action_admin_pane:
                    mainViewPager.setCurrentItem(ADMIN_PANE);
                    break;
            }
            return true;
        });
    }

    public int getPagePosition(){
        return mainViewPager.getCurrentItem();
    }

    public void setPagePosition(int position){
        mainViewPager.setCurrentItem(position);
    }

    public void setFragmentNewsListOnTop(){
        fragmentNews.setNewsListOnTop();
    }

    public void setFragmentNotificationsListOnTop(){
        fragmentNotifications.setNotificationsListOnTop();
    }

    public int getFragmentNewsListScrollPosition(){
        return fragmentNews.getNewsListPosition();
    }

    private void loadComponents(){
        fragmentMe            = new FragmentMe();
        fragmentNews          = new FragmentNews();
        fragmentNotifications = new FragmentNotifications();
        fragmentAdminPane     = new FragmentAdminPane();

        fragmentNews.addOnUserImageClickedListener(userID -> onMainListener.onUserImageClicked(userID));
        fragmentNotifications.addOnNotificationsListListener(newsID -> onMainListener.onNewsClicked(newsID));

        settingsButton        = requireView().findViewById(R.id.settings_button);
        searchButton          = requireView().findViewById(R.id.search_button);
        bottomNavigationView  = requireView().findViewById(R.id.bottom_navigation_bar);
        mainViewPager         = requireView().findViewById(R.id.main_view_pager);

        searchButtonFunction();
        settingButtonFunction();
        mainViewPagerFunction();
        bottomNavigationViewFunction();
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case NEWS:
                    return  fragmentNews;
                case NOTIFICATIONS:
                    return  fragmentNotifications;
                case ME:
                    return  fragmentMe;
                case ADMIN_PANE:
                    return  fragmentAdminPane;
            }

            return null;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    public interface OnMainListener {
        void search();
        void settings();
        void onNewsClicked(ObjectId newsID);
        void onUserImageClicked(ObjectId userID);
    }

    public void addOnMainListener(OnMainListener onMainListener){
        this.onMainListener = onMainListener;
    }
}