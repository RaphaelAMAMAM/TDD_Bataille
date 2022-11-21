package org.example;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Player player1 = new Player("Raphael");
        Player player2 = new Player("Adversaire");

        CardGameWith52Cards cardGame = new CardGameWith52Cards();

        player1.setDeckOne(cardGame.give26RandomCard());
        player2.setDeckOne(cardGame.give26RandomCard());

        CardBattleGame game = new CardBattleGame(player1, player2);

        int counter = 0;
        while (true) {
            counter++;
            displayIntroAndNumberOfCards(player1, player2);
            if (hasSomebodyLost(game, player1, player2)) break;
            switchDeckIfNecessary(player1, player2);
            playerDrawNewCard(player1);
            playerDrawNewCard(player2);

            if (bothPlayerGotSameCardValue(player1, player2)) {
                displayMessageForBothPlayerHaveSameCard(player1, player2);
                putCardInHandToCardInGame(game, player1);
                putCardInHandToCardInGame(game, player2);
                putDeckTwoInDeckOneIfNecessary(player1);
                putDeckTwoInDeckOneIfNecessary(player2);
                if (giveCardsToOtherPlayerIfHasLost(player1, player2, game)) continue;
                if (giveCardsToOtherPlayerIfHasLost(player2, player1, game)) continue;

                playerGetHiddenCard(game, player1);
                playerGetHiddenCard(game, player2);
                playerDrawNewCard(player1);
                playerDrawNewCard(player2);
            }


            if (thereIsAWinnerForThisRound(player1, player2)){
                Player roundWinner = getRoundWinner(player1, player2);
                Player roundLooser = getRoundLooser(player1, player2);

                putCardInHandToDeckTwo(roundWinner);
                looserPutHisCardInHandInDeckTwoOfWinner(roundWinner, roundLooser);
                putCardInGameIntoWinnerDeckTwo(game, roundWinner);
                System.out.println(roundWinner.getPrenom() + " a gagné cette manche");

            }
        }
        System.out.println(" en " + counter + " tours");
    }

    public static boolean bothPlayerGotSameCardValue(Player playerUn, Player playerTwo) {
        return playerUn.getCarteInHand().getValeur() == playerTwo.getCarteInHand().getValeur();
    }

    public static void addCardToCardInGame(CardBattleGame jeu, Card card) {
        jeu.getCartesEnJeu().add(card);
    }

    public static void removeCardInHandFromPlayerDeckOne(Player player) {
        player.getDeckOne().remove(player.getCarteInHand());
    }

    private static void displayIntroAndNumberOfCards(Player player1, Player player2) {
        System.out.println("----------------NOUVEAU TOUR----------------");
        System.out.println(player1.getPrenom() + " a " + player1.getTotalCard() + " carte(s)");
        System.out.println(player2.getPrenom() + " a " + player2.getTotalCard() + " carte(s)");
    }

    private static boolean hasSomebodyLost(CardBattleGame game, Player player1, Player player2) {
        if (game.someoneLost(player1, player2)) {
            System.out.print(game.getWhoWon(player1, player2));
            return true;
        }
        return false;
    }

    private static void switchDeckIfNecessary(Player player1, Player player2) {
        if (player1.switchDeckIfDeckOneIsEmpty()) {
            System.out.println(player1.getPrenom() + " a changé de tas");
        }

        if (player2.switchDeckIfDeckOneIsEmpty()) {
            System.out.println(player2.getPrenom() + " a changé de tas");
        }
    }

    private static void playerDrawNewCard(Player player) {
        player.setCarteInHand(player.getRandomCard(player.getDeckOne()));
        System.out.println(player.getPrenom() + " a pioché la carte " + player.getCarteInHand().getNom());
    }

    private static void displayMessageForBothPlayerHaveSameCard(Player player1, Player player2) {
        System.out.println("_______\n" + player1.getPrenom() + " et " + player2.getPrenom() + " ont pioché des cartes de même valeur\n" +
                "Les deux joueurs repiochent une carte qu'ils vont déposer en face cachée sur leur ancienne carte\n" +
                "Les deux joueurs repiochent une carte et les déposent en face ouverte\n" +
                "_______");
    }

    private static void putCardInHandToCardInGame(CardBattleGame game, Player player) {
        addCardToCardInGame(game, player.getCarteInHand());
        removeCardInHandFromPlayerDeckOne(player);
    }

    private static void putDeckTwoInDeckOneIfNecessary(Player player) {
        if (player.getSizeDeckOne() < 2) {
            putDeckTwoInDeckOne(player);
            player.clearDeckTwo();
        }
    }

    private static void putDeckTwoInDeckOne(Player player) {
        for (Card card : player.getDeckTwo()) {
            player.getDeckOne().add(card);
        }
    }

    private static boolean hasPlayerLost(Player player) {
        return player.getSizeDeckOne() < 2;
    }

    private static void giveDeckToOtherPlayer(List<Card> cards, Player winner) {
        for (Card card : cards) {
            winner.getDeckTwo().add(card);

        }
    }

    private static boolean giveCardsToOtherPlayerIfHasLost(Player winner, Player looser, CardBattleGame game) {
        if (hasPlayerLost(looser)) {
            giveDeckToOtherPlayer(looser.getDeckOne(), winner);
            giveDeckToOtherPlayer(looser.getDeckTwo(), winner);
            giveDeckToOtherPlayer(game.getCartesEnJeu(), winner);
            looser.getDeckOne().clear();
            looser.getDeckTwo().clear();
            game.getCartesEnJeu().clear();
            System.out.println(looser.getPrenom() + " n'a plus assez de carte pour repiocher");
            return true;
        }
        return false;
    }

    private static void playerGetHiddenCard(CardBattleGame game, Player player){
        Card hiddenCardPlayerOne = game.getRandomCard(player.getDeckOne());
        player.setCarteInHand(hiddenCardPlayerOne);
        putCardInHandToCardInGame(game, player);
    }

    private static void putCardInHandToDeckTwo(Player player){
        player.getDeckOne().remove(player.getCarteInHand());
        player.getDeckTwo().add(player.getCarteInHand());
    }

    private static void looserPutHisCardInHandInDeckTwoOfWinner(Player winner, Player looser){
        winner.getDeckTwo().add(looser.getCarteInHand());
        looser.getDeckOne().remove(looser.getCarteInHand());
    }

    private static void putCardInGameIntoWinnerDeckTwo(CardBattleGame game, Player winner){
        if (!(game.getCartesEnJeu().size() == 0)) {
            for (Card card : game.getCartesEnJeu()) {
                winner.getDeckTwo().add(card);
            }
            game.getCartesEnJeu().clear();
        }
    }

    public static boolean thereIsAWinnerForThisRound(Player player1, Player player2){
        return player1.getCarteInHand().getValeur() != player2.getCarteInHand().getValeur();
    }

    private static Player getRoundWinner(Player player1, Player player2){
        return player1.getCarteInHand().getValeur() > player2.getCarteInHand().getValeur() ?
                player1 : player2;
    }

    private static Player getRoundLooser(Player player1, Player player2){
        return player1.getCarteInHand().getValeur() > player2.getCarteInHand().getValeur() ?
                player2 : player1;
    }
}
