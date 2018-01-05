package com.example.jb.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class SpendingActivity extends AppCompatActivity {

    String currentDate = DateFormat.getDateInstance().format(new Date());
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        date = findViewById(R.id.date);
        date.setText(currentDate);

    }

    public void goToSavingActivity(View v){
        Intent intent = new Intent(SpendingActivity.this, SavingsActivity.class);
        startActivity(intent);
    }

    public void goToSettingsActivity(View v){
        Intent intent = new Intent(SpendingActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
