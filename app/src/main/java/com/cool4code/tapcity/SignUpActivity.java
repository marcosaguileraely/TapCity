package com.cool4code.tapcity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cool4code.tapcity.Utils.GoogleGeo;
import com.cool4code.tapcity.Utils.SendMail;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SignUpActivity extends ActionBarActivity {
    EditText name;
    EditText lastname;
    EditText mobile;
    EditText dni;
    EditText pass;
    EditText repass;
    EditText email;
    Button   send;

    private String GeoUrl    = "https://maps.googleapis.com/maps/";
    private String GeoParams = "api/geocode/json?latlng=";

    double      longitude;
    double      latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

        name     = (EditText) findViewById(R.id.name);
        lastname = (EditText) findViewById(R.id.lastname);
        mobile   = (EditText) findViewById(R.id.mobile);
        dni      = (EditText) findViewById(R.id.user);
        pass     = (EditText) findViewById(R.id.pass);
        repass   = (EditText) findViewById(R.id.repass);
        email    = (EditText) findViewById(R.id.email);
        send     = (Button)   findViewById(R.id.send_data);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usertxt   = dni.getText().toString();
                final String passtxt   = pass.getText().toString();
                final String emailtxt  = email.getText().toString();
                final String mobiletxt = mobile.getText().toString();
                final String nametxt   = name.getText().toString();
                final String lasttxt   = lastname.getText().toString();
                final String fullname  = nametxt +" "+ lasttxt;

                final String citytxt   = getLocation();
                Log.d("//City", citytxt);
                DateFormat df          = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date today             = Calendar.getInstance().getTime();
                final String now       = df.format(today);

                if (nametxt.equals("") || lasttxt.equals("") || mobile.getText().toString().equals("") || usertxt.equals("") || passtxt.equals("") || emailtxt.equals("")) {
                    Toast.makeText(getApplicationContext(), "¡Por favor completa todos los campos!",Toast.LENGTH_LONG).show();
                }else {
                    ParseUser user = new ParseUser();
                    user.setUsername(String.valueOf(usertxt));
                    user.setPassword(String.valueOf(passtxt));
                    user.setEmail(String.valueOf(emailtxt));
                    user.put("name", nametxt);
                    user.put("lastname", lasttxt);
                    user.put("mobile", mobiletxt);
                    user.put("city", citytxt);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),"Registro exitoso. Enviando notificación...", Toast.LENGTH_SHORT).show();
                                SendMail sendMail =  new SendMail();
                                sendMail.sendEmailNotification(emailtxt, fullname, usertxt, passtxt, now, citytxt);
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d("Action", e.toString());
                                Toast.makeText(getApplicationContext(), "¡Ups! intentalo nuevamente. @" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }//end else
            }
        });//end onClick
    }//end onCreate

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
        return geoTown;
    }
}
