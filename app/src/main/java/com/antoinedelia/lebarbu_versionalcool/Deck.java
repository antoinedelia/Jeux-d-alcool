package com.antoinedelia.lebarbu_versionalcool;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<AbstractMap.SimpleEntry<String, Cards>> cards = new ArrayList<>();
    public Deck()
    {
        cards.add(new AbstractMap.SimpleEntry<>("ace_of_clubs", Cards.ACE));
        cards.add(new AbstractMap.SimpleEntry<>("ace_of_spades", Cards.ACE));
        cards.add(new AbstractMap.SimpleEntry<>("ace_of_diamonds", Cards.ACE));
        cards.add(new AbstractMap.SimpleEntry<>("ace_of_hearts", Cards.ACE));

        cards.add(new AbstractMap.SimpleEntry<>("eight_of_clubs", Cards.EIGHT));
        cards.add(new AbstractMap.SimpleEntry<>("eight_of_spades", Cards.EIGHT));
        cards.add(new AbstractMap.SimpleEntry<>("eight_of_diamonds", Cards.EIGHT));
        cards.add(new AbstractMap.SimpleEntry<>("eight_of_hearts", Cards.EIGHT));

        cards.add(new AbstractMap.SimpleEntry<>("five_of_clubs", Cards.FIVE));
        cards.add(new AbstractMap.SimpleEntry<>("five_of_spades", Cards.FIVE));
        cards.add(new AbstractMap.SimpleEntry<>("five_of_diamonds", Cards.FIVE));
        cards.add(new AbstractMap.SimpleEntry<>("five_of_hearts", Cards.FIVE));

        cards.add(new AbstractMap.SimpleEntry<>("four_of_clubs", Cards.FOUR));
        cards.add(new AbstractMap.SimpleEntry<>("four_of_spades", Cards.FOUR));
        cards.add(new AbstractMap.SimpleEntry<>("four_of_diamonds", Cards.FOUR));
        cards.add(new AbstractMap.SimpleEntry<>("four_of_hearts", Cards.FOUR));

        cards.add(new AbstractMap.SimpleEntry<>("jack_of_clubs", Cards.JACK));
        cards.add(new AbstractMap.SimpleEntry<>("jack_of_spades", Cards.JACK));
        cards.add(new AbstractMap.SimpleEntry<>("jack_of_diamonds", Cards.JACK));
        cards.add(new AbstractMap.SimpleEntry<>("jack_of_hearts", Cards.JACK));

        cards.add(new AbstractMap.SimpleEntry<>("king_of_clubs", Cards.KING));
        cards.add(new AbstractMap.SimpleEntry<>("king_of_spades", Cards.KING));
        cards.add(new AbstractMap.SimpleEntry<>("king_of_diamonds", Cards.KING));
        cards.add(new AbstractMap.SimpleEntry<>("king_of_hearts", Cards.KING));

        cards.add(new AbstractMap.SimpleEntry<>("nine_of_clubs", Cards.NINE));
        cards.add(new AbstractMap.SimpleEntry<>("nine_of_spades", Cards.NINE));
        cards.add(new AbstractMap.SimpleEntry<>("nine_of_diamonds", Cards.NINE));
        cards.add(new AbstractMap.SimpleEntry<>("nine_of_hearts", Cards.NINE));

        cards.add(new AbstractMap.SimpleEntry<>("queen_of_clubs", Cards.QUEEN));
        cards.add(new AbstractMap.SimpleEntry<>("queen_of_spades", Cards.QUEEN));
        cards.add(new AbstractMap.SimpleEntry<>("queen_of_diamonds", Cards.QUEEN));
        cards.add(new AbstractMap.SimpleEntry<>("queen_of_hearts", Cards.QUEEN));

        cards.add(new AbstractMap.SimpleEntry<>("seven_of_clubs", Cards.SEVEN));
        cards.add(new AbstractMap.SimpleEntry<>("seven_of_spades", Cards.SEVEN));
        cards.add(new AbstractMap.SimpleEntry<>("seven_of_diamonds", Cards.SEVEN));
        cards.add(new AbstractMap.SimpleEntry<>("seven_of_hearts", Cards.SEVEN));

        cards.add(new AbstractMap.SimpleEntry<>("six_of_clubs", Cards.SIX));
        cards.add(new AbstractMap.SimpleEntry<>("six_of_spades", Cards.SIX));
        cards.add(new AbstractMap.SimpleEntry<>("six_of_diamonds", Cards.SIX));
        cards.add(new AbstractMap.SimpleEntry<>("six_of_hearts", Cards.SIX));

        cards.add(new AbstractMap.SimpleEntry<>("ten_of_clubs", Cards.TEN));
        cards.add(new AbstractMap.SimpleEntry<>("ten_of_spades", Cards.TEN));
        cards.add(new AbstractMap.SimpleEntry<>("ten_of_diamonds", Cards.TEN));
        cards.add(new AbstractMap.SimpleEntry<>("ten_of_hearts", Cards.TEN));

        cards.add(new AbstractMap.SimpleEntry<>("three_of_clubs", Cards.THREE));
        cards.add(new AbstractMap.SimpleEntry<>("three_of_spades", Cards.THREE));
        cards.add(new AbstractMap.SimpleEntry<>("three_of_diamonds", Cards.THREE));
        cards.add(new AbstractMap.SimpleEntry<>("three_of_hearts", Cards.THREE));

        cards.add(new AbstractMap.SimpleEntry<>("two_of_clubs", Cards.TWO));
        cards.add(new AbstractMap.SimpleEntry<>("two_of_spades", Cards.TWO));
        cards.add(new AbstractMap.SimpleEntry<>("two_of_diamonds", Cards.TWO));
        cards.add(new AbstractMap.SimpleEntry<>("two_of_hearts", Cards.TWO));
    }

    public int getRemainingCards()
    {
        return cards.size();
    }


    public AbstractMap.SimpleEntry<String, Cards> getNextCard()
    {
        int number;
        AbstractMap.SimpleEntry<String, Cards> card = null;
        if(!cards.isEmpty()) {
            do {
                number = new Random().nextInt(cards.size());
            } while (cards.get(number) == null);
            card = new AbstractMap.SimpleEntry<>(cards.get(number).getKey(), cards.get(number).getValue());
            cards.remove(number);
        }
        else
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
}
