package com.example.heytaxi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DriversRequest extends StringRequest {
    private static final String REQUEST_URL = "https://heytaxii.000webhostapp.com/Driver.php/";
    private Map<String, String> params;

    public DriversRequest(String location, String company, String vehicleType, String rating, Response.Listener<String> listener) {
        super(Request.Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("location", location);
        params.put("company", company);
        params.put("vehicleType", vehicleType);
        params.put("rating", rating);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

