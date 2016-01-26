package com.antoinedelia.lebarbu_versionalcool;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Antoine on 26/01/2016. !
 */
public class Deck {

    //private List<String> cartes;
    //private String[] cartes = {"ace_of_clubs", "ace_of_spades", "ace_of_diamonds", "ace_of_hearts"};
    private List<String> cartes = new ArrayList<String>();
    private String actual_card;

    public Deck()
    {
        cartes.add("ace_of_clubs");
        cartes.add("ace_of_spades");
        cartes.add("ace_of_diamonds");
        cartes.add("ace_of_hearts");

        cartes.add("eight_of_clubs");
        cartes.add("eight_of_spades");
        cartes.add("eight_of_diamonds");
        cartes.add("eight_of_hearts");

        cartes.add("five_of_clubs");
        cartes.add("five_of_spades");
        cartes.add("five_of_diamonds");
        cartes.add("five_of_hearts");

        cartes.add("four_of_clubs");
        cartes.add("four_of_spades");
        cartes.add("four_of_diamonds");
        cartes.add("four_of_hearts");

        cartes.add("jack_of_clubs");
        cartes.add("jack_of_spades");
        cartes.add("jack_of_diamonds");
        cartes.add("jack_of_hearts");

        cartes.add("king_of_clubs");
        cartes.add("king_of_spades");
        cartes.add("king_of_diamonds");
        cartes.add("king_of_hearts");

        cartes.add("nine_of_clubs");
        cartes.add("nine_of_spades");
        cartes.add("nine_of_diamonds");
        cartes.add("nine_of_hearts");

        cartes.add("queen_of_clubs");
        cartes.add("queen_of_spades");
        cartes.add("queen_of_diamonds");
        cartes.add("queen_of_hearts");

        cartes.add("seven_of_clubs");
        cartes.add("seven_of_spades");
        cartes.add("seven_of_diamonds");
        cartes.add("seven_of_hearts");

        cartes.add("six_of_clubs");
        cartes.add("six_of_spades");
        cartes.add("six_of_diamonds");
        cartes.add("six_of_hearts");

        cartes.add("ten_of_clubs");
        cartes.add("ten_of_spades");
        cartes.add("ten_of_diamonds");
        cartes.add("ten_of_hearts");

        cartes.add("three_of_clubs");
        cartes.add("three_of_spades");
        cartes.add("three_of_diamonds");
        cartes.add("three_of_hearts");

        cartes.add("two_of_clubs");
        cartes.add("two_of_spades");
        cartes.add("two_of_diamonds");
        cartes.add("two_of_hearts");
    }

    public int getRemainingCards()
    {
        return cartes.size();
    }


    public String getNextCard()
    {
        int number = 0;
        if(!cartes.isEmpty()) {
            do {
                number = new Random().nextInt(cartes.size());
            } while (cartes.get(number) == null);
            actual_card = cartes.get(number);
            cartes.remove(number);
        }
        else
        {
            System.out.println("Fin du deck");
            actual_card = "FIN";
        }
        return actual_card;

//        int number;
//        do {
//            number = new Random().nextInt(cartes.length);
//        }while(cartes[number] == null);
//
//        actual_card = cartes[number];
//        cartes[number] = null;
//        return actual_card;
    }

}
