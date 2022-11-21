package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardGameWith52Cards extends CanDraw {

    private List<Card> jeuDeCard;

    public CardGameWith52Cards() {
        jeuDeCard = new ArrayList<>(createPaquet());
    }

    public List<Card> getJeuDeCarte() {
        return jeuDeCard;
    }

    public List<Card> createPaquet(){
        List<Card> jeuDeCard = new ArrayList<>();
        Set<String> colors = getColors();

        for (String color : colors){
            jeuDeCard.add(new Card("as de " + color, 14));
            for (int i = 2 ; i <= 13 ; i++){
                jeuDeCard.add(new Card(getStringOf(i) + " de " + color, i));
            }
        }
        return jeuDeCard;
    }

    public List<Card> give26RandomCard(){
        List<Card> newDeckWith26Cards = new ArrayList<>();
        for (int i = 1 ; i <= 26 ; i++){
            Card card = getRandomCard(this.jeuDeCard);
            this.jeuDeCard.remove(card);
            newDeckWith26Cards.add(card);
        }
        return newDeckWith26Cards;
    }

    private Set<String> getColors(){
        Set<String> colors = new HashSet<>();
        colors.add("Carreau");
        colors.add("Coeur");
        colors.add("Trèfle");
        colors.add("Pique");
        return colors;
    }

    private String getStringOf(int carte){
        return switch (carte) {
            case 1 -> "as";
            case 2 -> "deux";
            case 3 -> "trois";
            case 4 -> "quatre";
            case 5 -> "cinq";
            case 6 -> "six";
            case 7 -> "sept";
            case 8 -> "huit";
            case 9 -> "neuf";
            case 10 -> "dix";
            case 11 -> "valet";
            case 12 -> "dame";
            case 13 -> "roi";
            default -> throw new RuntimeException("Card inexistante");
        };
    }
}
