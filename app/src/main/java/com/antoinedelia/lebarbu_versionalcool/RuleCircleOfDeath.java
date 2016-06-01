package com.antoinedelia.lebarbu_versionalcool;

import android.content.Context;

public class RuleCircleOfDeath implements Rule{

    private String smallRule;
    private String longRule;


    public RuleCircleOfDeath(Deck.Cards typeCard, Context context)
    {
        this.smallRule = context.getResources().getStringArray(R.array.rulesSmallCircleOfDeath)[typeCard.getNumVal()];
        this.longRule = context.getResources().getStringArray(R.array.rulesLongCircleOfDeath)[typeCard.getNumVal()];
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
