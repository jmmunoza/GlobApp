package com.globapp.globapp.data.services;

import org.bson.types.ObjectId;

public interface IGiveStar {
    void giveStar(ObjectId userID, String congratulationsMessage);
}
