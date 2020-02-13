package com.csc331.flash_card_mania_app;

public class Card {
    private CardSide front;
    private CardSide back;
    private String hint;
    private int difficulty;
    private boolean side;

    public Card(String hint, int difficulty) {
        front = new CardSide("Front text example");
        back = new CardSide("Back text example");
    }

    public CardSide flip() {
        side = !side;

        if (side) {
            return front;
        } else {
            return back;
        }
    }

    public String getHint() {
        return hint;
    }
    public int getDifficulty() {
        return difficulty;
    }
}
