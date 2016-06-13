package com.antoinedelia.lebarbu_versionalcool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ArrayList<Player> listPlayers = new ArrayList<>();

    //Create the menu with all the games
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(MenuActivity.this, TutorialActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();

        final ImageView imageCircleOfDeath = (ImageView) findViewById(R.id.imageCircleOfDeath);
        if(imageCircleOfDeath != null)
        imageCircleOfDeath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CircleOfDeathActivity.class);
                intent.putParcelableArrayListExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 0);
            }
        });

        final ImageView imageBizkit = (ImageView) findViewById(R.id.imageBizkit);
        if(imageBizkit != null)
        imageBizkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BizkitActivity.class);
                intent.putParcelableArrayListExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 0);
            }
        });

        final TextView textViewCircleOfDeath = (TextView) findViewById(R.id.textViewCircleOfDeath);
        if(textViewCircleOfDeath != null)
        textViewCircleOfDeath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CircleOfDeathActivity.class);
                intent.putParcelableArrayListExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 0);
            }
        });

        final TextView textViewBizkit = (TextView) findViewById(R.id.textViewBizkit);
        if(textViewBizkit != null)
        textViewBizkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BizkitActivity.class);
                intent.putParcelableArrayListExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addPlayers:
                Intent intent = new Intent(MenuActivity.this, PlayersActivity.class);
                //We pass the list of the players (if there is one)
                intent.putParcelableArrayListExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 1);
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 || requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //We get the list of the players
                listPlayers = data.getParcelableArrayListExtra("listPlayers");
            }
        }
    }


}
