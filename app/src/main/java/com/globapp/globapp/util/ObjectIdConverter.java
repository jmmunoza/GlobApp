package com.globapp.globapp.util;

import androidx.room.TypeConverter;

import org.bson.types.ObjectId;

public class ObjectIdConverter {
    @TypeConverter
    public static ObjectId toObjectId(String id){
        return id == null ? null: new ObjectId(id);
    }

    @TypeConverter
    public static String fromObjectId(ObjectId id){
        return id == null ? null : id.toString();
    }
}