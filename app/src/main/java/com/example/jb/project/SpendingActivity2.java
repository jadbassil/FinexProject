package com.example.jb.project;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
public class SpendingActivity2 extends AppCompatActivity {
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
