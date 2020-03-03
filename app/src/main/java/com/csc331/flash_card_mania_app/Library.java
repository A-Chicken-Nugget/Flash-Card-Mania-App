package com.csc331.flash_card_mania_app;

import java.util.ArrayList;
import java.util.UUID;

public class Library {
    private UUID id = UUID.randomUUID();
    private String name;
    private String subject;
    private String description;
    private ArrayList<Card> cards;

    public Library(String name, String subject, String description) {
        this.name = name;
        this.subject = subject;
        this.description = description;

        cards = new ArrayList<Card>() {{
            add(new Card("test hint",50));
        }};
    }

    public void addCard(Card card) {
        cards.add(card);
    }
    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public UUID getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSubject() {
        return subject;
    }
    public String getDescription() {
        return description;
    }
    public Card getCardFromID(UUID id) {
        Card returnCard = null;

        for (Card card : cards) {
            if (card.getID().equals(id)) {
                returnCard = card;
            }
        }
        return returnCard;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
