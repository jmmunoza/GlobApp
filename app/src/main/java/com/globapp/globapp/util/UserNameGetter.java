package com.globapp.globapp.util;

import com.globapp.globapp.model.User;

public class UserNameGetter {
    public static String getUserName(User user){
        if(user.getUserSecondName() != null){
            return user.getUserFirstName()  + " " + user.getUserSecondName() + " " + user.getUserLastName();
        } else {
            return user.getUserFirstName() + " " + user.getUserLastName();
        }
    }
}
