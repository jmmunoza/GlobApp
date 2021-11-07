package com.globapp.globapp.data.services;

import android.net.Uri;

import org.bson.types.ObjectId;

public interface ICreateProfile {
    void create(ObjectId userID, String userDescription, Uri userImageUri, Uri coverImageUri);
}
