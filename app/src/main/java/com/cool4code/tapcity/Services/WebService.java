package com.cool4code.tapcity.Services;

/**
 * Created by marcosantonioaguilerely on 12/26/14.
 */
public class WebService {
//    Context context =  this;
//    String APP_KEY      = "R5fV8yH0fiUEwnZqbyMZ9Z4usecKa84lshbC0AdJ";
//    String REST_API_KEY = "boukz5y9Sv3xIPntibPJAG8BFicUxaxRC9ZZziqn";
//
//    public void VolleyService(String URL, JSONObject jsonObject){
//        String URL_COMPLETE = URL + "/" + jsonObject;
//        final ProgressDialog pDialog = new ProgressDialog(context);
//        pDialog.setMessage("Creando denuncia...");
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        StringEntity se;
//        try {
//            se = new StringEntity(jsonObject.toString());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL_COMPLETE, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("App", response.toString());
//                        pDialog.hide();
//                        Toast.makeText(context, "¡Reporte creado!", Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("App", "Error: " + error.getMessage());
//                pDialog.hide();
//                Toast.makeText(context, "No se completo la denuncia. Intente nuevamente.", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            /*** Passing some request headers ***/
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("X-Parse-Application-Id", APP_KEY);
//                headers.put("X-Parse-REST-API-Key", REST_API_KEY);
//                headers.put("Accept", "application/json");
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//        };
//        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
//    }
}
