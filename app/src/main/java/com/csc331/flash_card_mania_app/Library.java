package com.csc331.flash_card_mania_app;

import java.util.ArrayList;

public class Library {
    private String name;
    private String subject;
    private ArrayList<Card> cards;

    public Library(String name, String subject) {
        this.name = name;
        this.subject = subject;

        cards = new ArrayList<Card>() {{
            add(new Card());
        }};
    }

    public String getName() {
        return name;
    }
    public String getSubject() {
        return subject;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
}
