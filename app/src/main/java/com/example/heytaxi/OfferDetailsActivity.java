package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class OfferDetailsActivity extends AppCompatActivity {

    private int userID;
    private int driverID;
    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView vehicleTypeTV;
    private TextView companyTV;
    private RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        firstNameTV = findViewById(R.id.driver_firstName);
        lastNameTV = findViewById(R.id.driver_lastName);
        vehicleTypeTV = findViewById(R.id.driver_vehicle);
        companyTV = findViewById(R.id.driver_company);
        ratingBar = findViewById(R.id.driver_rating);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
            driverID  = extras.getInt("driverID");
            String driverfn = extras.getString("driverfn");
            String driverln = extras.getString("driverln");
            String vehicleType = extras.getString("vt");
            String company = extras.getString("c");
            String driverLocation = extras.getString("dl");
            String rating = extras.getString("r");

            firstNameTV.setText(driverfn);
            lastNameTV.setText(driverln);
            vehicleTypeTV.setText(vehicleType);
            companyTV.setText(company);
            ratingBar.setRating(Float.parseFloat(rating));
        }


    }

    public void onHire(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra("hire", true);
        intent.putExtra("userID", userID);
        intent.putExtra("driverID", driverID);
        startActivity(intent);
        finish();
    }

}
