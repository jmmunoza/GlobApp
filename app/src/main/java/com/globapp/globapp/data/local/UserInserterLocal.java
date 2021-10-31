package com.globapp.globapp.data.local;

import com.globapp.globapp.data.local.dao.UserDAO;
import com.globapp.globapp.data.services.IUserInserter;
import com.globapp.globapp.model.User;

public class UserInserterLocal implements IUserInserter {
    private final UserDAO userDAO;

    public UserInserterLocal(){
        userDAO = LocalDB.getInstance().userDAO();
    }

    @Override
    public void insert(User user) {
        userDAO.insert(user);
    }

}
