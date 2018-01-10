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
        totalSaving = findViewById(R.id.total_savings);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        totalSaving.setHint(sharedPref.getString("savings",""));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        totalSaving.setHint(sharedPref.getString("savings",""));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void showDesc(View v){
        new AlertDialog.Builder(this)
                .setTitle("What is Total savings ?")
                .setMessage("If you already have previous savings put their value here so you can add new savings")
                .setNeutralButton("OK",null)
                .show();
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

    public void setTotalSavings(View v){
        if(totalSaving.getText().toString().equals("")) {
            Toast.makeText(this, "Field is not set", Toast.LENGTH_SHORT).show();
            return;
        }
        final int total = Integer.valueOf(totalSaving.getText().toString());
        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Are you sure you want to change your total savings value")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDataToServer(total);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void sendDataToServer(final int total){
        class SendPostReq extends AsyncTask<String, Void, String> {
            SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
            int id = sharedPref.getInt("user_id", 0);

            @Override
            protected String doInBackground(String... args){
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("id", String.valueOf(id)));
                list.add(new BasicNameValuePair("total", String.valueOf(total)));

                try{
                   Connect j = new Connect();
                   JSONObject obj ;
                   obj = j.makeHttpRequest("http://10.0.2.2/Finex/totalSavings.php", "POST", list);
                   return obj.toString();

                }catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            public void onPostExecute(String result){
                super.onPostExecute(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    Toast.makeText(getApplicationContext(), obj.getString("message").toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        SendPostReq spr = new SendPostReq();
        spr.execute();
    }

}

