package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OfferActivity extends AppCompatActivity {

    private ListView drivers_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        drivers_listView = findViewById(R.id.drivers_list);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Nizam", "Alesevic", "BMW", "Red Taxi", 4));
        DriversListAdapter adapter = new DriversListAdapter(this, drivers);
        drivers_listView.setAdapter(adapter);

        drivers_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Driver driver = (Driver) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), OfferDetailsActivity.class);
                intent.putExtra("driverID", driver.getDriverID());
                startActivity(intent);

            }
        });
    }


}
