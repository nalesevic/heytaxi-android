package com.example.heytaxi_driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.Locale;
import java.util.Map;

public class DriverUIActivity extends AppCompatActivity implements LocationListener {

    private ListView users_listView;
    private int driverID;
    private String rideID;
    private String dlLt;
    private String dlLg;
    private String passengerLocationAddress;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ui);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            driverID = extras.getInt("driverID");
        }

        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(DriverUIActivity.this);
        users_listView = findViewById(R.id.passenger_list);

        String url = "http://35.229.27.81/rest/driver/list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<PassengerRequest> passengersRequest = new ArrayList<>();
                    JSONArray mJsonArray = new JSONArray(response);
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        rideID = mJsonObject.getString("rideID");
                        dlLt = mJsonObject.getString("startPointLatitude");
                        dlLg = mJsonObject.getString("startPointLongitude");
                        passengerLocationAddress = getCompleteAddressString(Double.valueOf(dlLt), Double.valueOf(dlLg));
                        passengersRequest.add(new PassengerRequest(dlLt, dlLg, passengerLocationAddress));
                    }
                    if (passengersRequest.size() == 0) {
                        Toast.makeText(DriverUIActivity.this, "No passengers", Toast.LENGTH_SHORT).show();
                    }
                    UsersListAdapter adapter = new UsersListAdapter(DriverUIActivity.this, passengersRequest);
                    users_listView.setAdapter(adapter);
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
                params.put("driverID", String.valueOf(driverID));
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

    }

    public void onRefresh(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(DriverUIActivity.this);
        String url = "http://35.229.27.81/rest/driver/list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("RE", response);
                    List<PassengerRequest> passengersRequest = new ArrayList<>();
                    JSONArray mJsonArray = new JSONArray(response);
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        rideID = mJsonObject.getString("rideID");
                        dlLt = mJsonObject.getString("startPointLatitude");
                        dlLg = mJsonObject.getString("startPointLongitude");
                        passengerLocationAddress = getCompleteAddressString(Double.valueOf(dlLt), Double.valueOf(dlLg));
                        passengersRequest.add(new PassengerRequest(dlLt, dlLg, passengerLocationAddress));
                    }
                    if (passengersRequest.size() == 0) {
                        Toast.makeText(DriverUIActivity.this, "No passengers", Toast.LENGTH_SHORT).show();
                    }
                    UsersListAdapter adapter = new UsersListAdapter(DriverUIActivity.this, passengersRequest);
                    users_listView.setAdapter(adapter);
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
                params.put("driverID", String.valueOf(driverID));
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
    }

    public void onAcceptRequest(View view) {
        Intent intent = new Intent(this, TimeArrivalActivity.class);
        intent.putExtra("rideID", rideID);
        intent.putExtra("driverID", String.valueOf(driverID));
        Log.e("EXTRA", String.valueOf(driverID));
        startActivity(intent);
    }

    private String getCompleteAddressString(Double LATITUDE, Double LONGITUDE) {
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());

        RequestQueue requestQueue = Volley.newRequestQueue(DriverUIActivity.this);
        String url = "http://35.229.27.81/rest/driver/location";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                params.put("driverID", String.valueOf(driverID));
                params.put("latitude", latitude);
                params.put("longitude", longitude);
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
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
