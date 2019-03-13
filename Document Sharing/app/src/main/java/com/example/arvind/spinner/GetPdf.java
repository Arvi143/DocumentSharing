package com.example.arvind.spinner;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.arvind.spinner.adater.FileListAdapter;
import com.example.arvind.spinner.config.Urlconfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetPdf extends AppCompatActivity implements FileListAdapter.AdapterListener {
    private List<ShowUploadFile> subject = new ArrayList<ShowUploadFile>();
    private RecyclerView recyclerView;
    private FileListAdapter adapter;
    private ProgressDialog progressDialog;


    // button to show progress dialog
    Button downloadbutton;

    // Progress Dialog
    private ProgressDialog pDialog;
    ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "https://api.androidhive.info/progressdialog/hive.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Files");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        adapter = new FileListAdapter(subject, this);


        // recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep m_list_row.xmlth to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep m_list_row.xmlth to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        //adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

//        // row click listener
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                ShowUploadFile ob = subject.get(position);
//                Toast.makeText(getApplicationContext(), ob.getId() + " is selected!", Toast.LENGTH_SHORT).show();
////                Intent myIntent = new Intent(getBaseContext(), UserDashboard.class);
////                myIntent.putExtra("semid", ob.getId());
////                startActivity(myIntent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        //getting data from previous activity
        Bundle bundle = getIntent().getExtras();
        String semId = bundle.getString("semid");
        String subjectid = bundle.getString("subjectid");
        if (semId != null) {
            Log.d("semid", semId);
            Log.d("subjectid", subjectid);
            try {
                getUploadedFileSubject(semId, subjectid);
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        } else {
            Log.d("semid", "semid is null");
        }


//        downloadbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DownloadFileFromURL().execute(file_url);
//            }
//        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }


    private void getUploadedFileSubject(final String semId, final String subjectId) {
        //start progress dialog
        progressDialog = new ProgressDialog(GetPdf.this);


        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final RequestQueue queue = Volley.newRequestQueue(GetPdf.this);

        String url = Urlconfig.GET_PDFs;
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
                            if (res_code == 200 && status == true) {
                                String records = ob.getString("results");

                                JSONArray jsonarray = new JSONArray(records);
                                if (jsonarray.length() != 0) {
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject ob1 = jsonarray.getJSONObject(i);
                                        String id = ob1.getString("id");
                                        String url = ob1.getString("url");
                                        String semestername = ob1.getString("semestername");
                                        String semesterid = ob1.getString("semesterid");
                                        String adminid = ob1.getString("adminid");
                                        String adminname = ob1.getString("adminname");
                                        String remarks = ob1.getString("remarks");
                                        String subjectid = ob1.getString("subjectid");
                                        subject.add(new ShowUploadFile(id, url, semestername, semesterid,
                                                adminid, adminname, remarks, subjectid));

                                    }
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getBaseContext(), "No Data Found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getBaseContext(), "Something went   wrong!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), "Something went  wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // error
                        //stop progress dialog
                        progressDialog.dismiss();
                        volleyError.printStackTrace();
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

                        if (message != null) {
                            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("semesterid", semId);
                params.put("subjectid", subjectId);

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

    @Override
    public void downloadClicked(ShowUploadFile showUploadFile) {
        Toast.makeText(getBaseContext(), showUploadFile.getUrl(), Toast.LENGTH_SHORT).show();
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.jpg");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }


        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
            // setting downloaded into image view
            my_image.setImageDrawable(Drawable.createFromPath(imagePath));
        }

    }


}