package com.globapp.globapp.data.services;

import android.net.Uri;

import org.bson.types.ObjectId;

public interface IGiveStar {
    void giveStar(ObjectId userID, String congratulationsMessage, Uri imageUri);
}
