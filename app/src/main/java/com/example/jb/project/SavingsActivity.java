package com.example.jb.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SavingsActivity extends AppCompatActivity {

    EditText totalSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }



    public void goToCashTransferActivity(View v){
        Intent intent = new Intent(SavingsActivity.this, CashTransferActivity.class);
        startActivity(intent);
    }

    public void goToSavingAmountActivity(View v){
        Intent intent = new Intent(SavingsActivity.this, SavingAmountActivity.class);
        startActivity(intent);
    }

    public  void goToNewObjectiveActivity(View v){
        Intent intent = new Intent(SavingsActivity.this, NewObjectiveActivity.class);
        startActivity(intent);
    }



}

