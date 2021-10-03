package com.globapp.globapp.fragmentmain;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;

import com.globapp.globapp.classes.Admin;
import com.globapp.globapp.fragmentmain.fragmentadminpane.FragmentAdminPane;
import com.globapp.globapp.fragmentmain.fragmentme.FragmentMe;
import com.globapp.globapp.fragmentmain.fragmentnews.FragmentNews;
import com.globapp.globapp.fragmentmain.fragmentnotifications.FragmentNotifications;
import com.globapp.globapp.fragmentmain.fragmentsearch.FragmentSearch;
import com.globapp.globapp.fragmentmain.fragmentsettings.FragmentSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.concurrent.TimeUnit;


public class FragmentMain extends Fragment {
    // Fragments
    public FragmentMe            fragmentMe;
    public FragmentNews          fragmentNews;
    public FragmentSearch        fragmentSearch;
    public FragmentSettings      fragmentSettings;
    public FragmentNotifications fragmentNotifications;
    public FragmentAdminPane     fragmentAdminPane;

    // Consonants
    public static final int NEWS = 0;
    public static final int NOTIFICATIONS = 1;
    public static final int ME = 2;
    public static final int ADMIN_PANE = 3;


    // Components
    public ViewPager2           mainViewPager;
    public ViewPagerAdapter     viewPagerAdapter;
    public BottomNavigationView bottomNavigationView;
    public ImageButton          search_button;
    public ImageButton          settings_button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(((MainActivity)getContext()).isDarkMode){
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

    private void loadComponents(){
        fragmentMe            = new FragmentMe();
        fragmentNews          = new FragmentNews();
        fragmentNotifications = new FragmentNotifications();
        fragmentSearch        = new FragmentSearch();
        fragmentSettings      = new FragmentSettings();
        fragmentAdminPane     = new FragmentAdminPane();


        settings_button = ((MainActivity)getContext()).findViewById(R.id.settings_button);
        settings_button.setOnClickListener(v -> ((MainActivity)getContext()).addFragmentRight(fragmentSettings));

        search_button   = ((MainActivity)getContext()).findViewById(R.id.search_button);
        search_button.setOnClickListener(v -> ((MainActivity)getContext()).addFragmentLeft(fragmentSearch));

        bottomNavigationView = ((MainActivity)getContext()).findViewById(R.id.bottom_navigation_bar);
        if(!(((MainActivity)getContext()).me instanceof Admin))
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

        viewPagerAdapter = new ViewPagerAdapter((MainActivity)getContext());
        mainViewPager = getView().findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(viewPagerAdapter);
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

    public class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
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
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}