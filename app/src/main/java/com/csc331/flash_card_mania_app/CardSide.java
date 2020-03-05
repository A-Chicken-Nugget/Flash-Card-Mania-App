package com.csc331.flash_card_mania_app;

import android.net.Uri;

import java.io.File;
import java.io.InputStream;

public class CardSide {
    private boolean type;
    private String text;
    private InputStream imageStream;

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
    }

    public void setText(String text) {
        this.text = text;
        type = false;
    }
    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
        type = true;
    }
    public void setType(boolean type) {
        this.type = type;
    }
}
