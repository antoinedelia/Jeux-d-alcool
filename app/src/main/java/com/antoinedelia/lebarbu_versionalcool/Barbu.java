package com.antoinedelia.lebarbu_versionalcool;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.AbstractMap;
import java.util.ArrayList;

public class Barbu extends AppCompatActivity {

    private Menu menu;
    private MenuItem item;
    private MenuItem retry;
    private String rulesDetails;
    private Deck deck = null;
    private AbstractMap.SimpleEntry<String, Deck.Cards> card;
    private String rules;
    private String[] textRules;
    private ArrayList<String> listPlayers = new ArrayList<>();
    private int numberPlayers = 0;
    private int numberActualPlayer = 0;
    //List with details (number of sips...)
    private ArrayList<String> listPlayersWithDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barbu);
        //Change language (dev only)
        //LocaleHelper.setLocale(this, "en");

        Intent intent = getIntent();
        listPlayers = intent.getStringArrayListExtra("listPlayers");
        numberPlayers = listPlayers.size();
        if (numberPlayers != 0) {
            final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
            final String actualPlayer = getResources().getString(R.string.actualPlayer) + " " + listPlayers.get(numberActualPlayer);
            nameActualPlayer.setText(actualPlayer);
        }

        listPlayersWithDetails = listPlayers;
        deck = new Deck();
        card = deck.getNextCard();
        textRules = getResources().getStringArray(R.array.rules);
        rules = textRules[card.getValue().getNumVal()];

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        LinearLayout linearLayoutRules = (LinearLayout) findViewById(R.id.containerRules);
        //TextView textView = (TextView) findViewById(R.id.textViewRules);


        final ImageView imageViewCard = (ImageView) findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(card.getKey(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        imageViewCard.setImageResource(resourceId);

        final TextView textViewRules = (TextView) findViewById(R.id.textViewRules);
        textViewRules.setText(rules.substring(0, rules.lastIndexOf("%")));
        rulesDetails = rules.substring(rules.lastIndexOf("%") + 2, rules.length());

        linearLayoutRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Barbu.this);
                builder.setMessage(rulesDetails)
                        .setTitle(getResources().getString(R.string.ruleCard));

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        imageViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cardsLeft = deck.getRemainingCards();
                if (cardsLeft > 0) {
                    numberActualPlayer++;
                    if (numberActualPlayer == numberPlayers) {
                        numberActualPlayer = 0;
                    }
                    if (numberPlayers != 0) {
                        final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
                        final String actualPlayer = getResources().getString(R.string.actualPlayer) + " " + listPlayers.get(numberActualPlayer);
                        nameActualPlayer.setText(actualPlayer);
                    }

                    card = deck.getNextCard();
                    rules = textRules[card.getValue().getNumVal()];
                    int resourceId = Barbu.this.getResources().getIdentifier(card.getKey(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                    imageViewCard.setImageResource(resourceId);

                    textViewRules.setText(rules.substring(0, rules.lastIndexOf("%")));
                    rulesDetails = rules.substring(rules.lastIndexOf("%") + 2, rules.length());

                    int remainingCards = deck.getRemainingCards();
                    String cards = getResources().getString(R.string.card) + (remainingCards > 1 ? "s" : "");
                    item.setTitle(String.valueOf(remainingCards) + " " + cards);
                } else {
                    //TODO End game + show scoreboard via Alert Dialog
                    imageViewCard.clearAnimation();
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
        getMenuInflater().inflate(R.menu.menu_barbu, menu);
        item = menu.findItem(R.id.action_cardsRemaining);
        retry = menu.findItem(R.id.action_retry);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_retry:
                newGame();
                break;
            case android.R.id.home:
                Intent intent = new Intent();
                //We send back the list of the players
                intent.putExtra("listPlayers", listPlayers);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.action_infoPlayers:
                if (numberPlayers != 0) {
                    //We show the information about the players
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.barbu);
                    builder.setTitle(getResources().getString(R.string.action_players));

                    ListView playersList = new ListView(this);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listPlayers);
                    playersList.setAdapter(arrayAdapter);

                    builder.setView(playersList);
                    builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    final Dialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(Barbu.this, getResources().getString(R.string.noPlayer), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    private void newGame() {
        deck = new Deck();
        card = deck.getNextCard();
        rules = textRules[card.getValue().getNumVal()];
        numberPlayers = listPlayers.size();
        numberActualPlayer = 0;
        if (numberPlayers != 0) {
            final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
            final String actualPlayer = getResources().getString(R.string.actualPlayer) + " " + listPlayers.get(numberActualPlayer);
            nameActualPlayer.setText(actualPlayer);
        }
        listPlayersWithDetails = listPlayers;
        final ImageView imageViewCard = (ImageView) findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(card.getKey(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        imageViewCard.setImageResource(resourceId);
        final TextView textViewRules = (TextView) findViewById(R.id.textViewRules);
        textViewRules.setText(rules.substring(0, rules.lastIndexOf("%")));
        rulesDetails = rules.substring(rules.lastIndexOf("%") + 2, rules.length());
        int remainingCards = deck.getRemainingCards();
        String cards = getResources().getString(R.string.card) + (remainingCards > 1 ? "s" : "");
        item.setTitle(String.valueOf(remainingCards) + " " + cards);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //We send back the list of the players
        intent.putExtra("listPlayers", listPlayers);
        setResult(RESULT_OK, intent);
        finish();
    }
}
