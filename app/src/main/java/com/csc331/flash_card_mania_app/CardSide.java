package com.csc331.flash_card_mania_app;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.InputStream;

public class CardSide {
    private boolean type;
    private String text;
    private ImageInfo imageInfo;

    public CardSide(String text) {
        this.text = text;
    }

    public boolean getType() {
        return type;
    }
    public String getText() {
        return text;
    }
    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }
    public void setType(boolean type) {
        this.type = type;
    }
}
