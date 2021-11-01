package com.globapp.globapp.data;

import com.globapp.globapp.data.local.LocalDB;
import com.globapp.globapp.data.remote.MongoDB;

public class DataRepository {
    public static void init(){
        MongoDB.initDB();
        LocalDB.initDB();
    }
}
