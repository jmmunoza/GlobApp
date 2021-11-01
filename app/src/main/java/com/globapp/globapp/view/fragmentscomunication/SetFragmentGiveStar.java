package com.globapp.globapp.view.fragmentscomunication;

import com.globapp.globapp.view.fragments.FragmentGiveStar;

import org.bson.types.ObjectId;

public class SetFragmentGiveStar {
    public static void set(ObjectId userID){
        FragmentGiveStar fragmentGiveStar = new FragmentGiveStar(userID);
        fragmentGiveStar.addOnGiveStarListener(new FragmentGiveStar.OnGiveStarListener() {
            @Override
            public void onSuccess() {
                FragmentAdderManager.deleteLastFragment();
                //enableAnimation(R.drawable.celebration_animated_1);
            }

            @Override
            public void onCancel() {
                FragmentAdderManager.deleteLastFragment();
            }

            @Override
            public void onError() {
                //Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
            }
        });
        FragmentAdderManager.addFragmentUp(fragmentGiveStar);
    }
}
