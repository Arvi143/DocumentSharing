package com.example.arvind.spinner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.arvind.spinner.config.Urlconfig;
import com.example.arvind.spinner.stringutils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserregisterActivity extends AppCompatActivity {
    private TextInputLayout loginuser_input_layout;
    private TextInputEditText loginuser;
    private TextInputLayout emailuser_input_layout;
    private TextInputEditText emaillogin;
    private TextInputLayout userphone_input_layout;
    private TextInputEditText userphone_edittext;
    private TextInputLayout userpas_input_layout;
    private TextInputEditText userpas_edittext;
    private TextInputLayout usercpas_input_layout;
    private TextInputEditText usercpas_edittext;
    private AppCompatButton usersignupclicked;
    private String deviceid;
    Spinner spin;
    private String phone;
    private ProgressDialog progressDialog;

    String[] semarray = {"Select Semester", "1st Sem", "2nd Sem", "3rd Sem", "4th Sem", "5th Sem", "6th Sem"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        loginuser_input_layout = (TextInputLayout) findViewById(R.id.loginuser_input_layout);
        loginuser = (TextInputEditText) findViewById(R.id.loginuser);
        emailuser_input_layout = (TextInputLayout) findViewById(R.id.emailuser_input_layout);
        emaillogin = (TextInputEditText) findViewById(R.id.emaillogin);
        userphone_input_layout = (TextInputLayout) findViewById(R.id.userphone_input_layout);
        userphone_edittext = (TextInputEditText) findViewById(R.id.userphone_edittext);
        userpas_input_layout = (TextInputLayout) findViewById(R.id.userpas_input_layout);
        userpas_edittext = (TextInputEditText) findViewById(R.id.userpas_edittext);
        usercpas_input_layout = (TextInputLayout) findViewById(R.id.usercpas_input_layout);
        usercpas_edittext = (TextInputEditText) findViewById(R.id.usercpas_edittext);
        usersignupclicked = (AppCompatButton) findViewById(R.id.usersignupclicked);
        spin = (Spinner) findViewById(R.id.spn);


        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");
        deviceid = bundle.getString("deviceid");
        userphone_edittext.setText(phone);
        userphone_edittext.setKeyListener(null);
        boolean valid = true;


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, semarray);

        spin.setAdapter(adapter);
        usersignupclicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginuser.getText().toString().trim().equals("")) {
                    loginuser_input_layout.setErrorEnabled(true);
                    loginuser_input_layout.setError("Enter User Name");

                } else if (!Utils.isValidEmail(emaillogin.getText().toString().trim())) {
                    emailuser_input_layout.setErrorEnabled(true);
                    emailuser_input_layout.setError("Enter valid Email");

                } else if (!Utils.isValidPhoneNumber(userphone_edittext.getText().toString().trim())) {
                    userphone_input_layout.setErrorEnabled(true);
                    userphone_input_layout.setError("Enter valid Mobile No.");




                } else if (usercpas_edittext.getText().toString().trim().equals("")) {
                    usercpas_input_layout.setErrorEnabled(true);
                    usercpas_input_layout.setError("Password does not matched");


                } else if (!(userpas_edittext.getText().toString().trim().equals(usercpas_edittext.getText().toString().trim()))) {
                    usercpas_input_layout.setErrorEnabled(true);
                    usercpas_input_layout.setError(" Password Does not Matchd");

                } else if (spin.getSelectedItem().toString().trim().equalsIgnoreCase("Select Semester")) {
                    Toast.makeText(getBaseContext(), "Select Semester",
                            Toast.LENGTH_SHORT).show();

                } else {
                    String name = loginuser.getText().toString().trim();
                    String email = emaillogin.getText().toString().trim();
                    String phone = userphone_edittext.getText().toString().trim();
                    String password = userpas_edittext.getText().toString().trim();
                    String cnfpassword = usercpas_edittext.getText().toString().trim();
                    String semester = spin.getSelectedItem().toString().trim();

                    Log.d("register", email + "  " + phone + "  " + name + "  " + password + "  " + cnfpassword);
                    deviceid = "jhkhkjhkjhkj";
                    createUser(name, email, phone, password, semester, deviceid);
                }


            }
        });


        loginuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginuser_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emaillogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailuser_input_layout.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userphone_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userphone_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userpas_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userpas_input_layout.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        usercpas_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usercpas_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    private void createUser(final String username, final String email, final String phone, final String password, final String semester, final String deviceid) {
        //
        Log.d("hhh", username + "  " + email + "    " + phone + "   " + deviceid + "   " + password);
        progressDialog = new ProgressDialog(UserregisterActivity.this);
        
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = Urlconfig.CREATE_ADMIN_URL;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        progressDialog.cancel();
                        try {
                            JSONObject ob = new JSONObject(response);
                            int res_code = ob.getInt("response_code");
                            boolean status = ob.getBoolean("status");
                            String message = ob.getString("message");
                            Log.d("gg", res_code + "  " + status + "   " + message + "   ");
                            if (res_code == 200 && status == true) {
                                //open login page
                                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(myIntent);
                                finish();
                            } else if (res_code == 205) {
                                //open login page
                                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(myIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        error.printStackTrace();
                        // error
//                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("adminname", username);
                params.put("email", email);
                params.put("phone", phone);
                params.put("device_id", deviceid);
                params.put("semester", semester);
                params.put("firebase_token", "Dummy");
                params.put("password", password);
                params.put("isAdmin", "2");

                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        postRequest.setShouldCache(false);
        queue.add(postRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                queue.getCache().clear();
            }
        });
    }
}