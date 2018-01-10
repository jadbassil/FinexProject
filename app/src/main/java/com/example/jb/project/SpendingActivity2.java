package com.example.jb.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class SpendingActivity2 extends AppCompatActivity {
    Button balance;
    Button savings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        balance = findViewById(R.id.balance_btn);
        savings = findViewById(R.id.savings_btn);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        balance.setText(sharedPref.getString("balance", "_"));
        savings.setText(sharedPref.getString("savings", "_"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        balance.setText(sharedPref.getString("balance", "_"));
        savings.setText(sharedPref.getString("savings", "_"));
    }


    public void listitems(View v){

    }

    public void openBalanceAct(View v){
        Intent intent = new Intent(SpendingActivity2.this,BalanceActivity.class);
        startActivity(intent);
    }

    public void openMonths(View v){
        Intent intent = new Intent(SpendingActivity2.this,Months.class);
        startActivity(intent);
    }


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.search:
                //my code here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    } */
}
