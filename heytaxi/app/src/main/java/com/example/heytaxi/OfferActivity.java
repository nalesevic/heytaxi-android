package com.example.heytaxi;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferActivity extends AppCompatActivity {

    private ListView drivers_listView;
    private int userID;
    private int driverID;
    private Double userLocationLt;
    private Double userLocationLg;
    private String rating;
    private String vehicleType;
    private String company;
    private String fn;
    private String ln;
    private String vt;
    private String c;
    private String dlLt;
    private String dlLg;
    private String r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        drivers_listView = findViewById(R.id.drivers_list);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        rating = "6";
        vehicleType = "all";
        company = "all";
        if(extras != null) {
            userID = extras.getInt("userID");
            userLocationLt = extras.getDouble("userLocationLt");
            userLocationLg = extras.getDouble("userLocationLg");
            rating = extras.getString("rating", "6");
            vehicleType = extras.getString("vehicleType", "all");
            company = extras.getString("company", "all");

        }

        String url = "http://35.229.27.81/rest/passenger/book/request";
        RequestQueue requestQueue = Volley.newRequestQueue(OfferActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<Driver> drivers = new ArrayList<>();
                    JSONArray mJsonArray = new JSONArray(response);
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        driverID = mJsonObject.getInt("driverID");
                        Log.e("DRIVER ID", driverID + "");
                        fn = mJsonObject.getString("firstName");
                        ln = mJsonObject.getString("lastName");
                        vt = mJsonObject.getString("vehicleType");
                        c = mJsonObject.getString("companyName");
                        dlLt = mJsonObject.getString("latitude");
                        dlLg = mJsonObject.getString("longitude");
                        r = mJsonObject.getString("rating");
                        Log.e("OFFER REQUEST", r);

                        drivers.add(new Driver(driverID, fn, ln, vt, c, dlLt, dlLg, r));
                    }
                    if (drivers.size() == 0) {
                        Toast.makeText(OfferActivity.this, "No available drivers", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OfferActivity.this, MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    DriversListAdapter adapter = new DriversListAdapter(OfferActivity.this, drivers);
                    drivers_listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR", "Error while fetching drivers list");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rating", rating);
                params.put("vehicleType", vehicleType);
                params.put("company", company);
                params.put("userLocationLt", userLocationLt.toString());
                params.put("userLocationLg", userLocationLg.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);

        drivers_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Driver driver = (Driver) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), OfferDetailsActivity.class);
                intent.putExtra("driverID", driver.getDriverID());
                Log.i("DRIVER ID", String.valueOf(driverID));
                intent.putExtra("userID", userID);
                intent.putExtra("driverfn", driver.getFirstName());
                intent.putExtra("driverln", driver.getLastName());
                intent.putExtra("vt", driver.getVehicleType());
                intent.putExtra("c", driver.getCompany());
                intent.putExtra("dlLt", driver.getDriverLocationLatitude());
                intent.putExtra("ldLg", driver.getDriverLocationLongitude());
                intent.putExtra("r", driver.getRating());
                Log.e("OFFER INTENT", driver.getRating());
                startActivity(intent);
            }
        });
    }


}
