package com.antoinedelia.lebarbu_versionalcool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Menu menu;
    private MenuItem item;
    private String rulesDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Deck deck = new Deck();
        String[] cardAndRules = deck.getNextCard();
        //int remainingCards = deck.getRemainingCards();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout linearLayoutRules = (LinearLayout) findViewById(R.id.containerRules);
        //TextView textView = (TextView) findViewById(R.id.textViewRules);


        final ImageView imageViewCarte = (ImageView)findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(cardAndRules[0], "drawable", "com.antoinedelia.lebarbu_versionalcool");
        imageViewCarte.setImageResource(resourceId);

        final TextView textViewRules = (TextView) findViewById(R.id.textViewRules);
        textViewRules.setText(cardAndRules[1].substring(0, cardAndRules[1].lastIndexOf("%")));

//        final TextView textViewRulesDetails = (TextView) findViewById(R.id.textViewRulesDetails);
        rulesDetails = cardAndRules[1].substring(cardAndRules[1].lastIndexOf("%")+2, cardAndRules[1].length());
//        textViewRulesDetails.setText(test);

        linearLayoutRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Rules.class);
                intent.putExtra("textRulesDetails", rulesDetails);
                startActivity(intent);
            }
        });

        imageViewCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] cardAndRules = deck.getNextCard();
                int resourceId = MainActivity.this.getResources().getIdentifier(cardAndRules[0], "drawable", "com.antoinedelia.lebarbu_versionalcool");
                imageViewCarte.setImageResource(resourceId);

                textViewRules.setText(cardAndRules[1].substring(0, cardAndRules[1].lastIndexOf("%")));
                rulesDetails = cardAndRules[1].substring(cardAndRules[1].lastIndexOf("%")+2, cardAndRules[1].length());

                int remainingCards = deck.getRemainingCards();
                String cards = remainingCards>1 ? " cards" : " card";
                item.setTitle(String.valueOf(remainingCards) + cards);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.action_cardsRemaining);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
