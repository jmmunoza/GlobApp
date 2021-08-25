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
    private FragmentMe            fragmentMe;
    private FragmentNews          fragmentNews;
    private FragmentSearch        fragmentSearch;
    private FragmentSettings      fragmentSettings;
    private FragmentNotifications fragmentNotifications;

    // Consonants
    private static final int NEWS = 0;
    private static final int NOTIFICATIONS = 1;
    private static final int ME = 2;

    // Components
    private ViewPager2           mainViewPager;
    private ViewPagerAdapter     viewPagerAdapter;
    private BottomNavigationView bottomNavigationView;
    private ImageButton          search_button;
    private ImageButton          settings_button;

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
        fragmentMe            = new FragmentMe(((MainActivity)getContext()).me);
        fragmentNews          = new FragmentNews();
        fragmentNotifications = new FragmentNotifications();
        fragmentSearch        = new FragmentSearch();
        fragmentSettings      = new FragmentSettings();

        settings_button = ((MainActivity)getContext()).findViewById(R.id.settings_button);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).addFragment(fragmentSettings);
            }
        });

        search_button   = ((MainActivity)getContext()).findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).addFragment(fragmentSearch);
            }
        });

        bottomNavigationView = ((MainActivity)getContext()).findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                }
                return true;
            }
        });
        viewPagerAdapter = new ViewPagerAdapter((MainActivity)getContext());

        mainViewPager = getView().findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(viewPagerAdapter);
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
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}