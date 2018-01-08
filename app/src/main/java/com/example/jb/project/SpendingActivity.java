package com.example.jb.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class SpendingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_moe);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void listitems(View v){

    }

    public void openBalanceAct(View v){
        Intent intent = new Intent(SpendingActivity.this,SavingsActivity.class);
        startActivity(intent);
    }

    public void openMonths(View v){
        Intent intent = new Intent(SpendingActivity.this,Months.class);
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
