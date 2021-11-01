package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.globapp.globapp.R;
import com.globapp.globapp.data.remote.LoginUserMongo;
import com.globapp.globapp.data.services.ILoginUser;
import com.google.android.material.textfield.TextInputEditText;

import org.bson.types.ObjectId;

import java.util.concurrent.TimeUnit;

public class FragmentLogin extends Fragment {
    private TextInputEditText    loginUserText;
    private TextInputEditText    loginPasswordText;
    private CardView             loginButton;
    private OnLoginReadyListener onLoginReadyListener;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginPasswordText = requireView().findViewById(R.id.login_password_text);
        loginUserText     = requireView().findViewById(R.id.login_user_text);
        loginButton       = requireView().findViewById(R.id.login_button);

        loginButtonFunction();
    }

    private void loginButtonFunction(){
        loginButton.setOnClickListener(v -> {
            String email    = loginUserText.getText().toString();
            String password = loginPasswordText.getText().toString();
            if(!email.equals("") && !password.equals("")){
                ILoginUser loginUser = new LoginUserMongo(onLoginReadyListener);
                loginUser.login(email, password);
            }
        });
    }

    public interface OnLoginReadyListener {
        void onNewUser(ObjectId userID);
        void onUserCreated();
        void onWrongPassword();
        void onWrongEmail();
        void onError();
    }

    public void addOnLoginReadyListener(OnLoginReadyListener onLoginReadyListener){
        this.onLoginReadyListener = onLoginReadyListener;
    }
}