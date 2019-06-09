package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfferActivity extends AppCompatActivity {

    private ListView drivers_listView;
    private TextView companyTV;
    private TextView vehicleTypeTV;
    private int userID;
    private String userLocation;
    private String rating;
    private String vehicleType;
    private String company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        drivers_listView = findViewById(R.id.drivers_list);
        companyTV = (TextView) findViewById(R.id.company_name);
        vehicleTypeTV = (TextView) findViewById(R.id.vehicle_type);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
            userLocation = extras.getString("userLocation");
            rating = extras.getString("rating");
            vehicleType = extras.getString("vehicleType");
            company = extras.getString("company");

        }


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        jsonResponse.getJSONArray("drivers");
                        List<Driver> drivers = new ArrayList<>();
                        DriversListAdapter adapter = new DriversListAdapter(OfferActivity.this, drivers);
                        drivers_listView.setAdapter(adapter);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OfferActivity.this);
                        builder.setMessage("Fetching Drivers Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DriversRequest driversRequest = new DriversRequest("lokacija", "kompanija", "limuzina", "4", responseListener);
        RequestQueue queue = Volley.newRequestQueue(OfferActivity.this);
        queue.add(driversRequest);


        drivers_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Driver driver = (Driver) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), OfferDetailsActivity.class);
                intent.putExtra("driverID", driver.getDriverID());
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
    }


}
