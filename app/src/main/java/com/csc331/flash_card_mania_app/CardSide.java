package com.csc331.flash_card_mania_app;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.InputStream;

public class CardSide {
    private boolean type;
    private String text;
    private byte[] imageBytes;

    public CardSide(String text) {
        this.text = text;
    }

    public boolean getType() {
        return type;
    }
    public String getText() {
        return text;
    }
    public InputStream getImageStream() {
        return imageStream;
    public byte[] getImageBtyes() {
        return imageBytes;
    }

    public void setText(String text) {
        this.text = text;
        type = false;
    }
    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }
    public void setImageBytes(byte[] bytes) {
        imageBytes = bytes;
        type = true;
    }
    public void setType(boolean type) {
        this.type = type;
    }
}
