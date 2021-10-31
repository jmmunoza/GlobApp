package com.globapp.globapp.data.services;

import com.globapp.globapp.model.User;

public interface IUserSessionController {
    void    setUserSessionID(String userSessionID);
    String  getUserSessionID();
    void    setUserAdmin(boolean isAdmin);
    boolean isUserAdmin();
}
