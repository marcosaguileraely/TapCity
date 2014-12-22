package com.cool4code.tapcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity extends ActionBarActivity {
    EditText name;
    EditText lastname;
    EditText mobile;
    EditText dni;
    EditText pass;
    EditText repass;
    EditText email;
    Button   send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //ParseUtil parseUtil = new ParseUtil();
        //parseUtil.onCreate();

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
                String usertxt   = dni.getText().toString();
                String passtxt   = pass.getText().toString();
                String emailtxt  = email.getText().toString();
                String mobiletxt = mobile.getText().toString();
                String nametxt   = name.getText().toString();
                String lasttxt   = lastname.getText().toString();

                if (nametxt.equals("") && lasttxt.equals("") && mobile.getText().toString().equals("") && usertxt.equals("") && passtxt.equals("") && emailtxt.equals("")) {
                    Toast.makeText(getApplicationContext(), "¡Por favor completa todos los campos!",Toast.LENGTH_LONG).show();
                }else {
                    ParseUser user = new ParseUser();
                    user.setUsername(String.valueOf(usertxt));
                    user.setPassword(String.valueOf(passtxt));
                    user.setEmail(String.valueOf(emailtxt));
                    user.put("name", nametxt);
                    user.put("lastname", lasttxt);
                    user.put("mobile", mobiletxt);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),"Registro exitoso. Por favor ingresa a TapCity.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d("Action", e.toString());
                                Toast.makeText(getApplicationContext(), "¡Ups! intentalo nuevamente. @" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });




    }



}
