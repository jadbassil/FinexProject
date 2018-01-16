package com.example.jb.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class SpendingActivity extends AppCompatActivity {
    Button balance;
    Button savings;
    TextView day;
    TextView month;
    TextView range;
    TextView daysleft;
    TextView spendingMonth;
    TextView residualMonth;
    String mois;
    Calendar c = Calendar.getInstance();
    int totaldays;
    int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    int yr=c.get(Calendar.YEAR);
    int m=c.get(Calendar.MONTH);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        spendingMonth = findViewById(R.id.spending_month);
        residualMonth = findViewById(R.id.residual_month);
        balance = findViewById(R.id.balance_btn);
        savings = findViewById(R.id.savings_btn);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        balance.setText(sharedPref.getString("balance", "_"));
        savings.setText(sharedPref.getString("savings", "_"));

        if(balance.getText().toString().equals("0") && savings.getText().toString().equals("0")){
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("Your balance and savings accounts are empty please visit your profile to initialize your accounts")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(SpendingActivity.this, SettingsActivity.class);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("later", null)
                    .show();
        }

        day = findViewById(R.id.day);
        month=findViewById(R.id.month);
        range=findViewById(R.id.range);
        daysleft=findViewById(R.id.daysleft);
        totaldays=c.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setText(""+dayOfMonth);
        int day_int=Integer.parseInt(day.getText().toString());
        daysleft.setText(totaldays-day_int+" days left");

        Bundle chosenMonth = this.getIntent().getExtras();
        if(chosenMonth!=null) {
            mois=chosenMonth.getString("month");
            m=getIntFromMonth(mois);
            yr=Integer.valueOf(chosenMonth.getString("year"));
            daysleft.setText("");
        }
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
        getMonthlyData();
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

    private static boolean isLeapYear(final int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public void listitems(View v){

    }

    public void openBalanceAct(View v){
        Intent intent = new Intent(SpendingActivity.this,BalanceActivity.class);
        startActivity(intent);
    }

    public void openSavingsAct(View v){
        this.finish();
        Intent intent = new Intent(SpendingActivity.this,SavingsActivity.class);
        startActivity(intent);
    }

    public void openMonths(View v){
        this.finish();
        Intent intent = new Intent(SpendingActivity.this,Months.class);
        startActivity(intent);
    }

    public void openExpenses(View v){
        Intent intent = new Intent(SpendingActivity.this,Expenses.class);
        startActivity(intent);
    }


   @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Intent i = new Intent(SpendingActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getMonthlyData(){
        getData g = new getData();
        g.execute();
    }

    class getData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params){
            SharedPreferences sharedPref = getSharedPreferences("userInfo",MODE_PRIVATE);
            int id = sharedPref.getInt("user_id",0);

            ArrayList<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("id",String.valueOf(id)));
            list.add(new BasicNameValuePair("month", String.valueOf(m+1)));
            System.out.println(list);

            try{
                Connect c = new Connect();
                JSONObject obj;
                obj = c.makeHttpRequest("http://10.0.2.2/Finex/getMonthlyData.php", "POST", list);
                System.out.println(obj);
                return obj.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String result){
            try{
                JSONObject obj = new JSONObject(result);
                spendingMonth.setText(obj.getString("spendings"));
                residualMonth.setText(obj.getString("residualIncome"));

                if(spendingMonth.getText().toString().equals("null"))
                    spendingMonth.setText("_");

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public int getIntFromMonth(String month){
        switch (month){
            case "January": return 0;
            case "February": return 1;
            case "March": return 2;
            case "April": return 3;
            case "May": return 4;
            case "June": return 5;
            case "July": return 6;
            case "August": return 7;
            case "September": return 8;
            case "October": return 9;
            case "November": return 10;
            case "December": return 11;
        }
        return -1;
    }
}
