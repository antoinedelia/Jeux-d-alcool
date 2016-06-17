package com.antoinedelia.lebarbu_versionalcool;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> cards = new ArrayList<>();
    private Context context;

    public Deck(String typeOfRule, Context context) {
        this.context = context;

        cards.add(new Card(Cards.ACE, "ace_of_clubs", getRule(typeOfRule, Cards.ACE), SuitCards.CLUBS));
        cards.add(new Card(Cards.ACE, "ace_of_spades", getRule(typeOfRule, Cards.ACE), SuitCards.SPADES));
        cards.add(new Card(Cards.ACE, "ace_of_diamonds", getRule(typeOfRule, Cards.ACE), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.ACE, "ace_of_hearts", getRule(typeOfRule, Cards.ACE), SuitCards.HEARTS));

        cards.add(new Card(Cards.EIGHT, "eight_of_clubs", getRule(typeOfRule, Cards.EIGHT), SuitCards.CLUBS));
        cards.add(new Card(Cards.EIGHT, "eight_of_spades", getRule(typeOfRule, Cards.EIGHT), SuitCards.SPADES));
        cards.add(new Card(Cards.EIGHT, "eight_of_diamonds", getRule(typeOfRule, Cards.EIGHT), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.EIGHT, "eight_of_hearts", getRule(typeOfRule, Cards.EIGHT), SuitCards.HEARTS));

        cards.add(new Card(Cards.FIVE, "five_of_clubs", getRule(typeOfRule, Cards.FIVE), SuitCards.CLUBS));
        cards.add(new Card(Cards.FIVE, "five_of_spades", getRule(typeOfRule, Cards.FIVE), SuitCards.SPADES));
        cards.add(new Card(Cards.FIVE, "five_of_diamonds", getRule(typeOfRule, Cards.FIVE), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.FIVE, "five_of_hearts", getRule(typeOfRule, Cards.FIVE), SuitCards.HEARTS));

        cards.add(new Card(Cards.FOUR, "four_of_clubs", getRule(typeOfRule, Cards.FOUR), SuitCards.CLUBS));
        cards.add(new Card(Cards.FOUR, "four_of_spades", getRule(typeOfRule, Cards.FOUR), SuitCards.SPADES));
        cards.add(new Card(Cards.FOUR, "four_of_diamonds", getRule(typeOfRule, Cards.FOUR), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.FOUR, "four_of_hearts", getRule(typeOfRule, Cards.FOUR), SuitCards.HEARTS));

        cards.add(new Card(Cards.JACK, "jack_of_clubs", getRule(typeOfRule, Cards.JACK), SuitCards.CLUBS));
        cards.add(new Card(Cards.JACK, "jack_of_spades", getRule(typeOfRule, Cards.JACK), SuitCards.SPADES));
        cards.add(new Card(Cards.JACK, "jack_of_diamonds", getRule(typeOfRule, Cards.JACK), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.JACK, "jack_of_hearts", getRule(typeOfRule, Cards.JACK), SuitCards.HEARTS));

        cards.add(new Card(Cards.KING, "king_of_clubs", getRule(typeOfRule, Cards.KING), SuitCards.CLUBS));
        cards.add(new Card(Cards.KING, "king_of_spades", getRule(typeOfRule, Cards.KING), SuitCards.SPADES));
        cards.add(new Card(Cards.KING, "king_of_diamonds", getRule(typeOfRule, Cards.KING), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.KING, "king_of_hearts", getRule(typeOfRule, Cards.KING), SuitCards.HEARTS));

        cards.add(new Card(Cards.NINE, "nine_of_clubs", getRule(typeOfRule, Cards.NINE), SuitCards.CLUBS));
        cards.add(new Card(Cards.NINE, "nine_of_spades", getRule(typeOfRule, Cards.NINE), SuitCards.SPADES));
        cards.add(new Card(Cards.NINE, "nine_of_diamonds", getRule(typeOfRule, Cards.NINE), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.NINE, "nine_of_hearts", getRule(typeOfRule, Cards.NINE), SuitCards.HEARTS));

        cards.add(new Card(Cards.QUEEN, "queen_of_clubs", getRule(typeOfRule, Cards.QUEEN), SuitCards.CLUBS));
        cards.add(new Card(Cards.QUEEN, "queen_of_spades", getRule(typeOfRule, Cards.QUEEN), SuitCards.SPADES));
        cards.add(new Card(Cards.QUEEN, "queen_of_diamonds", getRule(typeOfRule, Cards.QUEEN), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.QUEEN, "queen_of_hearts", getRule(typeOfRule, Cards.QUEEN), SuitCards.HEARTS));

        cards.add(new Card(Cards.SEVEN, "seven_of_clubs", getRule(typeOfRule, Cards.SEVEN), SuitCards.CLUBS));
        cards.add(new Card(Cards.SEVEN, "seven_of_spades", getRule(typeOfRule, Cards.SEVEN), SuitCards.SPADES));
        cards.add(new Card(Cards.SEVEN, "seven_of_diamonds", getRule(typeOfRule, Cards.SEVEN), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.SEVEN, "seven_of_hearts", getRule(typeOfRule, Cards.SEVEN), SuitCards.HEARTS));

        cards.add(new Card(Cards.SIX, "six_of_clubs", getRule(typeOfRule, Cards.SIX), SuitCards.CLUBS));
        cards.add(new Card(Cards.SIX, "six_of_spades", getRule(typeOfRule, Cards.SIX), SuitCards.SPADES));
        cards.add(new Card(Cards.SIX, "six_of_diamonds", getRule(typeOfRule, Cards.SIX), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.SIX, "six_of_hearts", getRule(typeOfRule, Cards.SIX), SuitCards.HEARTS));

        cards.add(new Card(Cards.TEN, "ten_of_clubs", getRule(typeOfRule, Cards.TEN), SuitCards.CLUBS));
        cards.add(new Card(Cards.TEN, "ten_of_spades", getRule(typeOfRule, Cards.TEN), SuitCards.SPADES));
        cards.add(new Card(Cards.TEN, "ten_of_diamonds", getRule(typeOfRule, Cards.TEN), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.TEN, "ten_of_hearts", getRule(typeOfRule, Cards.TEN), SuitCards.HEARTS));

        cards.add(new Card(Cards.THREE, "three_of_clubs", getRule(typeOfRule, Cards.THREE), SuitCards.CLUBS));
        cards.add(new Card(Cards.THREE, "three_of_spades", getRule(typeOfRule, Cards.THREE), SuitCards.SPADES));
        cards.add(new Card(Cards.THREE, "three_of_diamonds", getRule(typeOfRule, Cards.THREE), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.THREE, "three_of_hearts", getRule(typeOfRule, Cards.THREE), SuitCards.HEARTS));

        cards.add(new Card(Cards.TWO, "two_of_clubs", getRule(typeOfRule, Cards.TWO), SuitCards.CLUBS));
        cards.add(new Card(Cards.TWO, "two_of_spades", getRule(typeOfRule, Cards.TWO), SuitCards.SPADES));
        cards.add(new Card(Cards.TWO, "two_of_diamonds", getRule(typeOfRule, Cards.TWO), SuitCards.DIAMONDS));
        cards.add(new Card(Cards.TWO, "two_of_hearts", getRule(typeOfRule, Cards.TWO), SuitCards.HEARTS));
    }

    public Rule getRule(String typeRule, Cards typeCard) {
        switch (typeRule) {
            case "CircleOfDeath":
                return new RuleCircleOfDeath(typeCard, context);
            case "AroundTheWorldRoundOne":
                return null;
        }
        return new RuleCircleOfDeath(typeCard, context);
    }

    public int getRemainingCards() {
        return cards.size();
    }


    public Card getNextCard() {
        int number;
        Card card = null;
        if (!cards.isEmpty()) {
            number = new Random().nextInt(cards.size());
            card = new Card(cards.get(number).getName(), cards.get(number).getPath(), cards.get(number).getRule(), cards.get(number).getSuit());
            cards.remove(number);
        } else
            System.out.println("Fin du deck");

        return card;
    }

    public enum Cards {
        ACE(0), TWO(1), THREE(2), FOUR(3),
        FIVE(4), SIX(5), SEVEN(6), EIGHT(7),
        NINE(8), TEN(9), JACK(10),
        QUEEN(11), KING(12);

        private int numVal;

        Cards(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }
    }

    public enum SuitCards {
        HEARTS(0), SPADES(1), DIAMONDS(2), CLUBS(3);

        private int numVal;

        SuitCards(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }
    }
}
