package com.cool4code.tapcity.Utils;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by marcosantonioaguilerely on 12/21/14.
 */

public class ParseUtil extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // Add your initialization code here
        Parse.initialize(this, "R5fV8yH0fiUEwnZqbyMZ9Z4usecKa84lshbC0AdJ", "JfFQPO6Fde2Dk4AgFR7T79GmZ100cg2pATmQGLDs");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
