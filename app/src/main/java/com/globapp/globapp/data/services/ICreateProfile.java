package com.globapp.globapp.data.services;

import org.bson.types.ObjectId;

public interface ICreateProfile {
    void create(ObjectId userID, String userDescription);
}
