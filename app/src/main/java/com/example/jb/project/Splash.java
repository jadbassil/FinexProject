package com.example.jb.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        ActionBar b = getSupportActionBar();
        b.hide();

        new Handler().postDelayed(new Runnable(){
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if(sharedPreferences.getInt("user_id", 0) != 0){
                    Intent i = new Intent(Splash.this, SpendingActivity.class);
                    Splash.this.startActivity(i);
                }
                else{
                    Intent i = new Intent(Splash.this, LoginActivity.class);
                    Splash.this.startActivity(i);
                }
            }
        }, 2000);

    }
}
