package com.example.saudispot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class BitmapImageConvertor {
    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {

//        Bitmap bitmap=((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }
}
