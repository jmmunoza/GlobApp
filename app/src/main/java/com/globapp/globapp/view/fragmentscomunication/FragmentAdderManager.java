package com.globapp.globapp.view.fragmentscomunication;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.view.MainActivity;
import com.globapp.globapp.view.fragments.FragmentAdminPane;
import com.globapp.globapp.view.fragments.FragmentMain;
import com.globapp.globapp.view.fragments.FragmentMe;
import com.globapp.globapp.view.fragments.FragmentNews;
import com.globapp.globapp.view.fragments.FragmentNotifications;

public class FragmentAdderManager {
    private static MainActivity activityInstance;
    private static FragmentManager fragmentManager;

    private FragmentAdderManager(Context context){
        activityInstance = (MainActivity) context;
        fragmentManager = activityInstance.getSupportFragmentManager();

        if(UserSessionController.getUserSessionID() != null){
            //SetFragmentMain.set();
            SetFragmentLogin.set();
        } else {
            SetFragmentLogin.set();
        }
    }

    public static void init(Context context){
        new FragmentAdderManager(context);
    }

    public static void onBackPressed(){
        int count = fragmentManager.getBackStackEntryCount();
        if (count  == 1) {
            Fragment bottomFragment = fragmentManager.getFragments().get(0);
            if(
                    bottomFragment instanceof FragmentMain          || bottomFragment instanceof FragmentNews ||
                    bottomFragment instanceof FragmentNotifications || bottomFragment instanceof FragmentMe ||
                    bottomFragment instanceof FragmentAdminPane){

                for(Fragment fragment: fragmentManager.getFragments()){
                    if(fragment instanceof FragmentMain){
                        FragmentMain fragmentMain = (FragmentMain) fragment;
                        if(fragmentMain.getPagePosition() == FragmentMain.NEWS){
                            activityInstance.moveTaskToBack(true);
                        } else {
                            fragmentMain.setPagePosition(FragmentMain.NEWS);
                        }
                    }
                }
            } else {
                activityInstance.moveTaskToBack(true);
            }
        } else {
            fragmentManager.popBackStackImmediate();
        }

    }

    public static void deleteAllFragments(){
        while(fragmentManager.getBackStackEntryCount() != 0){
            fragmentManager.popBackStackImmediate();
        }

        for (Fragment fragment : fragmentManager.getFragments()) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    public static void deleteLastFragment(){
        fragmentManager.popBackStackImmediate();
    }

    public static void addFragmentUp(Fragment fragment){
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_up,
                        R.anim.slide_out_down,
                        R.anim.slide_in_down,
                        R.anim.slide_out_up)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void addFragmentLeft(Fragment fragment){
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void addFragmentRight(Fragment fragment){
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }
}
