package com.globapp.globapp.util;

import androidx.room.TypeConverter;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.bson.types.ObjectId;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ArrayStringConverter {
    @TypeConverter
    public static ArrayList<ObjectId> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> listString = new Gson().fromJson(value, listType);
        return new ArrayList<>(Lists.transform(listString, ObjectId::new));
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<ObjectId> list) {
        ArrayList<String> stringsIDs = new ArrayList<>(Lists.transform(list, ObjectId::toString));
        String json = new Gson().toJson(stringsIDs);
        return json;
    }
}
