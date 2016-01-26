package com.antoinedelia.lebarbu_versionalcool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by Antoine on 20/01/2016.
 */
public class Rules extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Intent intent = getIntent();
        TextView textViewRulesDetails = (TextView) findViewById(R.id.textViewRulesDetails);
        textViewRulesDetails.setText(intent.getStringExtra("textRulesDetails"));

        int width =  displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int) (height*0.8));
    }
}
