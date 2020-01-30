package com.csc331.flash_card_mania_app;

import java.io.File;

public class CardSide {
    private String text;
    private File image;

    public CardSide(String text) {
        this.text = text;
    }
    public CardSide(File image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
        image = null;
    }
    public void setImage(File image) {
        this.image = image;
        text = null;
    }
}
