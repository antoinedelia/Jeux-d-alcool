package com.antoinedelia.lebarbu_versionalcool;

public class Card {

    private Deck.Cards name;
    private String path;
    private Rule rule;

    public Card(Deck.Cards nameCard, String pathCard, Rule ruleCard)
    {
        name = nameCard;
        path = pathCard;
        rule = ruleCard;
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

    public void setPath(String path) {
        this.path = path;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }


}
