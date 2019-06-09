package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private static int id;
    private boolean hire;
    private TextView nameTV;
    private EditText mobileET;
    private EditText cityET;
    private Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id = extras.getInt("userID");
        }
        User user = HTDatabase.getDatabase(ProfileActivity.this).userDAO().getUserById(id);
        nameTV = (TextView) findViewById(R.id.name_profile);
        mobileET = (EditText) findViewById(R.id.mobile_profile);
        cityET = (EditText) findViewById(R.id.city_profile);
        btnSaveProfile = (Button) findViewById(R.id.save_profile);

        nameTV.setText(user.getFirstName() + " " + user.getLastName());
        mobileET.setText(user.getMobile());
        cityET.setText(user.getCity());

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = -1;
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                if(extras != null) {
                    id = extras.getInt("userID");
                    hire = extras.getBoolean("hire");
                }
                User user = HTDatabase.getDatabase(ProfileActivity.this).userDAO().getUserById(id);
                String mobile = mobileET.getText().toString();
                String city = cityET.getText().toString();
                user.setCity(city);
                user.setMobile(mobile);

                HTDatabase.getDatabase(ProfileActivity.this).userDAO().updateUser(user);

                Intent sv = new Intent(ProfileActivity.this, LocationActivity.class);
                sv.putExtra("userID", id);
                sv.putExtra("hire", hire);
                startActivity(sv);
                finish();
            }
        });

    }
}
