package com.example.jb.project;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Months extends AppCompatActivity {

    ListView monthsList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    Date currentDate;
    Date regDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);
        monthsList = findViewById(R.id.months_list);
        SendData s = new SendData();
        s.execute();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Months.this, SpendingActivity.class);
        startActivity(i);
    }

    class SendData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
            int id = sharedPref.getInt("user_id", 0);
            ArrayList<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("id", String.valueOf(id)));
            Connect c = new Connect();
            try {
                JSONObject obj;
                obj = c.makeHttpRequest("http://10.0.2.2/Finex/getRegDate.php", "POST", list);
                return obj.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject obj = new JSONObject(s);
                String rDate = obj.getString("regDate");
                String cDate = dateFormat.format(date);
                try {
                    currentDate = dateFormat.parse(cDate);
                    regDate = dateFormat.parse(rDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int i=0;
                String[] months = new String[currentDate.getMonth()-regDate.getMonth()+1];
                System.out.println(regDate.getMonth());
                while (regDate.compareTo(currentDate) <= 0) {
                    months[i] = new String(monthToString(regDate.getMonth()) + " " + (regDate.getYear()-100+2000));
                    regDate.setMonth(regDate.getMonth()+1);
                    //months.add(monthToString(regDate.getMonth()) +" "+ (regDate.getYear()-100));

                    i++;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.months_list_layout, months);
                monthsList.setAdapter(adapter);
                monthsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getApplicationContext(), SpendingActivity.class);
                        System.out.println(regDate.getYear()-100+2000);
                        i.putExtra("month",position);
                        i.putExtra("year", regDate.getYear()-100+2000);
                        startActivity(i);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

