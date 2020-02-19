package com.example.heytaxi;

import android.content.Intent;
import android.location.Location;
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
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = (EditText) findViewById(R.id.email_login);
        passwordET = (EditText) findViewById(R.id.password_login);
        login_button = (Button) findViewById(R.id.btnLogin);

        TextView login = (TextView)findViewById(R.id.lnkRegister);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailEntered = emailET.getText().toString();
                final String passwordEntered = passwordET.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                String url = "http://35.229.27.81/rest/mobile/sign-in";
                StringRequest stringRequest = new StringRequest(POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("LOGIN RESPONSE", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String userType = jsonResponse.getString("status");
                            if (userType.equals("passenger")) {
                                boolean is = true;
                                int userID = jsonResponse.getInt("passengerID");
                                String firstName = jsonResponse.getString("firstName");
                                String lastName = jsonResponse.getString("lastName");
                                List<User> users = HTDatabase.getDatabase(LoginActivity.this).userDAO().getUsers();
                                for (int i = 0; i < users.size(); i++) {
                                    if (users.get(i).getUserID() == userID) {
                                        is = false;
                                    }
                                }
                                if (is) {
                                    User user = new User(userID, firstName, lastName);
                                    HTDatabase.getDatabase(LoginActivity.this).userDAO().register(user);
                                }

                                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                                intent.putExtra("userID", userID);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            } else if (userType.equals("driver")) {
                                int driverID = jsonResponse.getInt("driverID");
                                String firstName = jsonResponse.getString("firstName");
                                String lastName = jsonResponse.getString("lastName");
                                Intent intent = new Intent(LoginActivity.this, DriverUIActivity.class);
                                intent.putExtra("driverID", driverID);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Incorrect email or password")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE", String.valueOf(error));
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Incorrect email or password")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("email", emailEntered);
                        params.put("password", passwordEntered);
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
                /*
                            if (success) {
                                boolean is = true;
                                int userID = jsonResponse.getInt("userID");
                                String firstname = jsonResponse.getString("firstname");
                                String lastname = jsonResponse.getString("lastname");
                                List<User> users = HTDatabase.getDatabase(LoginActivity.this).userDAO().getUsers();
                                for(int i = 0; i < users.size(); i ++) {
                                    if(users.get(i).getUserID() == userID) {
                                        is = false;
                                    }
                                }
                                if(is) {
                                    User user = new User(userID, firstname, lastname);
                                    HTDatabase.getDatabase(LoginActivity.this).userDAO().register(user);
                                }
                                Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
                                intent.putExtra("userID", userID);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Incorrect email or password!")
                                        .setNegativeButton("Try again", null)
                                        .create()
                                        .show();
                            }

*/
            }
        });
    }
}


