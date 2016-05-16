package com.antoinedelia.lebarbu_versionalcool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class Menu extends AppCompatActivity {
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
                startActivityForResult(intent, 0);
            }
        });

        imagePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Players.class);
                startActivityForResult(intent, 0);
            }
        });
    }


}
