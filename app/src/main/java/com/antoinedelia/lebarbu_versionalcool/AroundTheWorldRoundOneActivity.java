package com.antoinedelia.lebarbu_versionalcool;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class AroundTheWorldRoundOneActivity extends AppCompatActivity {

    private Deck deck = null;
    private Card card;
    private ArrayList<Player> listPlayers = new ArrayList<>();
    private int numberPlayers = 0;
    private int numberActualPlayer = 0;
    private int round = 0;
    private long lastClickTime = 0;
    private static int DELAY_TIME = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_the_world_round_one_red_or_black);

        final LinearLayout linearLayoutCard = (LinearLayout) findViewById(R.id.containerImageCard);
        final LinearLayout linearLayoutRedOrBlack = (LinearLayout) findViewById(R.id.containerImageRedOrBlack);
        final LinearLayout linearLayoutMoreOrLess = (LinearLayout) findViewById(R.id.containerImageMoreOrLess);
        final LinearLayout linearLayoutBetweenOrOutside = (LinearLayout) findViewById(R.id.containerImageBetweenOrOutside);
        final LinearLayout linearLayoutSameOrDifferent = (LinearLayout) findViewById(R.id.containerImageSameOrDifferent);
        final LinearLayout linearLayoutSuitChoice = (LinearLayout) findViewById(R.id.containerImageSuitChoice);

        if(linearLayoutCard != null)
            linearLayoutCard.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        listPlayers = intent.getParcelableArrayListExtra("listPlayers");
        numberPlayers = listPlayers.size();

        for(int i = 0; i < numberPlayers; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                listPlayers.get(i).getCards().add(j, new Card(null, "unknown_card", null, null));
            }
        }

        deck = new Deck("AroundTheWorldRoundOne", this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        final ImageView imageViewCard = (ImageView) findViewById(R.id.imageViewCarte);

        if (numberPlayers != 0) {
            final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
            final String actualPlayer = getResources().getString(R.string.actualPlayer) + " " + listPlayers.get(numberActualPlayer);
            if (nameActualPlayer != null)
                nameActualPlayer.setText(actualPlayer);
        }

        //Click on card
        if (imageViewCard != null)
            imageViewCard.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(imageViewCard.getVisibility() == View.INVISIBLE)
                                return;
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            int cardsLeft = deck.getRemainingCards();
                            if (cardsLeft > 0) {
                                numberActualPlayer++;
                                if (numberActualPlayer == numberPlayers) {
                                    numberActualPlayer = 0;
                                    if(round < 4)
                                    {
                                        round++;
                                        changeRound();
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(AroundTheWorldRoundOneActivity.this);
                                        builder.setIcon(R.drawable.around_the_world_round_one);
                                        builder.setMessage(getResources().getString(R.string.gameOver)).setTitle(getResources().getString(R.string.gameOver));

                                        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                        final Dialog dialog = builder.create();
                                        dialog.show();
                                    }

                                }

                                if(round == 0)
                                {
                                    if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.INVISIBLE);
                                    if(linearLayoutRedOrBlack != null) linearLayoutRedOrBlack.setVisibility(View.VISIBLE);
                                }
                                if(round == 1)
                                {
                                    if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.INVISIBLE);
                                    if(linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(View.VISIBLE);
                                }
                                if(round == 2)
                                {
                                    if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.INVISIBLE);
                                    if(linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(View.VISIBLE);
                                }
                                if(round == 3)
                                {
                                    if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.INVISIBLE);
                                    if(linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(View.VISIBLE);
                                }
                                if(round == 4)
                                {
                                    if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.INVISIBLE);
                                    if(linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(View.VISIBLE);
                                }

                                if (numberPlayers > 0) {
                                    final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
                                    final String actualPlayer = getResources().getString(R.string.actualPlayer) + " " + listPlayers.get(numberActualPlayer);
                                    if (nameActualPlayer != null)
                                        nameActualPlayer.setText(actualPlayer);
                                }
                                checkSips();
                                refreshCards();
                            } else {
                                newGame();
                            }
                        }
                    }
            );

        final ImageView imageViewRed = (ImageView) findViewById(R.id.imageViewRed);
        //Click on red
        if (imageViewRed != null)
            imageViewRed.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewRed.getVisibility() == View.INVISIBLE)
                                return;
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutRedOrBlack != null) linearLayoutRedOrBlack.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewBlack = (ImageView) findViewById(R.id.imageViewBlack);
        //Click on black
        if (imageViewBlack != null)
            imageViewBlack.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewBlack.getVisibility() == View.INVISIBLE) {
                                return;
                            }
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutRedOrBlack != null) linearLayoutRedOrBlack.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewLess = (ImageView) findViewById(R.id.imageViewLess);
        //Click on less
        if (imageViewLess != null)
            imageViewLess.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewLess.getVisibility() == View.INVISIBLE)
                                return;
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewMore = (ImageView) findViewById(R.id.imageViewMore);
        //Click on more
        if (imageViewMore != null)
            imageViewMore.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewMore.getVisibility() == View.INVISIBLE) {
                                return;
                            }
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewBetween = (ImageView) findViewById(R.id.imageViewBetween);
        //Click on between
        if (imageViewBetween != null)
            imageViewBetween.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewBetween.getVisibility() == View.INVISIBLE)
                                return;
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewOutside = (ImageView) findViewById(R.id.imageViewOutside);
        //Click on outside
        if (imageViewOutside != null)
            imageViewOutside.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewOutside.getVisibility() == View.INVISIBLE) {
                                return;
                            }
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewSame = (ImageView) findViewById(R.id.imageViewSame);
        //Click on same
        if (imageViewSame != null)
            imageViewSame.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewSame.getVisibility() == View.INVISIBLE)
                                return;
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent .setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewDifferent = (ImageView) findViewById(R.id.imageViewDifferent);
        //Click on different
        if (imageViewDifferent != null)
            imageViewDifferent.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewDifferent.getVisibility() == View.INVISIBLE) {
                                return;
                            }
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewHearts = (ImageView) findViewById(R.id.imageViewHearts);
        //Click on hearts
        if (imageViewHearts != null)
            imageViewHearts.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewHearts.getVisibility() == View.INVISIBLE)
                                return;
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewSpades = (ImageView) findViewById(R.id.imageViewSpades);
        //Click on spades
        if (imageViewSpades != null)
            imageViewSpades.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewSpades.getVisibility() == View.INVISIBLE) {
                                return;
                            }
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewDiamonds = (ImageView) findViewById(R.id.imageViewDiamonds);
        //Click on diamonds
        if (imageViewDiamonds != null)
            imageViewDiamonds.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewDiamonds.getVisibility() == View.INVISIBLE)
                                return;
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(View.INVISIBLE);
                        }
                    }
            );

        final ImageView imageViewClubs = (ImageView) findViewById(R.id.imageViewClubs);
        //Click on clubs
        if (imageViewClubs != null)
            imageViewClubs.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < DELAY_TIME) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            card = deck.getNextCard();
                            int resourceId = AroundTheWorldRoundOneActivity.this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
                            if(imageViewCard != null) imageViewCard.setImageResource(resourceId);
                            listPlayers.get(numberActualPlayer).getCards().set(round, card);
                            refreshCards();
                            if(imageViewClubs.getVisibility() == View.INVISIBLE) {
                                return;
                            }
                            if(linearLayoutCard != null) linearLayoutCard.setVisibility(View.VISIBLE);
                            if(linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(View.INVISIBLE);
                        }
                    }
            );
    }

    public void changeRound() {
        LinearLayout linearLayoutRedOrBlack = (LinearLayout) findViewById(R.id.containerImageRedOrBlack);
        LinearLayout linearLayoutMoreOrLess = (LinearLayout) findViewById(R.id.containerImageMoreOrLess);
        LinearLayout linearLayoutBetweenOrOutside = (LinearLayout) findViewById(R.id.containerImageBetweenOrOutside);
        LinearLayout linearLayoutSameOrDifferent = (LinearLayout) findViewById(R.id.containerImageSameOrDifferent);
        LinearLayout linearLayoutSuitChoice = (LinearLayout) findViewById(R.id.containerImageSuitChoice);

        TextView textViewQuestionRound = (TextView) findViewById(R.id.questionRound);

        switch (round)
        {
            case 1:
                if(linearLayoutRedOrBlack != null) linearLayoutRedOrBlack.setVisibility(View.INVISIBLE);
                if(linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(View.VISIBLE);
                if(textViewQuestionRound != null) textViewQuestionRound.setText(getResources().getString(R.string.aroundTheWorldRoundOnePartTwo));
                final ImageView imageViewRed = (ImageView) findViewById(R.id.imageViewRed);
                final ImageView imageViewBlack = (ImageView) findViewById(R.id.imageViewBlack);
                if(imageViewRed != null) imageViewRed.setOnClickListener(null);
                if(imageViewBlack != null) imageViewBlack.setOnClickListener(null);
                return;
            case 2:
                if(linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(View.INVISIBLE);
                if(linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(View.VISIBLE);
                if(textViewQuestionRound != null) textViewQuestionRound.setText(getResources().getString(R.string.aroundTheWorldRoundOnePartThree));
                final ImageView imageViewLess = (ImageView) findViewById(R.id.imageViewLess);
                final ImageView imageViewMore = (ImageView) findViewById(R.id.imageViewMore);
                if(imageViewLess!= null) imageViewLess.setOnClickListener(null);
                if(imageViewMore != null) imageViewMore.setOnClickListener(null);
                return;
            case 3:
                if(linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(View.INVISIBLE);
                if(linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(View.VISIBLE);
                if(textViewQuestionRound != null) textViewQuestionRound.setText(getResources().getString(R.string.aroundTheWorldRoundOnePartFour));
                final ImageView imageViewBetween= (ImageView) findViewById(R.id.imageViewBetween);
                final ImageView imageViewOutside = (ImageView) findViewById(R.id.imageViewOutside);
                if(imageViewBetween != null) imageViewBetween.setOnClickListener(null);
                if(imageViewOutside != null) imageViewOutside.setOnClickListener(null);
                return;
            case 4:
                if(linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(View.INVISIBLE);
                if(linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(View.VISIBLE);
                if(textViewQuestionRound != null) textViewQuestionRound.setText(getResources().getString(R.string.aroundTheWorldRoundOnePartFive));
                final ImageView imageViewSame = (ImageView) findViewById(R.id.imageViewSame);
                final ImageView imageViewDifferent = (ImageView) findViewById(R.id.imageViewDifferent);
                if(imageViewSame != null) imageViewSame.setOnClickListener(null);
                if(imageViewDifferent != null) imageViewDifferent.setOnClickListener(null);
        }
    }

    public void refreshCards(){
        final ImageView imageViewCardOne = (ImageView) findViewById(R.id.cardOne);
        final ImageView imageViewCardTwo = (ImageView) findViewById(R.id.cardTwo);
        final ImageView imageViewCardThree = (ImageView) findViewById(R.id.cardThree);
        final ImageView imageViewCardFour = (ImageView) findViewById(R.id.cardFour);
        final ImageView imageViewCardFive = (ImageView) findViewById(R.id.cardFive);

        if(round >= 0)
        {
            int resourceId1 = this.getResources().getIdentifier("thumbnail_"+listPlayers.get(numberActualPlayer).getCards().get(0).getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
            Bitmap icon = BitmapFactory.decodeResource(getResources(), resourceId1);
            if(imageViewCardOne != null) imageViewCardOne.setImageBitmap(icon);
        }
        if(round >= 1)
        {
            int resourceId2 = this.getResources().getIdentifier("thumbnail_"+listPlayers.get(numberActualPlayer).getCards().get(1).getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
            Bitmap icon = BitmapFactory.decodeResource(getResources(), resourceId2);
            if(imageViewCardTwo != null) imageViewCardTwo.setImageBitmap(icon);
        }
        if(round >= 2)
        {
            int resourceId3 = this.getResources().getIdentifier("thumbnail_"+listPlayers.get(numberActualPlayer).getCards().get(2).getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
            Bitmap icon = BitmapFactory.decodeResource(getResources(), resourceId3);
            if(imageViewCardThree != null) imageViewCardThree.setImageBitmap(icon);
        }
        if(round >= 3)
        {
            int resourceId4 = this.getResources().getIdentifier("thumbnail_"+listPlayers.get(numberActualPlayer).getCards().get(3).getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
            Bitmap icon = BitmapFactory.decodeResource(getResources(), resourceId4);
            if(imageViewCardFour != null) imageViewCardFour.setImageBitmap(icon);
        }
        if(round >= 4)
        {
            int resourceId5 = this.getResources().getIdentifier("thumbnail_"+listPlayers.get(numberActualPlayer).getCards().get(4).getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
            Bitmap icon = BitmapFactory.decodeResource(getResources(), resourceId5);
            if(imageViewCardFive != null) imageViewCardFive.setImageBitmap(icon);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_around_the_world_round_one, menu);
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
                    builder.setIcon(R.drawable.around_the_world_round_one);
                    builder.setTitle(getResources().getString(R.string.action_players));

                    List<String> playersWithInfo = new ArrayList<>();
                    for (int i = 0; i < listPlayers.size(); i++) {
                        String textSip = getResources().getString(R.string.sip) + (listPlayers.get(i).getNumberSips() > 1 ? "s" : "");
                        String textToDisplay = listPlayers.get(i).getName() + " " + getResources().getString(R.string.drank) + " " + listPlayers.get(i).getNumberSips() + " " + textSip;
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
                    Toast.makeText(AroundTheWorldRoundOneActivity.this, getResources().getString(R.string.noPlayer), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_infoRules:

                break;
        }
        return true;
    }

    private void newGame() {
        deck = new Deck("AroundTheWorldRoundOne", this);
        card = deck.getNextCard();
        numberPlayers = listPlayers.size();
        numberActualPlayer++;
        if (numberActualPlayer == numberPlayers) {
            numberActualPlayer = 0;
            if(round < 4) {
                round++;
                changeRound();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(AroundTheWorldRoundOneActivity.this);
                builder.setIcon(R.drawable.around_the_world_round_one);
                builder.setMessage(getResources().getString(R.string.gameOver)).setTitle(getResources().getString(R.string.gameOver));
                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                final Dialog dialog = builder.create();
                dialog.show();
            }
        }
        final ImageView imageViewCard = (ImageView) findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(card.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool");
        if (imageViewCard != null)
            imageViewCard.setImageResource(resourceId);
        if (numberPlayers != 0) {
            final TextView nameActualPlayer = (TextView) findViewById(R.id.nameActualPlayer);
            final String actualPlayer = getResources().getString(R.string.actualPlayer) + " " + listPlayers.get(numberActualPlayer);
            if (nameActualPlayer != null)
                nameActualPlayer.setText(actualPlayer);
            listPlayers.get(numberActualPlayer).setNumberSips(listPlayers.get(numberActualPlayer).getNumberSips() + 1);
        }

        checkSips();
        refreshCards();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //We send back the list of the players
        intent.putParcelableArrayListExtra("listPlayers", listPlayers);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void checkSips() {
        //TODO handle win or lose
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
        }
    }
}
