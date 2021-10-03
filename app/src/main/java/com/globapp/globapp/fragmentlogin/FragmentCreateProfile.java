package com.globapp.globapp.fragmentlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.classes.User;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class FragmentCreateProfile extends Fragment {
    private CardView        cancelButton;
    private CardView        continueButton;
    private CircleImageView userImage;
    private ImageView       coverImage;
    private TextView        usernameText;
    private ImageButton     userImageButton;
    private ImageButton     coverImageButton;
    private EditText        userDescription;
    private Boolean         buttonClicked = false;
    private Uri             userImageURI;
    private Uri             coverImageURI;

    // DATA
    private Document userData;
    private Document userQuery;

    public FragmentCreateProfile(ObjectId userID){
        this.userQuery = new Document("_id", userID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_create_profile, null);
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MainActivity.RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(buttonClicked){
                userImage.setImageURI(result.getUri());
                userImageURI = result.getUri();
            } else {
                coverImage.setImageURI(result.getUri());
                coverImageURI = result.getUri();
            }
        } else if (resultCode == MainActivity.RESULT_OK && requestCode == 9998) {
            CropImage.activity(data.getData()).start(getContext(), this);
        } else if (resultCode == MainActivity.RESULT_OK && requestCode == 9997) {
            CropImage.activity(data.getData()).setAspectRatio(1,1).start(getContext(), this);
        } else {
            Toast.makeText(getContext(), getString(R.string.you_havent_picked_image),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserData();
    }

    private void loadUserData(){
        ((MainActivity)getContext()).userCollection.findOne(userQuery).getAsync(result -> {
            if(result.isSuccess()){
                userData = result.get();
                loadComponents();
            } else {
                ((MainActivity)getContext()).connectDB();
                ((MainActivity)getContext()).getSupportFragmentManager().popBackStackImmediate();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadComponents(){
        cancelButton     = getView().findViewById(R.id.create_profile_cancel_button);
        continueButton   = getView().findViewById(R.id.create_profile_continue_button);
        userImage        = getView().findViewById(R.id.create_profile_user_image);
        coverImage       = getView().findViewById(R.id.create_profile_cover_image);
        userImageButton  = getView().findViewById(R.id.create_profile_user_image_button);
        coverImageButton = getView().findViewById(R.id.create_profile_cover_image_button);
        userDescription  = getView().findViewById(R.id.create_profile_text);
        usernameText     = getView().findViewById(R.id.create_profile_username);

        if(userData.getString("secondName") != null){
            usernameText.setText(
                    userData.getString("firstName")  + " " +
                    userData.getString("secondName") + " " +
                    userData.getString("lastName")
            );
        } else {
            usernameText.setText(
                    userData.getString("firstName")  + " " +
                    userData.getString("lastName")
            );
        }

        cancelButton.setOnClickListener((View.OnClickListener) v -> {
            ((MainActivity)getContext()).getSupportFragmentManager().popBackStackImmediate();
            ((MainActivity)getContext()).addFragmentRight(((MainActivity)getContext()).fragmentLogin);
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(userDescription.getWindowToken(), 0);
        });

        coverImageButton.setOnClickListener(v -> {
            buttonClicked = false;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 9998);
        });

        userImageButton.setOnClickListener(v -> {
            buttonClicked = true;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 9997);
        });

        continueButton.setOnClickListener(v -> {
            int textLength = userDescription.getText().toString().length();
            if(textLength > 20 && textLength < 300){
                continueButton.setClickable(false);

                Document newUser = userData
                        .append("description", userDescription.getText().toString())
                        .append("created",     true)
                        .append("credits",     0)
                        .append("stars",       0);

                ((MainActivity)getContext()).userCollection.findOneAndUpdate(userQuery, newUser).getAsync(result -> {
                    if(result.isSuccess()){
                        Toast.makeText(getContext(), "PERFIL CREADO EXITOSAMENTE", Toast.LENGTH_LONG).show();

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userDescription.getWindowToken(), 0);
                        ((MainActivity)getContext()).getSupportFragmentManager().popBackStackImmediate();
                        ((MainActivity)getContext()).addFragmentRight(((MainActivity)getContext()).fragmentLogin);
                    } else {
                        Toast.makeText(getContext(), "Hubo un error", Toast.LENGTH_LONG).show();
                        continueButton.setClickable(true);
                    }
                });
            } else {
                if(textLength <= 20){
                    Toast.makeText(getContext(), getString(R.string.minimum_length_text), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.maximum_length_text), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}