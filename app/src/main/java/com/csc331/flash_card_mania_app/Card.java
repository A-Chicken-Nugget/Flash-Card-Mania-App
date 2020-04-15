package com.csc331.flash_card_mania_app;

import java.util.ArrayList;
import java.util.UUID;

public class Card {
    private UUID id = UUID.randomUUID();
    private CardSide front;
    private CardSide back;
    private String hint;
    private Difficulty difficulty;
    private boolean side = true;

     public enum Difficulty {
        EASY("Easy"),
        MEDIUM("Medium"),
        HARD("Hard");

        private String name;

        private Difficulty(String name) {
            this.name = name;
        }

        public static Difficulty difficultyFromName(String name) {
            Difficulty difficulty = null;

            for (Difficulty dif : Difficulty.values()) {
                if (dif.getName().equalsIgnoreCase(name)) {
                    difficulty = dif;
                }
            }
            return difficulty;
        }
        public String getName() {
            return name;
        }
        public static ArrayList<String> listAll(boolean includeSelect) {
            ArrayList<String> list = new ArrayList<>();

            if (includeSelect) {
                list.add("--- SELECT ---");
            }
            for (Difficulty difficulty : Difficulty.values()) {
                list.add(difficulty.getName());
            }
            return list;
        }
    }

    public Card() {
        front = new CardSide("Front text example");
        back = new CardSide("Back text example");
    }
    public Card(String hint, Difficulty difficulty) {
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
    public Difficulty getDifficulty() {
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
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
