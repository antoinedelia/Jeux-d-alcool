package com.antoinedelia.lebarbu_versionalcool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private ArrayList<String> listPlayers = new ArrayList<>();
    //Create the menu with all the games
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        final ImageView imageBarbu = (ImageView)findViewById(R.id.imageBarbu);
        final ImageView imagePlayers = (ImageView)findViewById(R.id.imagePlayers);

        imageBarbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Barbu.class);
                intent.putExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 0);
            }
        });

        imagePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Players.class);
                //We pass the list of the players (if there is one)
                intent.putExtra("listPlayers", listPlayers);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 || requestCode == 1) {
            if(resultCode == RESULT_OK){
                //We get the list of the players
                listPlayers = data.getStringArrayListExtra("listPlayers");
            }
        }
    }


}
