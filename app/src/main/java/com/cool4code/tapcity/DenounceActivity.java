package com.cool4code.tapcity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cool4code.tapcity.Utils.GoogleGeo;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

public class DenounceActivity extends ActionBarActivity {
    private String GeoUrl    = "https://maps.googleapis.com/maps/";
    private String GeoParams = "api/geocode/json?latlng=";
    public  Context context  = this;

    String      email_tappsi    = "info@tappsi.co";
    String      email_easy      = "atencionalcliente@easytaxi.com.co";
    String      email_denuncie  = "denunciealtaxista@gmail.com";
    String      email_policia   = "";

    double      longitude;
    double      latitude;
    String      objId;
    String      plate;
    String      location_address;
    ParseUser   pUser;
    String      user_comment;

    Button          denounce;
    EditText        comment;
    Spinner         type;
    String          valueSpinner;
    ProgressDialog  mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denounce);

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

        denounce        = (Button) findViewById(R.id.denounce_button);
        comment         = (EditText) findViewById(R.id.comment_EditText);
        type            = (Spinner) findViewById(R.id.denounce_type);


        pUser           = ParseUser.getCurrentUser();
        objId           = pUser.getObjectId();
        Log.d("//ID", "ID: "+objId);
        Bundle extras   = getIntent().getExtras();
        plate           = extras.getString("TAXI_PLATE");
        Log.d("//PLATE", "PLATE: "+plate);
        location_address = getLocation();
        Log.d("//ADDRESS", "ADDRESS: "+location_address);

        denounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debbug - onclick", "--->"+valueSpinner);
                Log.d("Debbug - onclick", "--->"+user_comment);
                new CreateDenounce().execute();
            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueSpinner    =   type.getSelectedItem().toString();
                Log.d("Debbug", "--->"+valueSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String getLocation(){
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location   = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        latitude            = location.getLatitude();
        longitude           = location.getLongitude();
        Log.d("->", " Lat-> " + latitude + " Long-> " + longitude);

        GoogleGeo geo       = new GoogleGeo(GeoUrl, GeoParams);
        ArrayList geoArray  = geo.WSGetGeoCode(latitude, longitude);
        String geoString    = (String) geoArray.get(0);
        Log.d("Geo", "geo : " + geoString);
        String[] geoStringArray = geoString.trim().split("\\s*,\\s*");
        ArrayList<String> arryLocation = new ArrayList<String>();

        for(int i=0 ; i<= geoStringArray.length-1 ; i++){
            Log.d("Geo", "geo : " + geoStringArray[i]);
            arryLocation.add(geoStringArray[i]);
        }
        Log.d("String", "String : " + arryLocation);
        String geoAddress = (String) arryLocation.get(0);
        String geoTown    = (String) arryLocation.get(1);
        String geoState   = (String) arryLocation.get(2);
        String geoCountry = (String) arryLocation.get(3);

        Log.d("Address", "Address : " + geoAddress);
        Log.d("Town", "Town : " + geoTown);
        Log.d("State", "State : " + geoState);
        Log.d("Country", "Country : " + geoCountry);
        return geoAddress;
    }

    private class CreateDenounce extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setTitle("TapCity");
            mProgressDialog.setMessage("Enviando denuncia. Espere...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            user_comment            = comment.getText().toString();
            ParseObject data        = new ParseObject("Reports");
            ParseGeoPoint point     = new ParseGeoPoint(latitude, longitude);
            data.put("UserId",        pUser);
            data.put("geoString",     location_address);
            data.put("autoPlate",     plate);
            data.put("comment",       user_comment);
            data.put("type",          "negativo");
            data.put("type_denounce", valueSpinner);
            data.put("geo",         point);
            data.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e("PARSE.COM","FAILED " + e.getMessage());
                    }
                    else{
                        Log.e("PARSE.COM","SUCCESS");
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(DenounceActivity.this, "Denuncia enviada.", Toast.LENGTH_LONG).show();
            mProgressDialog.hide();
        }
    }
}
