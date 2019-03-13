package com.example.arvind.spinner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.arvind.spinner.config.Urlconfig;
import com.example.arvind.spinner.fbaccount.VerifyNumberActivity;
import com.example.arvind.spinner.pack.UserDashboard;

public class Splash extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);

        /* New Handler to start the Menu-Activity 
         * and close this com.example.arvind.spinner.Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //get islogin varivale to shared preferences
                SharedPreferences prefs = getSharedPreferences(Urlconfig.MY_PREFS_NAME, MODE_PRIVATE);
                boolean isLogin = prefs.getBoolean("isLogin",false);
                String isAdmin=prefs.getString("isAdmin","");
                String semid=prefs.getString("semid","");


                /* Create an Intent that will start the Menu-Activity. */
                if(isAdmin.equals("1")){
                    Intent myIntent = new Intent(getBaseContext(), AdminDashboard.class);
                    myIntent.putExtra("semid",semid);
                    myIntent.putExtra("isAdmin","1");
                    startActivity(myIntent);
                    finish();
                }else if(isAdmin.equals("2")){
                    Intent myIntent = new Intent(getBaseContext(), UserDashboard.class);
                    myIntent.putExtra("semid",semid);
                    myIntent.putExtra("isAdmin","2");
                    startActivity(myIntent);
                }
                else {
                    Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}