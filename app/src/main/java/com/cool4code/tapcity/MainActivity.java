package com.cool4code.tapcity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    String PLATE;
    EditText  taxi_plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFAA00")));
        int titleId;
        int textColor = getResources().getColor(android.R.color.white);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            TextView abTitle = (TextView) findViewById(titleId);
            abTitle.setTextColor(textColor);
        } else {
            TextView abTitle = (TextView) getWindow().findViewById(android.R.id.title);
            abTitle.setTextColor(textColor);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        taxi_plate              = (EditText) findViewById(R.id.taxiPlate);
        taxi_plate.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ImageView denunciar_img = (ImageView) findViewById(R.id.report_img1);
        ImageView felicitar_img = (ImageView) findViewById(R.id.congrats_img2);
        ImageView consultar_img = (ImageView) findViewById(R.id.search_img3);
        ImageView tips_img      = (ImageView) findViewById(R.id.tips_img4);

        denunciar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PLATE   = taxi_plate.getText().toString();
                Intent goToDenounce = new Intent(MainActivity.this, DenounceActivity.class);
                goToDenounce.putExtra("TAXI_PLATE", PLATE);
                Log.d("//PLATE", "--> " + PLATE);
                startActivity(goToDenounce);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
