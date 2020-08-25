package com.antoinedelia.lebarbu_versionalcool;

public class Card {

    private Deck.Cards name;
    private String path;
    private Rule rule;
    private Deck.SuitCards suit;

    public Card(Deck.Cards nameCard, String pathCard, Rule ruleCard, Deck.SuitCards suitCard)
    {
        name = nameCard;
        path = pathCard;
        rule = ruleCard;
        suit = suitCard;
    }

    public Deck.Cards getName() {
        return name;
    }

    public void setName(Deck.Cards name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public Rule getRule() {
        return rule;
    }

    public Deck.SuitCards getSuit(){ return suit; }

}
