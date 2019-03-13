package com.example.arvind.spinner.pack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arvind.spinner.AdminFileUploadActivity;
import com.example.arvind.spinner.LoginActivity;
import com.example.arvind.spinner.Splash;
import com.example.arvind.spinner.adater.DashboardAdapter;
import com.example.arvind.spinner.Dashboard;
import com.example.arvind.spinner.GetPdf;
import com.example.arvind.spinner.R;
import com.example.arvind.spinner.config.Urlconfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.arvind.spinner.config.Urlconfig.MY_PREFS_NAME;

public class UserDashboard extends AppCompatActivity {
    private List<Dashboard> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DashboardAdapter mDashboardAdapter;
    private ProgressDialog progressDialog;
    private String semId;
    private String semestername;

    private String isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("User Dashboard");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        mDashboardAdapter = new DashboardAdapter(UserDashboard.this, movieList);

        // recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep m_list_row.xmlth to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep m_list_row.xmlth to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mDashboardAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Dashboard movie = movieList.get(position);
                if (isAdmin.equals("1")) {
                    Log.d("fdf", movieList.get(position).getSemesterName());
                    Toast.makeText(getBaseContext(), movieList.get(position).getSemesterName() + "hjhjh", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(getBaseContext(), AdminFileUploadActivity.class);
                    myIntent.putExtra("semid", semId);
                    myIntent.putExtra("subjectid", movieList.get(position).getId());
                    myIntent.putExtra("semestername", semestername);
                    startActivity(myIntent);
                } else {
                    Intent myIntent = new Intent(getBaseContext(), GetPdf.class);
                    myIntent.putExtra("semid", semId);
                    myIntent.putExtra("subjectid", movieList.get(position).getId());
                    startActivity(myIntent);
                    Toast.makeText(getApplicationContext(), movie.getId() + " is selected! " + movie.getSemesterName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //getting data from previous activity
        Bundle bundle = getIntent().getExtras();
        semId = bundle.getString("semid");
        isAdmin = bundle.getString("isAdmin");
        if (isAdmin.equals("1"))
            semestername = bundle.getString("semestername");
        if (semId != null) {
            Log.d("semid", semId);
            getSemesterFromServer(semId);
        } else {
            Log.d("semid", "semid is null");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent mainIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(mainIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * get semester from server using semid
     *
     * @param semId
     */
    private void getSemesterFromServer(final String semId) {
        //start progress dialog
        progressDialog = new ProgressDialog(UserDashboard.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final RequestQueue queue = Volley.newRequestQueue(UserDashboard.this);

        String url = Urlconfig.SEMESTER_URL;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //stop progress dialog
                        progressDialog.dismiss();
                        // response
                        Log.d("Response", response);


                        try {
                            JSONObject ob = new JSONObject(response);
                            int res_code = ob.getInt("response_code");
                            boolean status = ob.getBoolean("status");

                            String records = ob.getString("records");
                            JSONObject ob1 = new JSONObject(records);
                            String id = ob1.getString("id");
                            String semestername = ob1.getString("semestername");
                            String subject = ob1.getString("subject");

                            Log.d("gg", res_code + "  " + status + "   " + records + "   " + "  " + id + "   " + semestername + "  " + subject);

                            JSONArray jsonarray = new JSONArray(subject);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String subject_id = jsonobject.getString("id");
                                String subject_name = jsonobject.getString("subject");
                                String url1 = jsonobject.getString("url");
                                Log.d("gg", "Subject ID: " + subject_id + "  Subject Name " + subject_name);
                                movieList.add(new Dashboard(subject_id, subject_name, url1));

                            }
                            mDashboardAdapter.notifyDataSetChanged();
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
                        //stop progress dialog
                        progressDialog.dismiss();
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("semesterid", semId);


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