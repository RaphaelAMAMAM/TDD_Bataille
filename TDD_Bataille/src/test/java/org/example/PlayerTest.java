package org.example;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.Main.bothPlayerGotSameCardValue;
import static org.example.Main.thereIsAWinnerForThisRound;

class PlayerTest {

    Player playerOne = new Player("Raphael");
    Player playerTwo = new Player("Adversaire");
    CardGameWith52Cards jeu52Cartes = new CardGameWith52Cards();
    CardBattleGame cardBattleGame = new CardBattleGame(playerOne, playerTwo);

    static void addTempCards(List<Card> cartesList) {
        cartesList.add(new Card("as de pique", 14));
        cartesList.add(new Card("sept de pique", 7));
    }

    @BeforeEach
    void setUp() {
        playerOne.setDeckOne(jeu52Cartes.give26RandomCard());
        playerTwo.setDeckOne(jeu52Cartes.give26RandomCard());
    }

    @Test
    void Should_return_52_When_We_Ask_For_Size_Of_New_DeckOne() {
        int expectedSize = 26;
        int gameSize = playerOne.getSizeDeckOne();
        assertThat(gameSize).isEqualTo(expectedSize);
    }

    @Test
    void Should_return_0_When_We_Ask_For_Size_Of_New_DeckTwo() {
        int expectedSize = 0;
        int gameSize = playerOne.getSizeDeckTwo();
        assertThat(gameSize).isEqualTo(expectedSize);
    }

    @Test
    void Should_return_true_if_card_of_player1_there_is_a_winner() {
        Card cardPlayerOne = new Card("neuf de pique", 9);
        Card cardPlayerTwo = new Card("huit de pique", 8);
        playerOne.setCarteInHand(cardPlayerOne);
        playerTwo.setCarteInHand(cardPlayerTwo);
        boolean thereIsAWinner = thereIsAWinnerForThisRound(playerOne, playerTwo);
        assertThat(thereIsAWinner).isTrue();
    }

    @Test
    void Should_return_false_if_there_is_no_winner_for_this_round() {
        Card cardPlayerOne = new Card("huit de pique", 8);
        Card cardPlayerTwo = new Card("huit de coeur", 8);
        playerOne.setCarteInHand(cardPlayerOne);
        playerTwo.setCarteInHand(cardPlayerTwo);
        boolean thereIsWinner = thereIsAWinnerForThisRound(playerOne, playerTwo);
        assertThat(thereIsWinner).isFalse();
    }

    @Test
    void Should_return_true_if_card_of_player1_is_equal_to_card_of_player2() {
        Card cardPlayerOne = new Card("huit de pique", 8);
        Card cardPlayerTwo = new Card("huit de pique", 8);
        boolean playerOneWin = cardBattleGame.areEqualsCards(cardPlayerOne, cardPlayerTwo);
        assertThat(playerOneWin).isTrue();
    }

    @Test
    void Should_return_true_if_there_is_a_looser() {
        playerOne.getDeckOne().clear();
        playerOne.getDeckTwo().clear();
        boolean someoneLost = cardBattleGame.someoneLost(playerOne, playerTwo);
        assertThat(someoneLost).isTrue();
    }

    @Test
    void Should_return_false_if_there_is_no_looser() {
        boolean someoneLost = cardBattleGame.someoneLost(playerOne, playerTwo);
        assertThat(someoneLost).isFalse();
    }

    @Test
    void Should_return_Name_Of_Winner() {
        playerTwo.getDeckOne().clear();
        playerTwo.getDeckTwo().clear();
        String result = cardBattleGame.getWhoWon(playerOne, playerTwo);
        String resultExpected = "Raphael a gagné la partie";
        assertThat(result).isEqualTo(resultExpected);
    }

    @Test
    void Should_return_null_if_there_is_no_winner() {
        String result = cardBattleGame.getWhoWon(playerOne, playerTwo);
        assertThat(result).isNull();
    }


    @Test
    void Should_return_true_if_main_deck_is_empty() {
        playerOne.getDeckOne().clear();
        boolean result = playerOne.hasFirstDeckEmpty();
        assertThat(result).isTrue();
    }

    @Test
    void Should_return_false_if_main_Deck_is_not_empty() {
        boolean result = playerOne.hasFirstDeckEmpty();
        assertThat(result).isFalse();
    }

    @Test
    void Deck_un_should_return_equal_than_deck_two() {
        playerOne.getDeckOne().clear();
        addTempCards(playerOne.getDeckTwo());
        playerOne.putDeck2InDeck1();

        List<Card> newDeckOne = playerOne.getDeckOne();
        List<Card> resultExpected = playerOne.getDeckTwo();

        assertThat(newDeckOne.hashCode()).isEqualTo(resultExpected.hashCode());
    }

    @Test
    void Deck_two_should_be_void_after_clear() {
        addTempCards(playerOne.getDeckTwo());
        playerOne.clearDeckTwo();
        assertThat(playerOne.getDeckTwo().size()).isEqualTo(0);
    }

    @Test
    void Should_return_true_if_both_users_got_same_cards() {
        playerOne.setCarteInHand(new Card("as de pique", 14));
        playerTwo.setCarteInHand(new Card("as de pique", 14));

        boolean areSameCard = bothPlayerGotSameCardValue(playerOne, playerTwo);
        assertThat(areSameCard).isTrue();
    }

    @Test
    void Should_return_false_if_both_users_got_different_cards() {
        playerOne.setCarteInHand(new Card("as de pique", 14));
        playerTwo.setCarteInHand(new Card("trois de coeur", 3));

        boolean areSameCard = bothPlayerGotSameCardValue(playerOne, playerTwo);
        assertThat(areSameCard).isFalse();
    }

    @Test
    void Should_return_list_with_card_added() {
        Card cardToAdd = new Card("as de pique", 14);
        Main.addCardToCardInGame(cardBattleGame, cardToAdd);
        int expectedResult = 1;
        int voidListWithOneCardAddedSize = cardBattleGame.getCartesEnJeu().size();

        assertThat(voidListWithOneCardAddedSize).isEqualTo(expectedResult);
    }

    @Test
    void Should_return_player_deck_size_less_one_when_we_remove_one_card() {
        Card cardFactice = new Card("Card factice", -1);
        // Création d'une carte factice pour éviter un éventuel conflit entre 2 cartes identiques
        playerOne.setCarteInHand(cardFactice);
        playerOne.getDeckOne().add(cardFactice);
        Main.removeCardInHandFromPlayerDeckOne(playerOne);
        int resultExpected = 26;
        int result = playerOne.getDeckOne().size();

        assertThat(result).isEqualTo(resultExpected);
    }

    @Test
    void Should_switch_deck_if_deck_one_is_empty() {
        addTempCards(playerOne.getDeckTwo());
        List<Card> tempDeck = new ArrayList<>(playerOne.getDeckTwo());
        playerOne.getDeckOne().clear();
        playerOne.switchDeckIfDeckOneIsEmpty();
        List<Card> newDeckOne = playerOne.getDeckOne();

        assertThat(newDeckOne).isEqualTo(tempDeck);

    }
}