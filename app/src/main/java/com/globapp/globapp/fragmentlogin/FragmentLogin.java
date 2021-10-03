package com.globapp.globapp.fragmentlogin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.Admin;
import com.globapp.globapp.classes.Recognition;
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.google.android.material.textfield.TextInputEditText;

import org.bson.Document;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class FragmentLogin extends Fragment {
    private TextInputEditText loginUserText;
    private TextInputEditText loginPasswordText;
    private CardView          loginButton;

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

        loginButton.setOnClickListener(v -> {
            if(!loginPasswordText.getText().toString().equals("") && !loginUserText.getText().toString().equals("")){
                if(((MainActivity)getContext()).databaseConnection != null){

                    Document userQuery = new Document()
                            .append("email", loginUserText.getText().toString());

                    ((MainActivity)getContext()).userCollection.findOne(userQuery).getAsync(result -> {
                        if (result.isSuccess()){
                            if(result.get() != null){
                                if(result.get().getString("password").equals(loginPasswordText.getText().toString())){
                                    if(result.get().getString("description") != null){
                                        if(result.get().getBoolean("admin")){
                                            ((MainActivity)getContext()).me = new Admin(
                                                    result.get().getObjectId("_id"),
                                                    result.get().getString("firstName"),
                                                    result.get().getString("secondName"),
                                                    result.get().getString("lastName"),
                                                    result.get().getString("description"),
                                                    null,
                                                    null,
                                                    new ArrayList<>(),
                                                    result.get().getInteger("credits",0),
                                                    result.get().getInteger("stars",0));
                                        } else {
                                            ((MainActivity)getContext()).me = new User(
                                                    result.get().getObjectId("_id"),
                                                    result.get().getString("firstName"),
                                                    result.get().getString("secondName"),
                                                    result.get().getString("lastName"),
                                                    result.get().getString("description"),
                                                    null,
                                                    null,
                                                    new ArrayList<Recognition>(),
                                                    result.get().getInteger("credits",0),
                                                    result.get().getInteger("stars",0));
                                        }

                                        ((MainActivity)getContext()).getSupportFragmentManager().popBackStackImmediate();
                                        ((MainActivity)getContext()).addFragmentRight(((MainActivity)getContext()).fragmentMain);
                                    } else {
                                        ((MainActivity)getContext()).getSupportFragmentManager().popBackStackImmediate();
                                        ((MainActivity)getContext()).addFragmentRight(
                                                new FragmentCreateProfile(result.get().getObjectId("_id"))
                                        );
                                    }
                                } else {
                                    Toast.makeText(getContext(), "CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "NO ESTAS REGISTRADO EN LA EMPRESA", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    ((MainActivity)getContext()).connectDB();
                }
            }
        });
    }
}