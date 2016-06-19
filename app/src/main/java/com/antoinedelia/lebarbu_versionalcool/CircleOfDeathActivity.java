package com.antoinedelia.lebarbu_versionalcool;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CircleOfDeathActivity extends AppCompatActivity {

    private MenuItem item;
    private String rulesDetails;
    private Deck deck = null;
    private Card card;
    private ArrayList<Player> listPlayers = new ArrayList<>();
    private ArrayList<String> listRules = new ArrayList<>();
    private int numberPlayers = 0;
    private int numberActualPlayer = 0;
    private boolean isKingImageLoaded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_of_death);

        Intent intent = getIntent();
        listPlayers = intent.getParcelableArrayListExtra("listPlayers");
        numberPlayers = listPlayers.size();

        deck = new Deck("CircleOfDeath", this);
        card = deck.getNextCard();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        final ImageView imageViewCard = (ImageView) findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        if(imageViewCard != null)
        imageViewCard.setImageResource(resourceId);

        final TextView textViewRules = (TextView) findViewById(R.id.textViewRules);
        if(textViewRules != null)
        textViewRules.setText(card.getRule().getSmallRule());
        rulesDetails = card.getRule().getLongRule();

        if (numberPlayers != 0) {
            final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
            final String actualPlayer = getResources().getString(R.string.currentPlayer) + " " + listPlayers.get(numberActualPlayer);
            if(nameActualPlayer != null)
            nameActualPlayer.setText(actualPlayer);
            for (int i = 0; i < listPlayers.size(); i++)
                listPlayers.get(i).setSpecialTrait("");
        }
        checkSipsAndSpecial();

        //Click on rule
        LinearLayout linearLayoutRules = (LinearLayout) findViewById(R.id.containerRules);
        if(linearLayoutRules != null)
        linearLayoutRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CircleOfDeathActivity.this);
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

        card = deck.getNextCard();

        //Click on card
        if(imageViewCard != null)
        imageViewCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isKingImageLoaded)
                            return;
                        int cardsLeft = deck.getRemainingCards();
                        if (cardsLeft > 0) {
                            numberActualPlayer++;
                            if (numberActualPlayer == numberPlayers) {
                                numberActualPlayer = 0;
                            }

                            card = deck.getNextCard();
                            int resourceId = CircleOfDeathActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            imageViewCard.setImageResource(resourceId);

                            if(textViewRules != null)
                            textViewRules.setText(card.getRule().getSmallRule());
                            rulesDetails = card.getRule().getLongRule();

                            int remainingCards = deck.getRemainingCards();
                            String cards = getResources().getString(R.string.card) + (remainingCards > 1 ? "s" : "");
                            item.setTitle(String.valueOf(remainingCards) + " " + cards);

                            if (numberPlayers > 0) {
                                final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
                                final String actualPlayer = getResources().getString(R.string.currentPlayer) + " " + listPlayers.get(numberActualPlayer);
                                if(nameActualPlayer != null)
                                nameActualPlayer.setText(actualPlayer);
                            }
                            checkSipsAndSpecial();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CircleOfDeathActivity.this);
                            builder.setMessage(getResources().getString(R.string.restartGame))
                                    .setTitle(getResources().getString(R.string.gameOver));

                            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.putParcelableArrayListExtra("listPlayers", listPlayers);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });

                            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    imageViewCard.clearAnimation();
                                    if(textViewRules != null)
                                    textViewRules.clearAnimation();
                                    newGame();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_circle_of_death, menu);
        item = menu.findItem(R.id.action_cardsRemaining);
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
                intent.putParcelableArrayListExtra("listPlayers", listPlayers);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.action_infoPlayers:
                if (numberPlayers != 0) {
                    //We show the information about the players
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.circle_of_fire);
                    builder.setTitle(getResources().getString(R.string.action_players));

                    List<String> playersWithInfo = new ArrayList<>();
                    for (int i = 0; i < listPlayers.size(); i++) {
                        String textSip = getResources().getString(R.string.sip) + (listPlayers.get(i).getNumberSips() > 1 ? "s" : "");
                        String textSpecialTrait = ((listPlayers.get(i).getSpecialTrait().isEmpty() || listPlayers.get(i).getSpecialTrait().equals(" ")) ? "" : " (" + listPlayers.get(i).getSpecialTrait().trim() + ")");
                        String textToDisplay = listPlayers.get(i).getName() + " " + getResources().getString(R.string.drank) + " " + listPlayers.get(i).getNumberSips() + " " + textSip + textSpecialTrait;
                        playersWithInfo.add(textToDisplay.trim());
                    }
                    ListView playersList = new ListView(this);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, playersWithInfo);
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
                    Toast.makeText(CircleOfDeathActivity.this, getResources().getString(R.string.noPlayer), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_infoRules:
                if (listRules.size() != 0) {
                    //We show the information about the players
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.circle_of_fire);
                    builder.setTitle(getResources().getString(R.string.action_rules));

                    ListView rulesList = new ListView(this);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listRules);
                    rulesList.setAdapter(arrayAdapter);

                    builder.setView(rulesList);
                    builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    final Dialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(CircleOfDeathActivity.this, getResources().getString(R.string.noRule), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    private void newGame() {
        deck = new Deck("CircleOfDeath", this);
        numberPlayers = listPlayers.size();
        numberActualPlayer = 0;
        final ImageView imageViewCard = (ImageView) findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        if(imageViewCard != null)
        imageViewCard.setImageResource(resourceId);
        final TextView textViewRules = (TextView) findViewById(R.id.textViewRules);
        if(textViewRules != null)
        textViewRules.setText(card.getRule().getSmallRule());
        rulesDetails = card.getRule().getLongRule();
        card = deck.getNextCard();
        int remainingCards = deck.getRemainingCards();
        String cards = getResources().getString(R.string.card) + (remainingCards > 1 ? "s" : "");
        item.setTitle(String.valueOf(remainingCards) + " " + cards);
        if (numberPlayers != 0) {
            final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
            final String actualPlayer = getResources().getString(R.string.currentPlayer) + " " + listPlayers.get(numberActualPlayer);
            if(nameActualPlayer != null)
            nameActualPlayer.setText(actualPlayer);
            listPlayers.get(numberActualPlayer).setNumberSips(listPlayers.get(numberActualPlayer).getNumberSips() + 1);
            for (int i = 0; i < listPlayers.size(); i++)
                listPlayers.get(i).setSpecialTrait("");
        }
        checkSipsAndSpecial();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //We send back the list of the players
        intent.putParcelableArrayListExtra("listPlayers", listPlayers);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void checkSipsAndSpecial() {
        //Check if ACE to give all player one sip
        if (numberPlayers > 0) {
            if (card.getName() == Deck.Cards.ACE) {
                for (int i = 0; i < listPlayers.size(); i++)
                    listPlayers.get(i).setNumberSips(listPlayers.get(i).getNumberSips() + 1);
            }
            //Check cards to give the current player the according number of sips
            if (card.getName() == Deck.Cards.TWO || card.getName() == Deck.Cards.THREE || card.getName() == Deck.Cards.FOUR || card.getName() == Deck.Cards.FIVE || card.getName() == Deck.Cards.SIX) {
                listPlayers.get(numberActualPlayer).setNumberSips(listPlayers.get(numberActualPlayer).getNumberSips() + card.getName().getNumVal() + 1);
            }
            //Check if JACK to give player the Snake eyes trait
            if (card.getName() == Deck.Cards.JACK) {
                for (int i = 0; i < listPlayers.size(); i++)
                    if (listPlayers.get(i).getSpecialTrait().contains(getResources().getStringArray(R.array.rulesSmallCircleOfDeath)[Deck.Cards.JACK.getNumVal()])) {
                        listPlayers.get(i).setSpecialTrait(listPlayers.get(i).getSpecialTrait().replace(getResources().getStringArray(R.array.rulesSmallCircleOfDeath)[Deck.Cards.JACK.getNumVal()], ""));
                        listPlayers.get(i).setSpecialTrait(listPlayers.get(i).getSpecialTrait().trim().replaceAll("[^A-Za-z ']+", ""));
                    }
                listPlayers.get(numberActualPlayer).setSpecialTrait((listPlayers.get(numberActualPlayer).getSpecialTrait().equals("") ? "" : listPlayers.get(numberActualPlayer).getSpecialTrait() + " / ") + getResources().getStringArray(R.array.rulesSmallCircleOfDeath)[Deck.Cards.JACK.getNumVal()]);
            }
            //Check if QUEEN to give player the Question's queen trait
            if (card.getName() == Deck.Cards.QUEEN) {
                for (int i = 0; i < listPlayers.size(); i++)
                    if (listPlayers.get(i).getSpecialTrait().contains(getResources().getStringArray(R.array.rulesSmallCircleOfDeath)[Deck.Cards.QUEEN.getNumVal()])) {
                        listPlayers.get(i).setSpecialTrait(listPlayers.get(i).getSpecialTrait().replace(getResources().getStringArray(R.array.rulesSmallCircleOfDeath)[Deck.Cards.QUEEN.getNumVal()], ""));
                        listPlayers.get(i).setSpecialTrait(listPlayers.get(i).getSpecialTrait().trim().replaceAll("[^A-Za-z ']+", ""));
                    }
                listPlayers.get(numberActualPlayer).setSpecialTrait((listPlayers.get(numberActualPlayer).getSpecialTrait().equals("") ? "" : listPlayers.get(numberActualPlayer).getSpecialTrait() + " / ") + getResources().getStringArray(R.array.rulesSmallCircleOfDeath)[Deck.Cards.QUEEN.getNumVal()]);
            }
        }

        //Check if KING to add a new rule
        if (card.getName() == Deck.Cards.KING) {
            isKingImageLoaded = false;
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.helpRuleCircleOfDeath));
            builder.setTitle(getResources().getString(R.string.addRule));
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            final android.app.AlertDialog dialog = builder.create();
            input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                }
            });

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog = (input.getText().toString().trim().isEmpty());
                    // if EditText is empty disable closing on positive button
                    if (!wantToCloseDialog) {
                        listRules.add(input.getText().toString().trim());
                        dialog.dismiss();
                        isKingImageLoaded = true;
                    }
                }
            });
        }
    }
}
