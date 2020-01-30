package com.csc331.flash_card_mania_app;

public class Card {
    private CardSide front;
    private CardSide back;
    private String hint;
    private int difficulty;

    public Card() {
        front = new CardSide("Front text example");
        back = new CardSide("Back text example");
    }

}
