package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OfferDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
    }

    public void onHire(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra("hire", true);
        startActivity(intent);
        finish();
    }

}
