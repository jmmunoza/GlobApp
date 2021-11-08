package com.globapp.globapp.data.repositories;

import com.globapp.globapp.data.listeners.OnUserLoadedListener;
import com.globapp.globapp.data.services.IUserGetter;
import com.globapp.globapp.data.services.IUserInserter;
import com.globapp.globapp.model.User;

import org.bson.types.ObjectId;

public class UserRepository {
    private final IUserGetter   iUserGetter;
    private final IUserInserter iUserInserter;

    public UserRepository(IUserInserter iUserInserter, IUserGetter iUserGetter){
        this.iUserGetter   = iUserGetter;
        this.iUserInserter = iUserInserter;
    }

    public void insertUser(User user){
        iUserInserter.insert(user);
    }

    public void getUser(ObjectId userID, OnUserLoadedListener onUserLoadedListener){
        iUserGetter.getUser(userID, onUserLoadedListener);
    }
}