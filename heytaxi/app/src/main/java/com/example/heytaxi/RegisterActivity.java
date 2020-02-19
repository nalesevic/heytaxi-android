package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class RegisterActivity extends AppCompatActivity {

    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView login = (TextView)findViewById(R.id.lnkLogin);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        final EditText firstNameET = (EditText) findViewById(R.id.firstname_registration);
        final EditText lastNameET = (EditText) findViewById(R.id.lastname_registration);
        final EditText emailET = (EditText) findViewById(R.id.email_registration);
        final EditText passwordET = (EditText) findViewById(R.id.password_registration);
        final Button register_button = (Button) findViewById(R.id.btnRegister);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = firstNameET.getText().toString();
                final String lastName = lastNameET.getText().toString();
                final String email = emailET.getText().toString();
                final String password = passwordET.getText().toString();

                if (firstName.equals("") && lastName.equals("") && email.equals("") && password.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                   RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                   String url = "http://35.229.27.81/rest/passenger/sign-up";
                   StringRequest stringRequest = new StringRequest(POST, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                           RegisterActivity.this.startActivity(intent);
                           finish();
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Log.e("RESPONSE", String.valueOf(error));
                           AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                           builder.setMessage("User already exists")
                                   .setNegativeButton("Retry", null)
                                   .create()
                                   .show();
                       }
                   }) {
                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String,String> params = new HashMap<String, String>();
                           params.put("firstName", firstName);
                           params.put("lastName", lastName);
                           params.put("email", email);
                           params.put("password", password);
                           return params;
                       }

                       @Override
                       public Map<String, String> getHeaders() throws AuthFailureError {
                           Map<String,String> params = new HashMap<String, String>();
                           params.put("Content-Type", "application/x-www-form-urlencoded");
                           return params;
                       }
                   };
                   requestQueue.add(stringRequest);
                }
            }
        });
    };
}