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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Deck deck = new Deck();
        String actualCard = deck.getNextCard();
        int remainingCards = deck.getRemainingCards();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout linearLayoutRules = (LinearLayout) findViewById(R.id.containerRules);
        //TextView textView = (TextView) findViewById(R.id.textViewRules);
        linearLayoutRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Rules.class));
            }
        });

        final ImageView imageViewCarte = (ImageView)findViewById(R.id.imageViewCarte);
        int resourceId = this.getResources().getIdentifier(actualCard, "drawable", "com.antoinedelia.lebarbu_versionalcool");
        imageViewCarte.setImageResource(resourceId);
        imageViewCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actualCard = deck.getNextCard();
                int resourceId = MainActivity.this.getResources().getIdentifier(actualCard, "drawable", "com.antoinedelia.lebarbu_versionalcool");
                imageViewCarte.setImageResource(resourceId);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
