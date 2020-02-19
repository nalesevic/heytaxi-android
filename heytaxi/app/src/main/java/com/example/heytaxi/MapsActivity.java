package com.example.heytaxi;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView tvCurrentLocation;
    private static LatLng center;
    private static String locationString;
    private static int userID;
    private static Double userLocationLt;
    private static Double userLocationLg;
    private String rating;
    private String vehicleType;
    private String company;
    private String rideID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
            vehicleType = extras.getString("vehicleType");
            company = extras.getString("company");
            rating = extras.getString("rating");
            rideID = extras.getString("rideID");
        }

        User user = HTDatabase.getDatabase(MapsActivity.this).userDAO().getUserById(userID);
        if (user.getLatitude() != null) {
            userLocationLt = user.getLatitude();
            userLocationLg = user.getLongitude();
        } else {
            userLocationLt = 43.859043;
            userLocationLg = 18.4274526;
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        center = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocationLt, userLocationLg), 17.0f));
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setMyLocationEnabled(true);

        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                center = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                userLocationLt = center.latitude;
                userLocationLg = center.longitude;
            }
        });

        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                locationString = getCompleteAddressString(center.latitude, center.longitude);
                updateUserLocation();
                tvCurrentLocation.setText(locationString);
            }
        });
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17.0f));
        locationString = getCompleteAddressString(location.getLatitude(), location.getLongitude());
        userLocationLt = location.getLatitude();
        userLocationLg = location.getLongitude();
        updateUserLocation();
        tvCurrentLocation.setText(locationString);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
                // not returned
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    public void onTaxiPreference(View view) {
        Intent intent = new Intent(MapsActivity.this, FilterActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    public void onProfile(View view) {
        Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("vehicleType", vehicleType);
        intent.putExtra("rating", rating);
        intent.putExtra("company", company);
        startActivity(intent);
    }

    public void onSend(View view) {
        Intent intent = new Intent(this, OfferActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("userLocationLt", userLocationLt);
        intent.putExtra("userLocationLg", userLocationLg);
        intent.putExtra("rating", rating);
        intent.putExtra("vehicleType", vehicleType);
        intent.putExtra("company", company);
        startActivity(intent);

    }

    private void updateUserLocation() {
        User user = HTDatabase.getDatabase(MapsActivity.this).userDAO().getUserById(userID);
        user.setLatitude(userLocationLt);
        user.setLongitude(userLocationLg);
        HTDatabase.getDatabase(MapsActivity.this).userDAO().updateUser(user);
    }
}
