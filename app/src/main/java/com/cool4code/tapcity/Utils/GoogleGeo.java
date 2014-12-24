package com.cool4code.tapcity.Utils;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by marcosantonioaguilerely on 12/23/14.
 */
public class GoogleGeo {
    boolean resul = true;
    private Context context;

    private String URL;
    private String WS_Method;

    public GoogleGeo(String URL, String WS_Method){
        this.URL        = URL;
        this.WS_Method  = WS_Method;
    }

    public ArrayList WSGetGeoCode(double latitude, double longitude) {
        String URLComplete = this.URL + this.WS_Method + latitude + "," + longitude;
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URLComplete);
        String jsonText = null;
        ArrayList<String> arrayAuth = new ArrayList<String>();

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                jsonText = builder.toString();
            } else {
                Log.e(GoogleGeo.class.getName(), "¡Conexión no exitosa!");
            }
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            JSONObject obj = new JSONObject(jsonText);
            JSONArray jsonArray = obj.getJSONArray("results");
            for(int i=0 ; i < jsonArray.length() ; i++){
                JSONObject object = jsonArray.getJSONObject(i);
                JSONArray address_components = object.getJSONArray("address_components");
                String formatted_address = object.getString("formatted_address");
                arrayAuth.add(formatted_address);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayAuth;

    }
}
