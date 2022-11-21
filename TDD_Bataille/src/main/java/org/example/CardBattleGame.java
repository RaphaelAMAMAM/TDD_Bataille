package org.example;

import java.util.ArrayList;
import java.util.List;

public class CardBattleGame extends CanDraw {

    private final Player playerOne;
    private final Player playerTwo;
    private final List<Card> cartesEnJeu;

    public CardBattleGame(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.cartesEnJeu = new ArrayList<>();
    }

    public boolean areEqualsCards (Card cardJoueurUn, Card cardJoueurDeux){
        return cardJoueurUn.getValeur() == cardJoueurDeux.getValeur();
    }

    public boolean someoneLost(Player player1, Player player2){
        return (player1.getSizeDeckOne() == 0 && player1.getSizeDeckTwo() == 0) ||
                (player2.getSizeDeckOne() == 0 && player2.getSizeDeckTwo() == 0);

    }

    public String getWhoWon(Player player1, Player player2){
        if (player1.getSizeDeckOne() == 0 && player1.getSizeDeckTwo() == 0){
            return player2.getPrenom() + " a gagné la partie";
        }
        if (player2.getSizeDeckOne() == 0 && player2.getSizeDeckTwo() == 0){
            return player1.getPrenom() + " a gagné la partie";
        }
        return null;
    }

    public List<Card> getCartesEnJeu() {
        return cartesEnJeu;
    }
}
