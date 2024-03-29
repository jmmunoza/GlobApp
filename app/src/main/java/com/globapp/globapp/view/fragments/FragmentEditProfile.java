package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.local.UserSessionController;
import com.globapp.globapp.data.remote.EditUserMongo;
import com.globapp.globapp.data.services.IEditUser;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.ImageConverter;
import com.globapp.globapp.util.KeyboardManager;
import com.globapp.globapp.util.UserNameGetter;
import com.globapp.globapp.view.MainActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import org.bson.types.ObjectId;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
public class FragmentEditProfile extends Fragment {

    private CardView        cancelButton;
    private CardView        continueButton;
    private CircleImageView userImage;
    private ImageView       coverImage;
    private TextView        username;
    private ImageButton     userImageButton;
    private ImageButton     coverImageButton;
    private EditText        userDescription;
    private Boolean         buttonClicked = false;
    private Uri             userImageURI;
    private Uri             coverImageURI;

    // Listener
    private OnEditProfileListener onEditProfileListener;

    // User Me Instance
    private User me;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_create_profile, null);
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MainActivity.RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (buttonClicked) {
                userImage.setImageURI(result.getUri());
                userImageURI = result.getUri();
            } else {
                coverImage.setImageURI(result.getUri());
                coverImageURI = result.getUri();
            }
        } else if (resultCode == MainActivity.RESULT_OK && requestCode == 9998) {
            CropImage.activity(data.getData()).start(requireContext(), this);
        } else if (resultCode == MainActivity.RESULT_OK && requestCode == 9997) {
            CropImage.activity(data.getData()).setAspectRatio(1, 1).start(requireContext(), this);
        } else {
            Toast.makeText(getContext(), getString(R.string.you_havent_picked_image), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserData();
    }

    private void loadUserData(){
        ObjectId userSessionID = UserSessionController.getUserSessionID();
        DataRepository.getUser(userSessionID, user -> {
            me = user;
            loadComponents();
        });
    }

    private void descriptionFunction(){
        userDescription.setText(me.getUserDescription());
    }

    private void userImageFunction(){
        if(me.getUserImage() != null){
            userImage.setImageBitmap(ImageConverter.ByteArrayToBitmap(me.getUserImage()));
        }
    }

    private void  coverImageFunction(){
        if(me.getUserCoverImage() != null){
            coverImage.setImageBitmap(ImageConverter.ByteArrayToBitmap(me.getUserCoverImage()));
        }
    }

    private void cancelButtonFunction(){
        cancelButton.setOnClickListener(v -> onEditProfileListener.onCancel());
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
            if (textLength > 20 && textLength < 300) {

                KeyboardManager.hide(getContext(), userDescription.getWindowToken());
                IEditUser iEditUser = new EditUserMongo(onEditProfileListener);
                iEditUser.edit(userDescription.getText().toString(), userImageURI, coverImageURI);

            } else {
                if (textLength <= 20) {
                    Toast.makeText(getContext(), getString(R.string.minimum_length_text), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.maximum_length_text), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void usernameFunction(){
        username.setText(UserNameGetter.getUserName(me));
    }

    private void loadComponents() {
        cancelButton     = requireView().findViewById(R.id.create_profile_cancel_button);
        continueButton   = requireView().findViewById(R.id.create_profile_continue_button);
        userImage        = requireView().findViewById(R.id.create_profile_user_image);
        coverImage       = requireView().findViewById(R.id.create_profile_cover_image);
        userImageButton  = requireView().findViewById(R.id.create_profile_user_image_button);
        coverImageButton = requireView().findViewById(R.id.create_profile_cover_image_button);
        userDescription  = requireView().findViewById(R.id.create_profile_text);
        username         = requireView().findViewById(R.id.create_profile_username);

        cancelButtonFunction();
        continueButtonFunction();
        coverImageButtonFunction();
        coverImageFunction();
        descriptionFunction();
        userImageButtonFunction();
        userImageFunction();
        usernameFunction();

    }

    public interface OnEditProfileListener {
        void onSuccess();
        void onCancel();
    }

    public void addOnEditProfileListener(OnEditProfileListener onEditProfileListener){
        this.onEditProfileListener = onEditProfileListener;
    }
}