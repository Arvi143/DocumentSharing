package com.example.arvind.spinner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private AppCompatButton signupclicked;
    private TextInputLayout user_input_layout;
    private TextInputEditText user_edittext;
    private TextInputEditText email_edittext;
    private TextInputLayout email_input_layout;
    private TextInputLayout phone_input_layout;
    private TextInputEditText phone_edittext;
    private TextInputEditText pas_edittext;
    private TextInputLayout pas_input_layout;
    private TextInputEditText cpas_edittext;
    private TextInputLayout cpas_input_layout;
    private String phone;
    private String deviceid;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register);

        signupclicked = (AppCompatButton) findViewById(R.id.signupclicked);
        user_edittext = (TextInputEditText) findViewById(R.id.user_edittext);
        user_input_layout = (TextInputLayout) findViewById(R.id.user_input_layout);
        email_edittext = (TextInputEditText) findViewById(R.id.email_edittext);
        email_input_layout = (TextInputLayout) findViewById(R.id.email_input_layout);
        phone_edittext = (TextInputEditText) findViewById(R.id.phone_edittext);
        phone_input_layout = (TextInputLayout) findViewById(R.id.phone_input_layout);
        pas_edittext = (TextInputEditText) findViewById(R.id.pas_edittext);
        pas_input_layout = (TextInputLayout) findViewById(R.id.pas_input_layout);
        cpas_edittext = (TextInputEditText) findViewById(R.id.cpas_edittext);
        cpas_input_layout = (TextInputLayout) findViewById(R.id.cpas_input_layout);


        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");
        deviceid = bundle.getString("deviceid");
        phone_edittext.setText(phone);
        phone_edittext.setKeyListener(null);



        signupclicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (user_edittext.getText().toString().trim().equals("")) {
                    user_input_layout.setErrorEnabled(true);
                    user_input_layout.setError("Enter User name");

                } else if (!Utils.isValidEmail(email_edittext.getText().toString().trim())) {
                    email_input_layout.setErrorEnabled(true);
                    email_input_layout.setError("Enter valid Email");
                }
//                else if (!Utils.isValidPhoneNumber(phone_edittext.getText().toString().trim())) {
//                    phone_input_layout.setErrorEnabled(true);
//                    phone_input_layout.setError("Enter valid Mobile No.");
//
//                }
                else if (pas_edittext.getText().toString().trim().equals("")) {
                    pas_input_layout.setErrorEnabled(true);
                    pas_input_layout.setError("Enter The Password");

                } else if (cpas_edittext.getText().toString().trim().equals("")) {
                    cpas_input_layout.setErrorEnabled(true);
                    cpas_input_layout.setError("Enter The Password");

                } else if (!(pas_edittext.getText().toString().trim().equals(cpas_edittext.getText().toString().trim()))) {
                    cpas_input_layout.setErrorEnabled(true);
                    cpas_input_layout.setError(" Password Does not Matchd");
                } else {
                    String email = email_edittext.getText().toString().trim();
                    String phone = phone_edittext.getText().toString().trim();
                    String name = user_edittext.getText().toString().trim();
                    String password = pas_edittext.getText().toString().trim();
                    String cnfPassword = cpas_edittext.getText().toString().trim();

                    Log.d("register", email + "  " + phone + "  " + name + "  " + password + "  " + cnfPassword);
                    createAdmin(name, email, phone, password, deviceid);
                }

            }
        });


        user_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                user_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email_input_layout.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone_input_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pas_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pas_input_layout.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cpas_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cpas_input_layout.setErrorEnabled(false);
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

    private void createAdmin(final String adminname, final String email, final String phone, final String password, final String deviceid) {
        //
        Log.d("hhh", adminname + "  " + email + "    " + phone + "   " + deviceid + "   " + password);


        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = Urlconfig.CREATE_ADMIN_URL;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        // response
                        Log.d("Response", response);


                        try {
                            JSONObject ob = new JSONObject(response);
                            int res_code = ob.getInt("response_code");
                            boolean status = ob.getBoolean("status");
                            String message = ob.getString("message");
                            Log.d("gg", res_code + "  " + status + "   " + message + "   ");
                            if (res_code == 200 && status == true) {

                                //save islogin varivale to shared preferences
                                SharedPreferences.Editor editor = getSharedPreferences(Urlconfig.MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putBoolean("isLogin", true);
                                editor.apply();

                                //open login page
                                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(myIntent);
                                finish();

                            } else if (res_code == 205) {
                                //save islogin varivale to shared preferences
                                SharedPreferences.Editor editor = getSharedPreferences(Urlconfig.MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putBoolean("isLogin", true);
                                editor.apply();
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
                        // error
                        progressDialog.cancel();
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("adminname", adminname);
                params.put("email", email);
                params.put("phone", phone);
                params.put("device_id", deviceid);
                params.put("semester", "sdaad");
                params.put("firebase_token", "Dummy");
                params.put("password", password);
                params.put("isAdmin", "1");
                return params;
            }
        };

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
