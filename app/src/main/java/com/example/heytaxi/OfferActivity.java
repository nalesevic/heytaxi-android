package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfferActivity extends AppCompatActivity {

    private ListView drivers_listView;
    private int userID;
    private String userLocation;
    private String rating;
    private String vehicleType;
    private String company;
    private String fn;
    private String ln;
    private String vt;
    private String c;
    private String dl;
    private String r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        drivers_listView = findViewById(R.id.drivers_list);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
            userLocation = extras.getString("userLocation");
            rating = extras.getString("rating", "1");
            vehicleType = extras.getString("vehicleType", "all");
            company = extras.getString("company", "all");

        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String success = response;
                    if(!success.contains("false")) {
                        List<Driver> drivers = new ArrayList<>();
                        JSONArray mJsonArray = new JSONArray(response);
                        JSONObject mJsonObject = new JSONObject();
                        for (int i = 0; i < mJsonArray.length(); i++) {
                            mJsonObject = mJsonArray.getJSONObject(i);
                            fn = mJsonObject.getString("firstname");
                            ln = mJsonObject.getString("lastName");
                            vt = mJsonObject.getString("vehicleType");
                            c = mJsonObject.getString("company");
                            dl = mJsonObject.getString("driverLocation");
                            r = mJsonObject.getString("rating");
                            drivers.add(new Driver(fn, ln, vt, c, dl, r));
                        }
                        DriversListAdapter adapter = new DriversListAdapter(OfferActivity.this, drivers);
                        drivers_listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(OfferActivity.this, "Enter your location", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OfferActivity.this, LocationActivity.class);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DriversRequest driversRequest = new DriversRequest(userLocation, company, vehicleType, rating, responseListener);
        RequestQueue queue = Volley.newRequestQueue(OfferActivity.this);
        queue.add(driversRequest);


        drivers_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Driver driver = (Driver) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), OfferDetailsActivity.class);
                intent.putExtra("driverID", driver.getDriverID());
                intent.putExtra("userID", userID);
                intent.putExtra("driverfn", driver.getFirstName());
                intent.putExtra("driverln", driver.getLastName());
                intent.putExtra("vt", driver.getVehicleType());
                intent.putExtra("c", driver.getCompany());
                intent.putExtra("dl", driver.getDriverLocation());
                intent.putExtra("r", driver.getRating());
                startActivity(intent);

            }
        });
    }


}
