package com.globapp.globapp.fragmentmain.fragmentuser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class FragmentGiveStar extends Fragment {
    // Data
    User user;

    // UI Components
    CardView  cancelButton;
    CardView  postButton;
    CardView  addImageButton;
    ImageView imageAdded;
    Uri       imageAddedURI;
    TextView  typeTo;
    EditText  postText;

    public FragmentGiveStar(User user){
        this.user = user;
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postponeEnterTransition(1, TimeUnit.MILLISECONDS);
        if(((MainActivity)getContext()).isDarkMode){
            return inflater.inflate(R.layout.fragment_give_star_dark, null);
        } else {
            return inflater.inflate(R.layout.fragment_give_star, null);
        }
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MainActivity.RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageAdded.setImageURI(result.getUri());
            imageAddedURI = result.getUri();
        } else if (resultCode == MainActivity.RESULT_OK && requestCode == 9998) {
            CropImage.activity(data.getData()).start(getContext(), this);
        } else {
            Toast.makeText(getContext(), getString(R.string.you_havent_picked_image),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadComponents();
    }

    @SuppressLint("SetTextI18n")
    void loadComponents(){
        cancelButton   = getView().findViewById(R.id.give_star_cancel_button);
        postButton     = getView().findViewById(R.id.give_star_post_button);
        addImageButton = getView().findViewById(R.id.give_star_add_image_button);
        imageAdded     = getView().findViewById(R.id.give_star_image);
        postText       = getView().findViewById(R.id.give_star_text);
        typeTo         = getView().findViewById(R.id.give_star_type_to);
        imageAddedURI  = null;

        typeTo.setText(getString(R.string.type_to) + " " + user.getMeName() + "...");

        cancelButton.setOnClickListener((View.OnClickListener) v -> {
            ((MainActivity)getContext()).getSupportFragmentManager().popBackStackImmediate();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(postText.getWindowToken(), 0);
        });

        addImageButton.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 9998);
        });

        postButton.setOnClickListener(v -> {
            int textLength = postText.getText().toString().length();
            if(textLength > 20 && textLength < 500){
                ((MainActivity)getContext()).news.add(0,
                        new NewsRecognition(postText.toString(), postText.getText().toString(), imageAddedURI, ((MainActivity)getContext()).me, user)
                );
                ((MainActivity)getContext()).getSupportFragmentManager().popBackStackImmediate();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(postText.getWindowToken(), 0);
                ((MainActivity)getContext()).enableAnimation(R.drawable.celebration_animated_1);
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