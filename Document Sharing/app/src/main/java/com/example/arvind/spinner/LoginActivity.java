 package com.example.arvind.spinner;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arvind.spinner.config.Urlconfig;
import com.example.arvind.spinner.fbaccount.VerifyNumberActivity;
import com.example.arvind.spinner.pack.UserDashboard;

 public class LoginActivity extends Activity {
    EditText user_edittext;
    EditText pas_edittext;
    TextView error;
    private String errorMsg;
    private String resp;
    private String username;
    private String semester;
    private TextInputLayout userlogin_input_layout;
    private TextInputLayout paslogin_input_layout;
    private ProgressDialog progressDialog;
    Button registerBtn;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            getActionBar().setDisplayHomeAsUpEnabled(true);

        user_edittext = (EditText) findViewById(R.id.user_edittext);
        pas_edittext = (EditText) findViewById(R.id.pas_edittext);
        userlogin_input_layout=(TextInputLayout)findViewById(R.id.userlogin_input_layout);
        paslogin_input_layout=(TextInputLayout)findViewById(R.id.paslogin_input_layout);
        registerBtn=(Button) findViewById(R.id.RegisterBtn);

//            error= (TextView) findViewById(R.id.error);

        Button loginclicked = (Button) findViewById(R.id.loginbutton);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VerifyNumberActivity.class);
                startActivity(intent);
            }
        });

        loginclicked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (user_edittext.getText().toString().trim().equals("")) {
                    userlogin_input_layout.setErrorEnabled(true);
                    userlogin_input_layout.setError("Enter User name");
                } else if (pas_edittext.getText().toString().trim().equals("")) {
                    paslogin_input_layout.setErrorEnabled(true);
                    paslogin_input_layout.setError("Enter the Password");

                } else {

                    String name = user_edittext.getText().toString().trim();
                    String password = pas_edittext.getText().toString().trim();


                    Log.d("register", name + "  " + password);
                    createLogin(name, password);
                }

            }
        });
        user_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userlogin_input_layout.setErrorEnabled(false);

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
                paslogin_input_layout.setErrorEnabled(false);

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






     private void createLogin (final String name, final String password) {
                //
                Log.d("hhh", name + "  " + password);

                //start progress dialog
                progressDialog=new ProgressDialog(LoginActivity.this);

                progressDialog.setMessage("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                final RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

                String url = Urlconfig.LOGIN_ADMIN_URL;
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //stop progress dialog
                                progressDialog.dismiss();
                                // response
                                Log.d("Response", response);

                                String id=null,name=null;
                                try {
                                    JSONObject ob = new JSONObject(response);
                                    int res_code = ob.getInt("response_code");
                                    boolean status = ob.getBoolean("status");
                                    Log.d("gg", res_code + "  " + status + "   ");
                                    if (res_code == 200 && status == true) {
                                        String isAdmin = ob.getString("isAdmin");

                                        String records = ob.getString("records");
                                        JSONArray jsonarray = new JSONArray(records);
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);

                                            id = jsonobject.getString("id");
                                            name = jsonobject.getString("name");
                                            semester = jsonobject.getString("semester");
                                            Log.d("jsonarr", id + "  " + name);

                                        }
                                        if (isAdmin.equals("1")) {
                                            //open admin dashboard
                                            SharedPreferences.Editor editor = getSharedPreferences(Urlconfig.MY_PREFS_NAME, MODE_PRIVATE).edit();
                                            editor.putString("adminid", id);
                                            editor.putString("adminname", name);
                                            editor.putString("isAdmin", "1");
                                            editor.putString("semid",semester.substring(0,1).toString());
                                            editor.apply();
                                            Intent myIntent = new Intent(getBaseContext(), AdminDashboard.class);
                                            myIntent.putExtra("semid",semester.substring(0,1).toString());
                                            myIntent.putExtra("isAdmin","1");
                                            startActivity(myIntent);
                                            finish();

                                        } else if(isAdmin.equals("2")){
                                            //open user dashboard
                                            SharedPreferences.Editor editor = getSharedPreferences(Urlconfig.MY_PREFS_NAME, MODE_PRIVATE).edit();
                                            editor.putString("isAdmin", "2");
                                            editor.putString("semid", semester.substring(0,1));
                                            editor.apply();
                                            Intent myIntent = new Intent(getBaseContext(), UserDashboard.class);
                                            myIntent.putExtra("semid",semester.substring(0,1));
                                            myIntent.putExtra("isAdmin","2");
                                            startActivity(myIntent);
                                            finish();
                                        }else {
                                            Toast.makeText(getBaseContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                        }


                                    } else {
                                        String message = ob.getString("message");
                                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getBaseContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                // error
                                //stop progress dialog
                                progressDialog.dismiss();
                                String message = null;
                                if (volleyError instanceof NetworkError) {
                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (volleyError instanceof ServerError) {
                                    message = "The server could not be found. Please try again after some time!!";
                                } else if (volleyError instanceof AuthFailureError) {
                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (volleyError instanceof ParseError) {
                                    message = "Parsing error! Please try again after some time!!";
                                } else if (volleyError instanceof NoConnectionError) {
                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (volleyError instanceof TimeoutError) {
                                    message = "Connection TimeOut! Please check your internet connection.";
                                }

                                if(message!=null){
                                    Toast.makeText(getBaseContext(),message, Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userid", user_edittext.getText().toString().trim());
                        params.put("password", pas_edittext.getText().toString().trim());


                        return params;
                    }
                };

    //        postRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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




