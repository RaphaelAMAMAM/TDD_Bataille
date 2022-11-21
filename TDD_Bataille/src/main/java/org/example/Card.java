package org.example;

import java.util.Objects;

public class Card {

    private final String nom;
    private final int valeur;

    public Card(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() {
        return nom;
    }

    public int getValeur() {
        return valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return valeur == card.valeur && nom.equals(card.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, valeur);
    }
}
