package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.globapp.globapp.R;
import com.globapp.globapp.data.repositories.UserDataManager;
import com.globapp.globapp.data.remote.CreateProfile;
import com.globapp.globapp.data.remote.UserGetterMongo;
import com.globapp.globapp.data.remote.UserInserterMongo;
import com.globapp.globapp.data.services.ICreateProfile;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.UserNameGetter;
import com.globapp.globapp.view.MainActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import org.bson.types.ObjectId;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

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

    // DATA
    private final ObjectId userID;
    private User     userInstance;

    // Listener
    private OnCreateProfileListener onCreateProfileListener;

    public FragmentCreateProfile(ObjectId userID){
        this.userID = userID;
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_create_profile, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserData();
    }

    private void loadUserData(){
        new UserDataManager(
                new UserInserterMongo(),
                new UserGetterMongo()).getUser(userID, user -> {

            userInstance = user;
            loadComponents();

        });
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MainActivity.RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            assert result != null;
            if(buttonClicked){
                userImage.setImageURI(result.getUri());
            } else {
                coverImage.setImageURI(result.getUri());
            }

        } else if (resultCode == MainActivity.RESULT_OK && requestCode == 9998) {
            CropImage.activity(data.getData()).start(getContext(), this);
        } else if (resultCode == MainActivity.RESULT_OK && requestCode == 9997) {
            CropImage.activity(data.getData()).setAspectRatio(1,1).start(getContext(), this);
        } else {
            Toast.makeText(
                    getContext(),
                    getString(R.string.you_havent_picked_image),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void loadComponents(){
        cancelButton     = requireView().findViewById(R.id.create_profile_cancel_button);
        continueButton   = requireView().findViewById(R.id.create_profile_continue_button);
        userImage        = requireView().findViewById(R.id.create_profile_user_image);
        coverImage       = requireView().findViewById(R.id.create_profile_cover_image);
        userImageButton  = requireView().findViewById(R.id.create_profile_user_image_button);
        coverImageButton = requireView().findViewById(R.id.create_profile_cover_image_button);
        userDescription  = requireView().findViewById(R.id.create_profile_text);
        usernameText     = requireView().findViewById(R.id.create_profile_username);

        setUsernameTextFunction();
        cancelButtonFunction();
        continueButtonFunction();
        userImageButtonFunction();
        coverImageButtonFunction();
    }

    private void setUsernameTextFunction(){
        usernameText.setText(UserNameGetter.getUserName(userInstance));
    }

    private void cancelButtonFunction(){
        cancelButton.setOnClickListener(v -> onCreateProfileListener.onCancel());
    }

    private void coverImageButtonFunction(){
        coverImageButton.setOnClickListener(v -> {
            buttonClicked = false;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 9998);
        });
    }

    private void userImageButtonFunction(){
        userImageButton.setOnClickListener(v -> {
            buttonClicked = true;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 9997);
        });
    }

    private void continueButtonFunction(){
        continueButton.setOnClickListener(v -> {
            int textLength = userDescription.getText().toString().length();
            if(textLength > 20 && textLength < 300){
                continueButton.setClickable(false);
                ICreateProfile iCreateProfile = new CreateProfile(onCreateProfileListener);
                iCreateProfile.create(userID, userDescription.getText().toString());
            } else {
                if(textLength <= 20){
                    Toast.makeText(
                            getContext(),
                            getString(R.string.minimum_length_text),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(
                            getContext(),
                            getString(R.string.maximum_length_text),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public interface OnCreateProfileListener {
        void onCancel();
        void onSuccess();
    }

    public void OnCreateProfileListener(OnCreateProfileListener onCreateProfileListener){
        this.onCreateProfileListener = onCreateProfileListener;
    }
}