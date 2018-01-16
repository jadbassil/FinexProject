package com.example.jb.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class BalanceActivity extends AppCompatActivity {
    @SuppressLint("RestrictedApi")
    ListView log;
    CustomBalanceAdapter adapter;
    JSONObject obj;
    private String[] l;
    public  ArrayList<balanceListModel> CustomListViewValuesArr = new ArrayList<balanceListModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        log = findViewById(R.id.logs_views);
        setList();
    }



    public void openAddIncome(View v){
        Intent intent = new Intent(BalanceActivity.this,AddingAmount.class);
        startActivity(intent);
    }

    public void setList() {
        obj = getUserBalance();
        System.out.println(obj);
        try {
            JSONArray arr = obj.optJSONArray("balances");
            l = new String[arr.length()];
            for(int i=0;i<arr.length();i++){final balanceListModel l = new balanceListModel();
               JSONObject o = arr.getJSONObject(i);
               String month =monthToString(o.getInt("month")-1);
               l.setMonth(month + " " + o.getInt("year"));
               l.setExpenses(o.getString("spendings"));
               l.setIncome(o.getString("revenues"));
               CustomListViewValuesArr.add(l);
            }
            Resources res =getResources();
            adapter = new CustomBalanceAdapter(this, CustomListViewValuesArr, res);
            //ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(NewObjectiveActivity.this, android.R.layout.simple_list_item_1, l);
            log.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getUserBalance() {
        JSONObject obj;
        class SendData extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                int id = sharedPreferences.getInt("user_id", 0);
                ArrayList<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("id", String.valueOf(id)));
                Connect c = new Connect();
                JSONObject obj = c.makeHttpRequest("http://10.0.2.2/Finex/getBalanceItems.php", "POST", list);

                return obj.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
               /* JSONArray arr = obj.optJSONArray("balances");
                ArrayList<balanceListModel> list = new ArrayList<>();
                for(int i=0;i<obj.getInt("numberRows");i++){
                    balanceListModel m = new balanceListModel();
                   JSONObject o = arr.getJSONObject(i);
                   m.setMonth(o.getString("month") + " " + o.getInt("year"));
                   m.setExpenses(o.getString("spendings"));
                   m.setIncome(o.getString("revenues"));
                   list.add(m);
                }
                Resources res =getResources();
                adapter = new CustomBalanceAdapter(, list, res);
                //ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(NewObjectiveActivity.this, android.R.layout.simple_list_item_1, l);
                log.setAdapter(adapter);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        SendData s = new SendData();
        try {
            s.execute();
            String result= s.get();
            obj = new JSONObject(result);
            return obj;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String monthToString(int m){
        String mois = "";
        switch(m){
            case 0:{
                mois="January";
                break;
            }
            case 1:{
                mois="February";
                break;
            }
            case 2:{
                mois="March";
                break;
            }
            case 3:{
                mois="April";
                break;
            }
            case 4:{
                mois="May";
                break;
            }
            case 5:{
                mois="June";
                break;
            }
            case 6:{
                mois="July";
                break;
            }
            case 7:{
                mois="August";
                break;
            }
            case 8:{
                mois="September";
                break;
            }
            case 9:{
                mois="October";
                break;
            }
            case 10:{
                mois="November";
                break;
            }
            case 11:{
                mois="December";
                break;
            }
        }
        return mois;
    }
}

