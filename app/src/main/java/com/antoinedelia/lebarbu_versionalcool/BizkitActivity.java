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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BizkitActivity extends AppCompatActivity {

    private ArrayList<Player> listPlayers = new ArrayList<>();
    private ArrayList<String> listRules = new ArrayList<>();
    private String rulesDetails;
    private Dice diceOne;
    private Dice diceTwo;
    private int numberPlayers = 0;
    private int numberActualPlayer = 0;
    private boolean isTwelve = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bizkit);

        Intent intent = getIntent();
        listPlayers = intent.getParcelableArrayListExtra("listPlayers");
        numberPlayers = listPlayers.size();

        for (int i = 0; i < listPlayers.size(); i++)
            listPlayers.get(i).setSpecialTrait("");

        diceOne = new Dice();
        diceTwo = new Dice();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        final ImageView imageViewDiceOne = (ImageView) findViewById(R.id.imageViewDiceOne);
        int resourceIdOne = this.getResources().getIdentifier(diceOne.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        if(imageViewDiceOne != null)
        imageViewDiceOne.setImageResource(resourceIdOne);

        final ImageView imageViewDiceTwo = (ImageView) findViewById(R.id.imageViewDiceTwo);
        int resourceIdTwo = this.getResources().getIdentifier(diceTwo.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        if(imageViewDiceTwo != null)
        imageViewDiceTwo.setImageResource(resourceIdTwo);

        final TextView textViewRules = (TextView) findViewById(R.id.textViewRules);
        if(textViewRules != null)
        textViewRules.setText(getResources().getStringArray(R.array.rulesSmallBizkit)[(diceOne.getValue() + diceTwo.getValue()) - 2]);
        rulesDetails = getResources().getStringArray(R.array.rulesLongBizkit)[(diceOne.getValue() + diceTwo.getValue()) - 2];

        if (numberPlayers != 0) {
            final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
            final String actualPlayer = getResources().getString(R.string.actualPlayer) + " " + listPlayers.get(numberActualPlayer);
            if(nameActualPlayer != null)
                nameActualPlayer.setText(actualPlayer);
            for (int i = 0; i < listPlayers.size(); i++)
                listPlayers.get(i).setSpecialTrait("");
        }
        checkSipsAndSpecial();

        //Click on rule
        LinearLayout linearLayoutRules = (LinearLayout) findViewById(R.id.containerRules);
        if (linearLayoutRules != null)
            linearLayoutRules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String doubleText = " ";
                    if (diceOne.getValue() == diceTwo.getValue())
                        doubleText += getResources().getString(R.string.doubleText) + " " + diceOne.getValue() + " " + getResources().getString(R.string.sip) + (diceOne.getValue() != 1 ? "s." : ".");
                    AlertDialog.Builder builder = new AlertDialog.Builder(BizkitActivity.this);
                    builder.setMessage(rulesDetails + doubleText)
                            .setTitle(getResources().getString(R.string.ruleDice));

                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        //Click on the dice
        final RelativeLayout relativeLayoutDice = (RelativeLayout) findViewById(R.id.containerImageDice);
        if (relativeLayoutDice != null)
            relativeLayoutDice.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!isTwelve) {
                                return;
                            }
                            numberActualPlayer++;
                            if (numberActualPlayer == numberPlayers) {
                                numberActualPlayer = 0;
                            }

                            diceOne.roll();
                            diceTwo.roll();
                            int resourceIdOne = BizkitActivity.this.getResources().getIdentifier(diceOne.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewDiceOne != null)
                            imageViewDiceOne.setImageResource(resourceIdOne);
                            int resourceIdTwo = BizkitActivity.this.getResources().getIdentifier(diceTwo.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewDiceTwo != null)
                            imageViewDiceTwo.setImageResource(resourceIdTwo);

                            if(textViewRules != null)
                            textViewRules.setText(getResources().getStringArray(R.array.rulesSmallBizkit)[(diceOne.getValue() + diceTwo.getValue()) - 2]);
                            rulesDetails = getResources().getStringArray(R.array.rulesLongBizkit)[(diceOne.getValue() + diceTwo.getValue()) - 2];

                            if (numberPlayers > 0) {
                                final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
                                final String actualPlayer = getResources().getString(R.string.actualPlayer) + " " + listPlayers.get(numberActualPlayer);
                                if (nameActualPlayer != null)
                                    nameActualPlayer.setText(actualPlayer);
                            }
                            checkSipsAndSpecial();
                        }
                    }
            );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bizkit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                    builder.setIcon(R.drawable.dice);
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
                    Toast.makeText(BizkitActivity.this, getResources().getString(R.string.noPlayer), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_infoRules:
                if (listRules.size() != 0) {
                    //We show the information about the players
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.dice);
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
                    Toast.makeText(BizkitActivity.this, getResources().getString(R.string.noRule), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    //TODO for later, give player sip (the actual player choose)
    public void checkSipsAndSpecial() {
        int sumOfDice = diceOne.getValue() + diceTwo.getValue();

        if (numberPlayers > 0) {
            //Player drinks
            if (sumOfDice == 5 || sumOfDice == 10)
                listPlayers.get(numberActualPlayer).setNumberSips(listPlayers.get(numberActualPlayer).getNumberSips() + 1);
            //Previous player drinks
            if (sumOfDice == 4 || sumOfDice == 9) {
                if (numberActualPlayer > 0)
                    listPlayers.get(numberActualPlayer - 1).setNumberSips(listPlayers.get(numberActualPlayer - 1).getNumberSips() + 1);
                else
                    listPlayers.get(listPlayers.size() - 1).setNumberSips(listPlayers.get(listPlayers.size() - 1).getNumberSips() + 1);
            }
            //Next player drinks
            if (sumOfDice == 6 || sumOfDice == 11) {
                if (numberActualPlayer < listPlayers.size() - 1)
                    listPlayers.get(numberActualPlayer + 1).setNumberSips(listPlayers.get(numberActualPlayer + 1).getNumberSips() + 1);
                else
                    listPlayers.get(0).setNumberSips(listPlayers.get(0).getNumberSips() + 1);
            }
            //Everyone drinks
            if (sumOfDice == 8)
                for (int i = 0; i < listPlayers.size(); i++)
                    listPlayers.get(i).setNumberSips(listPlayers.get(i).getNumberSips() + 1);
            //Check if three to give player the Biscuit trait
            if (sumOfDice == 3) {
                for (int i = 0; i < listPlayers.size(); i++)
                    if (listPlayers.get(i).getSpecialTrait().contains(getResources().getStringArray(R.array.rulesSmallBizkit)[1])) {
                        listPlayers.get(i).setSpecialTrait(listPlayers.get(i).getSpecialTrait().replace(getResources().getStringArray(R.array.rulesSmallBizkit)[1], ""));
                        listPlayers.get(i).setSpecialTrait(listPlayers.get(i).getSpecialTrait().trim().replaceAll("[^A-Za-z ']+", ""));
                    }
                listPlayers.get(numberActualPlayer).setSpecialTrait((listPlayers.get(numberActualPlayer).getSpecialTrait().equals("") ? "" : listPlayers.get(numberActualPlayer).getSpecialTrait() + " / ") + getResources().getStringArray(R.array.rulesSmallBizkit)[1]);
                listPlayers.get(numberActualPlayer).setNumberSips(listPlayers.get(numberActualPlayer).getNumberSips() + 1);
            }
            //Check if a dice is three
            if (diceOne.getValue() == 3 || diceTwo.getValue() == 3) {
                for (int i = 0; i < listPlayers.size(); i++)
                    if (listPlayers.get(i).getSpecialTrait().contains(getResources().getStringArray(R.array.rulesSmallBizkit)[1])) {
                        listPlayers.get(i).setNumberSips(listPlayers.get(i).getNumberSips() + ((diceOne.getValue() == 3 && diceTwo.getValue() == 3) ? 2 : 1));
                    }
            }
        }

        //Check if twelve to add a new rule and and Great Master
        if (sumOfDice == 12) {
            isTwelve = false;
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.helpRuleBizkit));
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
                        isTwelve = true;
                        if (!listPlayers.isEmpty()) {
                            for (int i = 0; i < listPlayers.size(); i++)
                                if (listPlayers.get(i).getSpecialTrait().contains(getResources().getStringArray(R.array.rulesSmallBizkit)[10])) {
                                    listPlayers.get(i).setSpecialTrait(listPlayers.get(i).getSpecialTrait().replace(getResources().getStringArray(R.array.rulesSmallBizkit)[10], ""));
                                    listPlayers.get(i).setSpecialTrait(listPlayers.get(i).getSpecialTrait().trim().replaceAll("[^A-Za-z ']+", ""));
                                }
                            listPlayers.get(numberActualPlayer).setSpecialTrait((listPlayers.get(numberActualPlayer).getSpecialTrait().equals("") ? "" : listPlayers.get(numberActualPlayer).getSpecialTrait() + " / ") + getResources().getStringArray(R.array.rulesSmallBizkit)[10]);
                        }
                    }
                }
            });
        }
    }
}
