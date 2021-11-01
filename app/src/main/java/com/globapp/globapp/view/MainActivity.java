package com.globapp.globapp.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.services.IUserSessionController;
import com.globapp.globapp.view.fragments.FragmentCreateProfile;
import com.globapp.globapp.view.fragments.FragmentEditProfile;
import com.globapp.globapp.view.fragments.FragmentGiveStar;
import com.globapp.globapp.view.fragments.FragmentLogin;
import com.globapp.globapp.view.fragments.FragmentMain;
import com.globapp.globapp.view.fragments.FragmentMe;
import com.globapp.globapp.view.fragments.FragmentOnNotification;
import com.globapp.globapp.view.fragments.FragmentSearch;
import com.globapp.globapp.view.fragments.FragmentSettings;
import com.globapp.globapp.view.fragments.FragmentUser;

import org.bson.types.ObjectId;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    private GifImageView animationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationContainer = findViewById(R.id.animation_container);
        setFragmentLogin();
    }

    private void setFragmentLogin(){
        FragmentLogin fragmentLogin = new FragmentLogin();
        fragmentLogin.addOnLoginReadyListener(new FragmentLogin.OnLoginReadyListener() {
            @Override
            public void onNewUser(ObjectId userID) {
                setFragmentCreateProfile(userID);
            }

            @Override
            public void onUserCreated() {
                getSupportFragmentManager().popBackStackImmediate();
                setFragmentMain();
            }

            @Override
            public void onWrongPassword() {
                Toast.makeText(MainActivity.this,"Mala contra", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onWrongEmail() {
                Toast.makeText(MainActivity.this, "Mal correo", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Errorrrrr", Toast.LENGTH_LONG).show();
            }
        });
        addFragmentRight(fragmentLogin);
    }

    private void setFragmentCreateProfile(ObjectId userID){
        FragmentCreateProfile fragmentCreateProfile = new FragmentCreateProfile(userID);
        fragmentCreateProfile.OnCreateProfileListener(new FragmentCreateProfile.OnCreateProfileListener() {
            @Override
            public void onCancel() {
                getSupportFragmentManager().popBackStackImmediate();
            }

            @Override
            public void onSuccess() {
                getSupportFragmentManager().popBackStackImmediate();
                getSupportFragmentManager().popBackStackImmediate();
                setFragmentLogin();
            }
        });
        addFragmentRight(fragmentCreateProfile);
    }

    private void setFragmentMain(){
        FragmentMain fragmentMain = new FragmentMain();
        fragmentMain.addOnMainListener(new FragmentMain.OnMainListener() {
            @Override
            public void search() {
                setFragmentSearch();
            }

            @Override
            public void settings() {
                setFragmentSettings();
            }

            @Override
            public void onNewsClicked(ObjectId newsID) {
                setFragmentOnNotification(newsID);
            }

            @Override
            public void onUserImageClicked(ObjectId userID) {
                setFragmentUser(userID);
            }
        });
        addFragmentRight(fragmentMain);
    }

    private void setFragmentSettings(){
        FragmentSettings fragmentSettings = new FragmentSettings();
        fragmentSettings.addOnSettingsListener(new FragmentSettings.OnSettingsListener() {
            @Override
            public void editProfile() {
                setFragmentEditProfile();
            }

            @Override
            public void logout() {
                while(getSupportFragmentManager().getBackStackEntryCount() != 0){
                    getSupportFragmentManager().popBackStackImmediate();
                }

                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }

                IUserSessionController iUserSessionController = new UserSessionController();
                iUserSessionController.setUserSessionID(null);
                iUserSessionController.setUserAdmin(false);
                setFragmentLogin();
            }
        });
        addFragmentLeft(fragmentSettings);
    }

    private void setFragmentSearch(){
        addFragmentRight(new FragmentSearch());
    }

    private void setFragmentEditProfile(){
        FragmentEditProfile fragmentEditProfile = new FragmentEditProfile();
        fragmentEditProfile.addOnEditProfileListener(new FragmentEditProfile.OnEditProfileListener() {
            @Override
            public void onSuccess() {
                getSupportFragmentManager().popBackStackImmediate();
            }

            @Override
            public void onCancel() {
                getSupportFragmentManager().popBackStackImmediate();
            }
        });
        addFragmentLeft(new FragmentEditProfile());
    }

    private void setFragmentGiveStar(ObjectId userID){
        FragmentGiveStar fragmentGiveStar = new FragmentGiveStar(userID);
        fragmentGiveStar.addOnGiveStarListener(new FragmentGiveStar.OnGiveStarListener() {
            @Override
            public void onSuccess() {
                getSupportFragmentManager().popBackStackImmediate();
                enableAnimation(R.drawable.celebration_animated_1);
            }

            @Override
            public void onCancel() {
                getSupportFragmentManager().popBackStackImmediate();
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
            }
        });
        addFragmentUp(fragmentGiveStar);
    }

    private void setFragmentOnNotification(ObjectId newsID){
        FragmentOnNotification fragmentOnNotification = new FragmentOnNotification(newsID);
        fragmentOnNotification.addOnNotificationListener(this::setFragmentUser);
        addFragmentRight(fragmentOnNotification);
    }

    private void setFragmentUser(ObjectId userID){
        IUserSessionController iUserSessionController = new UserSessionController();
        String userSessionID = iUserSessionController.getUserSessionID();

        if(userID.toString().equals(userSessionID)){
            FragmentMe fragmentMe = new FragmentMe();
            addFragmentRight(fragmentMe);
        } else {
            FragmentUser fragmentUser = new FragmentUser(userID);
            fragmentUser.addOnUserListener(this::setFragmentGiveStar);
            addFragmentRight(fragmentUser);
        }
    }


    public void enableAnimation(int animationID) {
        animationContainer.setImageResource(animationID);
        ((GifDrawable) animationContainer.getDrawable()).start();
        ((GifDrawable) animationContainer.getDrawable()).addAnimationListener(i -> {
            ((GifDrawable) animationContainer.getDrawable()).stop();
            animationContainer.setImageBitmap(null);
        });
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        System.out.println(getSupportFragmentManager().getFragments());

        /*
        if (count  == 1) {
            Fragment bottomFragment = getSupportFragmentManager().getFragments().get(0);
            if(bottomFragment instanceof FragmentMain          || bottomFragment instanceof FragmentNews ||
               bottomFragment instanceof FragmentNotifications || bottomFragment instanceof FragmentMe   ||
               bottomFragment instanceof FragmentAdminPane){
                if(fragmentMain.mainViewPager.getCurrentItem() == FragmentMain.NEWS){
                    moveTaskToBack(true);
                } else {
                    fragmentMain.mainViewPager.setCurrentItem(FragmentMain.NEWS);
                }
            } else {
                moveTaskToBack(true);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }

         */
    }


    public void addFragmentUp(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
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

    public void addFragmentLeft(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
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

    public void addFragmentRight(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
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