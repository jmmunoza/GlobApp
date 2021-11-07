package com.globapp.globapp.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.remote.GiveStarMongo;
import com.globapp.globapp.data.services.IGiveStar;
import com.globapp.globapp.model.User;
import com.globapp.globapp.util.KeyboardManager;
import com.globapp.globapp.view.MainActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import org.bson.types.ObjectId;

import java.util.concurrent.TimeUnit;

public class FragmentGiveStar extends Fragment {
    // Data
    private User     user;
    private final ObjectId userID;

    // UI Components
    private CardView  cancelButton;
    private CardView  postButton;
    private CardView  addImageButton;
    private ImageView imageAdded;
    private Uri       imageAddedURI;
    private TextView  typeTo;
    private EditText  postText;

    // Listener
    private OnGiveStarListener onGiveStarListener;

    public FragmentGiveStar(ObjectId userID){
        this.userID = userID;
    }

    @SuppressLint("InflateParams") @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        return inflater.inflate(R.layout.fragment_give_star, null);
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MainActivity.RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            assert result != null;
            imageAdded.setImageURI(result.getUri());
            imageAddedURI = result.getUri();
        } else if (resultCode == MainActivity.RESULT_OK && requestCode == 9998) {
            assert data != null;
            CropImage.activity(data.getData()).start(requireContext(), this);
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
        DataRepository.getUser(userID, user -> {

            this.user = user;
            loadComponents();
        });
    }

    private void cancelButtonFunction(){
        cancelButton.setOnClickListener(v -> onGiveStarListener.onCancel());
    }

    private void addImageButtonFunction(){
        addImageButton.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 9998);
        });
    }

    private void postButtonFunction(){
        postButton.setOnClickListener(v -> {
            int textLength = postText.getText().toString().length();
            if(textLength > 20 && textLength < 500){
                postButton.setEnabled(false);
                KeyboardManager.hide(requireContext(), postText.getWindowToken());
                IGiveStar iGiveStar = new GiveStarMongo(onGiveStarListener);
                iGiveStar.giveStar(userID, postText.getText().toString(), imageAddedURI);

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

    private void typeToFunction(){
        String typeToText = getString(R.string.type_to) + " " + user.getUserFirstName() + "...";
        typeTo.setText(typeToText);
    }

    void loadComponents(){
        cancelButton   = requireView().findViewById(R.id.give_star_cancel_button);
        postButton     = requireView().findViewById(R.id.give_star_post_button);
        addImageButton = requireView().findViewById(R.id.give_star_add_image_button);
        imageAdded     = requireView().findViewById(R.id.give_star_image);
        postText       = requireView().findViewById(R.id.give_star_text);
        typeTo         = requireView().findViewById(R.id.give_star_type_to);
        imageAddedURI  = null;

        addImageButtonFunction();
        cancelButtonFunction();
        postButtonFunction();
        typeToFunction();
    }

    public interface OnGiveStarListener {
        void onSuccess();
        void onCancel();
        void onError();
    }

    public void addOnGiveStarListener(OnGiveStarListener onGiveStarListener){
        this.onGiveStarListener = onGiveStarListener;
    }
}
