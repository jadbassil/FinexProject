package com.example.jb.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SavingsActivity extends AppCompatActivity {

    EditText totalSaving;
    ListView list;
    LinearLayout objectivesLay;
    JSONObject obj;
    CustomObjectivesAdapter adapter;
    public  ArrayList<ObjectivesListModel> CustomListViewValuesArr = new ArrayList<ObjectivesListModel>();


    private String[] l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        list = findViewById(R.id.objectives_list);
        obj = getUserObjectives();
        setList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(resultCode);
        if(resultCode == RESULT_OK)
            this.recreate();
    }

    public void goToCashTransferActivity(View v){
        Intent intent = new Intent(SavingsActivity.this, CashTransferActivity.class);
        startActivity(intent);
    }

    public void goToSavingAmountActivity(View v){
        this.finish();
        Intent intent = new Intent(SavingsActivity.this, SavingAmountActivity.class);
        startActivity(intent);
    }

    public  void goToNewObjectiveActivity(View v){
        Intent intent = new Intent(SavingsActivity.this, NewObjectiveActivity.class);
        startActivity(intent);
    }

    public void setList(){
        obj = getUserObjectives();
        //System.out.println(obj.length());

        try {
            JSONArray objectives = obj.optJSONArray("objectives");
            l = new String[objectives.length()];
            for (int i = 0; i < objectives.length(); i++) {
                final ObjectivesListModel l = new ObjectivesListModel();
                JSONObject o = objectives.getJSONObject(i);
                System.out.println(o);
                l.setName(o.getString("name"));
                l.setAmount(o.optString("amount"));
                l.setEndDate(o.optString("date_end"));
                CustomListViewValuesArr.add(l);
            }
            System.out.println(l[0]);
            Resources res =getResources();
            adapter = new CustomObjectivesAdapter(this, CustomListViewValuesArr, res);
            //ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(NewObjectiveActivity.this, android.R.layout.simple_list_item_1, l);
            list.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject getUserObjectives(){
        JSONObject obj;
        class SendToServer extends AsyncTask<String, Void, String>{
            JSONObject obj;

            @Override
            public String doInBackground(String... params){
                SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                int id = sharedPref.getInt("user_id", 0);
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("id",String.valueOf(id)));
                Connect j = new Connect();
                JSONObject o = j.makeHttpRequest("http://10.0.2.2/Finex/getObjectives.php", "POST", list);
                return o.toString();
            }
            @Override
            public void onPostExecute(String result){
                try {
                    obj = new JSONObject(result);
                    obj = new JSONObject(result);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        SendToServer s = new SendToServer();
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

}

