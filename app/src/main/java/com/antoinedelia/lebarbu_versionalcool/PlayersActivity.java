package com.antoinedelia.lebarbu_versionalcool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayersActivity extends AppCompatActivity {

    private ArrayList<Player> listPlayers = new ArrayList<>();
    private ArrayAdapter<Player> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);

        ListView listview = (ListView) findViewById(R.id.listView);

        //We get the list of the players (if there is one)
        Intent intent = getIntent();
        listPlayers = intent.getParcelableArrayListExtra("listPlayers");

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listPlayers);
        if (listview != null) {
            listview.setAdapter(adapter);

            //Delete player
            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayersActivity.this);
                    builder.setTitle(getResources().getString(R.string.delete) + " " + listPlayers.get(position).getName() + "?");

                    // Set up the buttons
                    builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listPlayers.remove(position);
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
                    dialog.show();
                    return true;
                }
            });

            //Rename player
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayersActivity.this);
                    builder.setTitle(getResources().getString(R.string.rename));
                    final EditText input = new EditText(PlayersActivity.this);
                    input.setText(listPlayers.get(position).getName());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setSelection(input.getText().length());
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Player player = new Player(input.getText().toString());
                            //If name is blank, remove it
                            if (!player.getName().isEmpty())
                                listPlayers.set(position, player);
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
            });
        }
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
                Player player = new Player(input.getText().toString());
                //Check if player's name isn't empty
                if (!player.getName().isEmpty()) {
                    listPlayers.add(player);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(PlayersActivity.this, getResources().getString(R.string.playerEmpty), Toast.LENGTH_SHORT).show();
                }
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //We send back the list of the players
        intent.putParcelableArrayListExtra("listPlayers", listPlayers);
        setResult(RESULT_OK, intent);
        finish();
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
        }
        return true;
    }
}
