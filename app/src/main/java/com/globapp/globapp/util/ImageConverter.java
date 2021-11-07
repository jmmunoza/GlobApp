package com.globapp.globapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.globapp.globapp.GlobAppApplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageConverter {
    public static byte[] UriToByteArray(Uri imageUri){
        try {
            InputStream iStream = GlobAppApplication.getAppContext().getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = iStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();

        } catch (Exception e){
            return null;
        }
    }

    public static Bitmap ByteArrayToBitmap(byte[] imageByteArray){
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
    }
}
