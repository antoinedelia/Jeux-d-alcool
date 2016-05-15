package com.antoinedelia.lebarbu_versionalcool;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<AbstractMap.SimpleEntry<String, Cards>> cartes = new ArrayList<>();
    public Deck()
    {
        cartes.add(new AbstractMap.SimpleEntry<>("ace_of_clubs", Cards.ACE));
        cartes.add(new AbstractMap.SimpleEntry<>("ace_of_spades", Cards.ACE));
        cartes.add(new AbstractMap.SimpleEntry<>("ace_of_diamonds", Cards.ACE));
        cartes.add(new AbstractMap.SimpleEntry<>("ace_of_hearts", Cards.ACE));

        cartes.add(new AbstractMap.SimpleEntry<>("eight_of_clubs", Cards.EIGHT));
        cartes.add(new AbstractMap.SimpleEntry<>("eight_of_spades", Cards.EIGHT));
        cartes.add(new AbstractMap.SimpleEntry<>("eight_of_diamonds", Cards.EIGHT));
        cartes.add(new AbstractMap.SimpleEntry<>("eight_of_hearts", Cards.EIGHT));

        cartes.add(new AbstractMap.SimpleEntry<>("five_of_clubs", Cards.FIVE));
        cartes.add(new AbstractMap.SimpleEntry<>("five_of_spades", Cards.FIVE));
        cartes.add(new AbstractMap.SimpleEntry<>("five_of_diamonds", Cards.FIVE));
        cartes.add(new AbstractMap.SimpleEntry<>("five_of_hearts", Cards.FIVE));

        cartes.add(new AbstractMap.SimpleEntry<>("four_of_clubs", Cards.FOUR));
        cartes.add(new AbstractMap.SimpleEntry<>("four_of_spades", Cards.FOUR));
        cartes.add(new AbstractMap.SimpleEntry<>("four_of_diamonds", Cards.FOUR));
        cartes.add(new AbstractMap.SimpleEntry<>("four_of_hearts", Cards.FOUR));

        cartes.add(new AbstractMap.SimpleEntry<>("jack_of_clubs", Cards.JACK));
        cartes.add(new AbstractMap.SimpleEntry<>("jack_of_spades", Cards.JACK));
        cartes.add(new AbstractMap.SimpleEntry<>("jack_of_diamonds", Cards.JACK));
        cartes.add(new AbstractMap.SimpleEntry<>("jack_of_hearts", Cards.JACK));

        cartes.add(new AbstractMap.SimpleEntry<>("king_of_clubs", Cards.KING));
        cartes.add(new AbstractMap.SimpleEntry<>("king_of_spades", Cards.KING));
        cartes.add(new AbstractMap.SimpleEntry<>("king_of_diamonds", Cards.KING));
        cartes.add(new AbstractMap.SimpleEntry<>("king_of_hearts", Cards.KING));

        cartes.add(new AbstractMap.SimpleEntry<>("nine_of_clubs", Cards.NINE));
        cartes.add(new AbstractMap.SimpleEntry<>("nine_of_spades", Cards.NINE));
        cartes.add(new AbstractMap.SimpleEntry<>("nine_of_diamonds", Cards.NINE));
        cartes.add(new AbstractMap.SimpleEntry<>("nine_of_hearts", Cards.NINE));

        cartes.add(new AbstractMap.SimpleEntry<>("queen_of_clubs", Cards.QUEEN));
        cartes.add(new AbstractMap.SimpleEntry<>("queen_of_spades", Cards.QUEEN));
        cartes.add(new AbstractMap.SimpleEntry<>("queen_of_diamonds", Cards.QUEEN));
        cartes.add(new AbstractMap.SimpleEntry<>("queen_of_hearts", Cards.QUEEN));

        cartes.add(new AbstractMap.SimpleEntry<>("seven_of_clubs", Cards.SEVEN));
        cartes.add(new AbstractMap.SimpleEntry<>("seven_of_spades", Cards.SEVEN));
        cartes.add(new AbstractMap.SimpleEntry<>("seven_of_diamonds", Cards.SEVEN));
        cartes.add(new AbstractMap.SimpleEntry<>("seven_of_hearts", Cards.SEVEN));

        cartes.add(new AbstractMap.SimpleEntry<>("six_of_clubs", Cards.SIX));
        cartes.add(new AbstractMap.SimpleEntry<>("six_of_spades", Cards.SIX));
        cartes.add(new AbstractMap.SimpleEntry<>("six_of_diamonds", Cards.SIX));
        cartes.add(new AbstractMap.SimpleEntry<>("six_of_hearts", Cards.SIX));

        cartes.add(new AbstractMap.SimpleEntry<>("ten_of_clubs", Cards.TEN));
        cartes.add(new AbstractMap.SimpleEntry<>("ten_of_spades", Cards.TEN));
        cartes.add(new AbstractMap.SimpleEntry<>("ten_of_diamonds", Cards.TEN));
        cartes.add(new AbstractMap.SimpleEntry<>("ten_of_hearts", Cards.TEN));

        cartes.add(new AbstractMap.SimpleEntry<>("three_of_clubs", Cards.THREE));
        cartes.add(new AbstractMap.SimpleEntry<>("three_of_spades", Cards.THREE));
        cartes.add(new AbstractMap.SimpleEntry<>("three_of_diamonds", Cards.THREE));
        cartes.add(new AbstractMap.SimpleEntry<>("three_of_hearts", Cards.THREE));

        cartes.add(new AbstractMap.SimpleEntry<>("two_of_clubs", Cards.TWO));
        cartes.add(new AbstractMap.SimpleEntry<>("two_of_spades", Cards.TWO));
        cartes.add(new AbstractMap.SimpleEntry<>("two_of_diamonds", Cards.TWO));
        cartes.add(new AbstractMap.SimpleEntry<>("two_of_hearts", Cards.TWO));
    }

    public int getRemainingCards()
    {
        return cartes.size();
    }


    public AbstractMap.SimpleEntry<String, Cards> getNextCard()
    {
        int number;
        AbstractMap.SimpleEntry<String, Cards> card = null;
        if(!cartes.isEmpty()) {
            do {
                number = new Random().nextInt(cartes.size());
            } while (cartes.get(number) == null);
            card = new AbstractMap.SimpleEntry<>(cartes.get(number).getKey(), cartes.get(number).getValue());
            cartes.remove(number);
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
