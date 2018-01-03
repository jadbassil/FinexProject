package com.example.jb.project;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class SavingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
    }

    public void showDesc(View v){
        new AlertDialog.Builder(this)
                .setTitle("What is Total savings ?")
                .setMessage("If you already have previous savings put their value here so you can add new savings")
                .setNeutralButton("OK",null)
                .show();
    }

    public void goToCashTransferActivity(View v){
        Intent intent = new Intent(SavingsActivity.this, CashTransferActivity.class);
        startActivity(intent);
    }

    public void test(View v){
        ScrollView sV = findViewById(R.id.objectives_views);
        TextView t = new TextView(this);
        t.setText("test");
        sV.addView(t);
    }
}
