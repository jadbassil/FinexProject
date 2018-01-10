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
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class SpendingActivity2 extends AppCompatActivity {
    TextView day;
    TextView month;
    TextView range;
    TextView daysleft;
    String mois;
    int totaldays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_moe);
        day=(TextView)findViewById(R.id.day);
        month=(TextView)findViewById(R.id.month);
        range=(TextView)findViewById(R.id.range);
        daysleft=(TextView)findViewById(R.id.daysleft);
        Calendar c = Calendar.getInstance();
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int yr=c.get(Calendar.YEAR);
        int m=c.get(Calendar.MONTH);
        day.setText(""+dayOfMonth);
        switch(m){
            case 0:{
                mois="January";
                range.setText("(from 1/1/"+yr+" to 31/1/"+yr+")");
                break;
            }
            case 1:{
                mois="February";
                if(isLeapYear(yr))
                    range.setText("(from 1/2/" + yr + " to 29/2/" + yr+")");
                else
                    range.setText("(from 1/2/" + yr + " to 28/2/" + yr+")");
                break;
            }
            case 2:{
                mois="March";
                range.setText("(from 1/3/"+yr+" to 31/3/"+yr+")");
                break;
            }
            case 3:{
                mois="April";
                range.setText("(from 1/4/"+yr+" to 30/4/"+yr+")");
                break;
            }
            case 4:{
                mois="May";
                range.setText("(from 1/5/"+yr+" to 31/5/"+yr+")");
                break;
            }
            case 5:{
                mois="June";
                range.setText("(from 1/6/"+yr+" to 30/6/"+yr+")");
                break;
            }
            case 6:{
                mois="July";
                range.setText("(from 1/7/"+yr+" to 31/7/"+yr+")");
                break;
            }
            case 7:{
                mois="August";
                range.setText("(from 1/8/"+yr+" to 31/8/"+yr+")");
                break;
            }
            case 8:{
                mois="September";
                range.setText("(from 1/9/"+yr+" to 30/9/"+yr+")");
                break;
            }
            case 9:{
                mois="October";
                range.setText("(from 1/10/"+yr+" to 31/10/"+yr+")");
                break;
            }
            case 10:{
                mois="November";
                range.setText("(from 1/11/"+yr+" to 30/11/"+yr+")");
                break;
            }
            case 11:{
                mois="December";
                range.setText("(from 1/12/"+yr+"to 31/12/"+yr+")");
                break;
            }
        }
        month.setText(mois+" "+yr);
        totaldays=c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int day_int=Integer.parseInt(day.getText().toString());
        daysleft.setText(totaldays-day_int+" days left");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private static boolean isLeapYear(final int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
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

    public void openSelection(View v){
        Intent intent = new Intent(SpendingActivity2.this,Expenses.class);
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
