package com.antoinedelia.lebarbu_versionalcool;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class Players extends ListActivity {

    //TODO save players list in application
    private ArrayList<String> listPlayers = new ArrayList<>();
    private int i = 1;
    private String nomJoueur;
    private ArrayAdapter<String> adapter;

    //TODO Add delete/rename button to delete/rename a player
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.players);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listPlayers);
        setListAdapter(adapter);
    }

    //TODO Add the action bar
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void addPlayer(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.addPlayer));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nomJoueur = input.getText().toString();
                listPlayers.add(getResources().getString(R.string.player) + " " + i++ + " : " + nomJoueur);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.create();
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();
    }
}
