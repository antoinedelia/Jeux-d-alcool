package com.antoinedelia.lebarbu_versionalcool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.AbstractMap;

public class Barbu extends AppCompatActivity {

    private Menu menu;
    private MenuItem item;
    private MenuItem retry;
    private String rulesDetails;
    private Deck deck = null;
    private AbstractMap.SimpleEntry<String, Deck.Cards> card;
    private String rules;
    private String[] textRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barbu);
        //Change language (dev only)
        //LocaleHelper.setLocale(this, "en");
        deck = new Deck();
        card = deck.getNextCard();
        textRules = getResources().getStringArray(R.array.rules);
        rules = textRules[card.getValue().getNumVal()];

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout linearLayoutRules = (LinearLayout) findViewById(R.id.containerRules);
        //TextView textView = (TextView) findViewById(R.id.textViewRules);


        final ImageView imageViewCarte = (ImageView)findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(card.getKey(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        imageViewCarte.setImageResource(resourceId);

        final TextView textViewRules = (TextView) findViewById(R.id.textViewRules);
        textViewRules.setText(rules.substring(0, rules.lastIndexOf("%")));
        rulesDetails = rules.substring(rules.lastIndexOf("%")+2, rules.length());

        linearLayoutRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barbu.this, Rules.class);
                intent.putExtra("textRulesDetails", rulesDetails);
                startActivity(intent);
            }
        });

        imageViewCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cardsLeft = deck.getRemainingCards();
                if(cardsLeft > 0)
                {
                    card = deck.getNextCard();
                    //TODO End game if card == null
                    rules = textRules[card.getValue().getNumVal()];
                    int resourceId = Barbu.this.getResources().getIdentifier(card.getKey(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                    imageViewCarte.setImageResource(resourceId);

                    textViewRules.setText(rules.substring(0, rules.lastIndexOf("%")));
                    rulesDetails = rules.substring(rules.lastIndexOf("%")+2, rules.length());

                    int remainingCards = deck.getRemainingCards();
                    String cards = remainingCards>1 ? " cards" : " card";
                    item.setTitle(String.valueOf(remainingCards) + cards);
                }
                else
                {
                    imageViewCarte.clearAnimation();
                    textViewRules.clearAnimation();
                    newGame();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.action_cardsRemaining);
        retry = menu.findItem(R.id.action_retry);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_retry) {
            newGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void newGame() {
        deck = new Deck();
        card = deck.getNextCard();
        rules = textRules[card.getValue().getNumVal()];
        final ImageView imageViewCarte = (ImageView)findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(card.getKey(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        imageViewCarte.setImageResource(resourceId);
        final TextView textViewRules = (TextView) findViewById(R.id.textViewRules);
        textViewRules.setText(rules.substring(0, rules.lastIndexOf("%")));
        rulesDetails = rules.substring(rules.lastIndexOf("%")+2, rules.length());
        int remainingCards = deck.getRemainingCards();
        String cards = remainingCards>1 ? " cards" : " card";
        item.setTitle(String.valueOf(remainingCards) + cards);
    }

}
