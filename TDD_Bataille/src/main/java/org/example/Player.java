package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player extends CanDraw{

    private final String prenom;
    private List<Card> deckOne;
    private List<Card> deckTwo;
    private Card cardInHand;

    public Player(String prenom) {
        this.prenom = prenom;
        this.deckOne = new ArrayList<>();
        this.deckTwo = new ArrayList<>();
        this.cardInHand = null;
    }

    public String getPrenom() {
        return prenom;
    }

    public List<Card> getDeckOne() {
        return deckOne;
    }

    public void setDeckOne(List<Card> deckOne) {
        this.deckOne = new ArrayList<>(deckOne);
    }

    public void setDeckTwo(List<Card> deckTwo) {
        this.deckTwo = deckTwo;
    }

    public List<Card> getDeckTwo() {
        return deckTwo;
    }

    public int getSizeDeckOne(){
        return deckOne.size();
    }

    public int getSizeDeckTwo() { return deckTwo.size(); }

    public Card getCarteInHand() {
        return cardInHand;
    }

    public void setCarteInHand(Card cardInHand) {
        this.cardInHand = cardInHand;
    }

    public int getTotalCard(){
        return getSizeDeckOne() + getSizeDeckTwo();
    }

    public boolean hasFirstDeckEmpty(){
        return getSizeDeckOne() == 0;
    }



    public void putDeck2InDeck1(){
        setDeckOne(this.getDeckTwo());
    }

    public void clearDeckTwo(){
        this.deckTwo.clear();

    }

    public boolean switchDeckIfDeckOneIsEmpty(){
        if (getSizeDeckOne() == 0){
            putDeck2InDeck1();
            clearDeckTwo();
            return true;
        }
        return false;

    }
}
