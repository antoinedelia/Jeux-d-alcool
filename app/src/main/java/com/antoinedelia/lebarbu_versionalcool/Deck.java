package com.antoinedelia.lebarbu_versionalcool;

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
    private String[] textRules = {"Tout le monde boit % Tous les joueurs prennent leur verre et boivent une gorgée",
    "Boit deux gorgées % Le joueur ayant tiré la carte doit boire deux gorgées",
            "Boit trois gorgées % Le joueur ayant tiré la carte doit boire trois gorgées",
            "Boit quatre gorgées % Le joueur ayant tiré la carte doit boire quatre gorgées",
            "Boit cinq gorgées % Le joueur ayant tiré la carte doit boire cinq gorgées",
            "Boit six gorgées % Le joueur ayant tiré la carte doit boire six gorgées",
            "J'ai déjà / Je n'ai jamais % Le joueur ayant tiré la carte doit dire quelque chose qu'il a déjà ou n'a jamais fait. Tous les autres joueurs doivent boire une gorgée s'ils ne se trouvent pas dans le cas du joueur",
            "Dans ma valise % Le joueur ayant tiré la carte commence le jeu en disant \"Dans ma valise, j'ai \" et ajoute un objet ou une personne. Le joueur suivant doit compléter la valise et ainsi de suite",
            "Les rimes % Le joueur ayant tiré la carte prononce un mot. Les autres joueurs devront dire un mot rimant avec celui du premier joueur jusqu'à ce que l'un d'entre eux se trompe",
            "Les thèmes % Le joueur ayant tiré la carte choisi un thème (ex : les marques d'alcool). Chaque joueur doit compléter ce thème.",
            "Snake eyes % Le joueur ayant tiré la carte peut dire \"Snake eyes !\" si un joueur le regarde dans les yeux. Ce joueur boit ainsi une gorgée",
            "La reine des questions % Toute personne répondant à une question de la reine des questions doit boire une gorgée",
            "Invente une règle% Le joueur ayant tiré la carte doit créer une règle qui s'appliquera jusqu'à la fin du jeu ou jusqu'à ce qu'un joueur annule sa règle en tirant un autre roi"};

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


    public String[] getNextCard()
    {
        int number = 0;
        String actual_card = "";
        String rules = "";
        String[] cardAndRules = new String[2];
        if(!cartes.isEmpty()) {
            do {
                number = new Random().nextInt(cartes.size());
            } while (cartes.get(number) == null);
            actual_card = cartes.get(number);
            cartes.remove(number);
            if (actual_card.substring(0, 3).equals("ace"))
                rules = textRules[0];
            else if (actual_card.substring(0, 3).equals("two"))
                rules = textRules[1];
            else if (actual_card.substring(0, 3).equals("thr"))
                rules = textRules[2];
            else if (actual_card.substring(0, 3).equals("fou"))
                rules = textRules[3];
            else if (actual_card.substring(0, 3).equals("fiv"))
                rules = textRules[4];
            else if (actual_card.substring(0, 3).equals("six"))
                rules = textRules[5];
            else if (actual_card.substring(0, 3).equals("sev"))
                rules = textRules[6];
            else if (actual_card.substring(0, 3).equals("eig"))
                rules = textRules[7];
            else if (actual_card.substring(0, 3).equals("nin"))
                rules = textRules[8];
            else if (actual_card.substring(0, 3).equals("ten"))
                rules = textRules[9];
            else if (actual_card.substring(0, 3).equals("jac"))
                rules = textRules[10];
            else if (actual_card.substring(0, 3).equals("que"))
                rules = textRules[11];
            else if (actual_card.substring(0, 3).equals("kin"))
                rules = textRules[12];
            else
                rules = "";
        }
        else
        {
            System.out.println("Fin du deck");
            actual_card = "FIN";
            rules = "Fin";
        }
        cardAndRules[0] = actual_card;
        cardAndRules[1] = rules;
        return cardAndRules;

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
