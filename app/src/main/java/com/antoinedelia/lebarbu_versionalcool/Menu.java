package com.antoinedelia.lebarbu_versionalcool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private ArrayList<String> listPlayers = new ArrayList<>();
    private MenuItem item;

    //Create the menu with all the games
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        final ImageView imageBarbu = (ImageView) findViewById(R.id.imageBarbu);

        imageBarbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Barbu.class);
                intent.putExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 0);
            }
        });

        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Barbu.class);
                intent.putExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.action_addPlayers);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addPlayers:
                Intent intent = new Intent(Menu.this, Players.class);
                //We pass the list of the players (if there is one)
                intent.putExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 1);
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 || requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //We get the list of the players
                listPlayers = data.getStringArrayListExtra("listPlayers");
            }
        }
    }


}
