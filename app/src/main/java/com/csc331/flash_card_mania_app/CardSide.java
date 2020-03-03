package com.csc331.flash_card_mania_app;

import android.net.Uri;

import java.io.File;

public class CardSide {
    private boolean type;
    private String text;
    private String imagePath;
    private Uri uri;

    public CardSide(String text) {
        this.text = text;
    }

    public boolean getType() {
        return type;
    }
    public String getContents() {
        if (!type) {
            return text;
        } else {
            return imagePath;
        }
    }
    public Uri getUri() {
        return uri;
    }

    public void setText(String text) {
        this.text = text;
        type = false;
    }
    public void setImage(String imagePath) {
        this.imagePath = imagePath;
        type = true;
    }
    public void setType(boolean type) {
        this.type = type;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
