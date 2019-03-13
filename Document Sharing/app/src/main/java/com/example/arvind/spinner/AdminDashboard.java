package com.example.arvind.spinner;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arvind.spinner.adater.DashboardAdapter;
import com.example.arvind.spinner.config.Urlconfig;
import com.example.arvind.spinner.pack.UserDashboard;
import com.example.arvind.spinner.pack.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.arvind.spinner.config.Urlconfig.MY_PREFS_NAME;

public class AdminDashboard extends AppCompatActivity {
    private List<Dashboard> m_list = new ArrayList<>();
    private RecyclerView recyclerView;
    private DashboardAdapter dashboardAdapter;
    private ProgressDialog progressDialog;
    private String isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Admin Dashboard");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        dashboardAdapter = new DashboardAdapter(AdminDashboard.this, m_list);

        // recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep m_list_row width to `match_parent`
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

        recyclerView.setAdapter(dashboardAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Dashboard ob = m_list.get(position);
                Toast.makeText(getApplicationContext(), ob.getId()+ " is selected!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getBaseContext(), UserDashboard.class);
                myIntent.putExtra("semid",ob.getId());
                myIntent.putExtra("isAdmin","1");
                myIntent.putExtra("semestername",ob.getSemesterName());
                startActivity(myIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //getting data from previous activity
        Bundle bundle = getIntent().getExtras();
        String semId = bundle.getString("semid");
        isAdmin = bundle.getString("isAdmin");
        if (semId != null) {
            Log.d("semid", semId);
           // getSemesterFromServer("1");
        } else {
            Log.d("semid", "semid is null");
        }

        prepareMovieData();
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
     * Prepares sample data to provide data set to dashboardAdapter
     */
    private void prepareMovieData() {
        Dashboard ob = new Dashboard("1", "1st Sem","http://paradigmmath.weebly.com/uploads/8/1/7/9/81799270/_______1889976.png");
        m_list.add(ob);

        ob = new Dashboard("2", "2nd Sem","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKxvDoMW5ciYUZSSrygh0UTEemcWsP7LAJs1g0Mt_eUAhF9zhK");
        m_list.add(ob);

        ob = new Dashboard("3", "3rd Sem","http://www.asdeha.com/wp-content/uploads/2018/05/semester3.png");
        m_list.add(ob);

        ob = new Dashboard("4", "4th Sem","http://www.asdeha.com/wp-content/uploads/2018/05/download.png");
        m_list.add(ob);

        ob = new Dashboard("5", "5th Sem","https://solveout.in/wp-content/uploads/2016/08/Logomakr_1drEhI.png");
        m_list.add(ob);

        ob = new Dashboard("6", "6th Sem","http://studyleague.com/images/SEM%206.png");
        m_list.add(ob);



        // notify dashboardAdapter about data set changes
        // so that it will render the list with new data
        dashboardAdapter.notifyDataSetChanged();
    }

    /**
     * get semester from server using semid
     *
     * @param semId
     */
    private void getSemesterFromServer(final String semId) {
        //start progress dialog
        progressDialog = new ProgressDialog(AdminDashboard.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final RequestQueue queue = Volley.newRequestQueue(AdminDashboard.this);

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
                                Log.d("gg","Subject ID: "+subject_id+"  Subject Name "+subject_name);
                                m_list.add(new Dashboard(subject_id,subject_name,url1));

                            }
                            dashboardAdapter.notifyDataSetChanged();
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
