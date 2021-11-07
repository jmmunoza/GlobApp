package com.globapp.globapp.data.services;

import android.net.Uri;

public interface IEditUser {
    void edit(String newDescription, Uri userImageUri, Uri coverImageUri);
}
