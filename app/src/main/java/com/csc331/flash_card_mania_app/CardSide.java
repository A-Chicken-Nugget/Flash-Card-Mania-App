package com.csc331.flash_card_mania_app;

import java.io.File;

public class CardSide {
    private boolean type;
    private String text;
    private String imagePath;

    public CardSide(String text) {
        this.text = text;
    }

    public String getContents() {
        if (!type) {
            return text;
        } else {
            return imagePath;
        }
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }
}
