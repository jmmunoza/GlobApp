package com.globapp.globapp.fragmentlogin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.TimeUnit;

public class FragmentLogin extends Fragment {
    private TextInputEditText loginUserText;
    private TextInputEditText loginPasswordText;
    private CardView loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        loginPasswordText = ((MainActivity)getContext()).findViewById(R.id.login_password_text);
        loginUserText     = ((MainActivity)getContext()).findViewById(R.id.login_user_text);
        loginButton       = ((MainActivity)getContext()).findViewById(R.id.login_button);

        GestureDetector gestureDetector = new GestureDetector(getContext(), new MainActivity.SingleTapConfirm());
        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(gestureDetector.onTouchEvent(event)) {
                    loginButton.setAlpha((float) 1);
                    ((MainActivity)getContext()).getSupportFragmentManager().popBackStackImmediate();
                    ((MainActivity)getContext()).addFragmentRight(new FragmentCreateProfile());
                } else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    loginButton.setAlpha((float) 0.5);
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    loginButton.setAlpha((float) 1);
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                    loginButton.setAlpha((float) 1);
                }
                return true;
            }
        });
    }
}