package com.example.heytaxi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewRequest extends StringRequest {
    private static final String REQUEST_URL = "https://heytaxii.000webhostapp.com/Review.php/";
    private Map<String, String> params;

    public ReviewRequest(String driverID, String review, String rating, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("driverID", driverID);
        params.put("rating", rating);
        params.put("review", review);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
