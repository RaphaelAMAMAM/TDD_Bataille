package org.example;

import java.util.List;

import static java.lang.Math.ceil;

public class CanDraw {
    private int getRandomEmplacementDeck(List<Card> deck) {
        return (int) ceil(Math.random() * (deck.size() - 1));
    }

    public final Card getRandomCard(List<Card> deck) {
        int emplacement = getRandomEmplacementDeck(deck);
        return deck.get(emplacement);
    }
}
