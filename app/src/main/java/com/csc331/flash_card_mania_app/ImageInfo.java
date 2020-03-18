package com.csc331.flash_card_mania_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;

public class ImageInfo {
    private String name;
    private Long size;
    private String type;
    private byte[] bytes;

    public ImageInfo(String name, Long size, String type, byte[] bytes) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }
    public Long getSize() {
        return size;
    }
    public String getType() {
        return type;
    }
    public Bitmap getBitmap() {
        return BitmapFactory.decodeStream(new ByteArrayInputStream(bytes));
    }
}
