package com.globapp.globapp.data.local;

import com.globapp.globapp.data.listeners.OnUserLoadedListener;
import com.globapp.globapp.data.local.dao.UserDAO;
import com.globapp.globapp.data.services.IUserGetter;
import com.globapp.globapp.model.User;

import org.bson.types.ObjectId;

public class UserGetterLocal implements IUserGetter {
    private final UserDAO userDAO;

    public UserGetterLocal(){
        userDAO = LocalDB.getInstance().userDAO();
    }

    @Override
    public void getUser(ObjectId userID, OnUserLoadedListener onUserLoadedListener) {
        User user = userDAO.getUser(userID.toString());
        onUserLoadedListener.onUserLoaded(user);
    }
}
