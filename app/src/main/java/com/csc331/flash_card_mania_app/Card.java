package com.csc331.flash_card_mania_app;

import java.util.UUID;

public class Card {
    private UUID id = UUID.randomUUID();
    private CardSide front;
    private CardSide back;
    private String hint;
    private int difficulty;
    private boolean side = true;

    public Card() {
        front = new CardSide("Front text example");
        back = new CardSide("Back text example");
    }
    public Card(String hint, int difficulty) {
        front = new CardSide("Front text example");
        back = new CardSide("Back text example");
        this.hint = hint;
        this.difficulty = difficulty;
    }

    public void flip() {
        side = !side;
    }

    public UUID getID() {
        return id;
    }
    public boolean getCurrentSide() {
        return side;
    }
    public CardSide getFront() {
        return front;
    }
    public CardSide getBack() {
        return back;
    }
    public String getHint() {
        return hint;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public CardSide getShownSide() {
        if (side) {
            return front;
        } else {
            return back;
        }
    }
    public void setHint(String hint) {
        this.hint = hint;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
