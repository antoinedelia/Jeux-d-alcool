package com.antoinedelia.lebarbu_versionalcool;

import android.content.Context;

public class RuleBarbu implements Rule{

    private String smallRule;
    private String longRule;


    public RuleBarbu(Deck.Cards typeCard, Context context)
    {
        this.smallRule = context.getResources().getStringArray(R.array.rulesSmallBarbu)[typeCard.getNumVal()];
        this.longRule = context.getResources().getStringArray(R.array.rulesLongBarbu)[typeCard.getNumVal()];
    }

    @Override
    public String getSmallRule() {
        return smallRule;
    }

    @Override
    public String getLongRule() {
        return longRule;
    }
}
