package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;

public class FilterActivity extends AppCompatActivity {

    private int userID;
    private String userLocation;
    private String company;
    private String vehicleType;
    private String rating;
    private Spinner companyS;
    private Spinner vehicleTypeS;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
            userLocation = extras.getString("userLocation");
        }

        companyS = findViewById(R.id.company_spinner);
        vehicleTypeS = findViewById(R.id.vehicleType_spinner);
        ratingBar = findViewById(R.id.choose_rating);

        String[] companies = new String[] {
                "all", "Red Taxi", "Sarajevo Taxi", "Yellow Taxi"
        };
        String[] vehicles = new String[] {
                "all", "Limousine", "Hatchback", "Van"
        };
        ArrayAdapter<String> companyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, companies);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, vehicles);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        companyS.setAdapter(companyAdapter);
        vehicleTypeS.setAdapter(vehicleAdapter);

    }

    public void onApplyFilter(View view) {
        float rating_float = ratingBar.getRating();
        rating = String.valueOf(rating_float);
        company = companyS.getSelectedItem().toString();
        vehicleType = vehicleTypeS.getSelectedItem().toString();
        if(rating.equals("0.0"))
            rating = "6";
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("rating", rating);
        intent.putExtra("company", company);
        intent.putExtra("vehicleType", vehicleType);
        intent.putExtra("userLocation", userLocation);
        startActivity(intent);
        finish();
    }
}
