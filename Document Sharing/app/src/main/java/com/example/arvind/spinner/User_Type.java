package com.example.arvind.spinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class User_Type extends AppCompatActivity {

    private AppCompatButton iadmin;
    private  AppCompatButton istd;
    private String phone;
    private String deviceid;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type);

        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");
        deviceid = bundle.getString("deviceid");

        iadmin=(AppCompatButton)findViewById(R.id.iadmin);

        iadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Type.this, RegisterActivity.class);
                intent.putExtra("phone", phone);
                intent.putExtra("deviceid", deviceid);
                startActivity(intent);

            }
        });

        istd=(AppCompatButton)findViewById(R.id.istd);
        istd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Type.this, UserregisterActivity.class);
                intent.putExtra("phone", phone);
                intent.putExtra("deviceid", deviceid);
                startActivity(intent);

            }
        });




    }




   }



