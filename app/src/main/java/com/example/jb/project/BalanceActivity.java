package com.example.jb.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class BalanceActivity extends AppCompatActivity {
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void showDesc(View v){
        new AlertDialog.Builder(this)
                .setTitle("What is Balance ? ")
                .setMessage("The balance is the total amount of money you have. \n" +
                        "Your monthly salary is added to your balance  and the expenses are deducted from it ")
                .setNeutralButton("Ok", null)
                .show();
    }

    public void openAddIncome(View v){
        Intent intent = new Intent(BalanceActivity.this,AddingAmount.class);
        startActivity(intent);
    }
}

