package com.globapp.globapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.globapp.globapp.GlobAppApplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

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
            return compress(byteBuffer.toByteArray());

        } catch (Exception e){
            return null;
        }
    }

    public static Bitmap ByteArrayToBitmap(byte[] imageByteArray){
        byte[] imageByteArrayDecompress = decompress(imageByteArray);
        return BitmapFactory.decodeByteArray(imageByteArrayDecompress, 0, imageByteArrayDecompress.length);
    }

    private static byte[] compress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DeflaterOutputStream defl = new DeflaterOutputStream(out);
            defl.write(in);
            defl.flush();
            defl.close();

            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] decompress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream infl = new InflaterOutputStream(out);
            infl.write(in);
            infl.flush();
            infl.close();

            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }
}
