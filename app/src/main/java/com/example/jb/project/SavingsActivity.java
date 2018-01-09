package com.example.jb.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SavingsActivity extends AppCompatActivity {

    EditText totalSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        totalSaving = findViewById(R.id.total_savings);

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
        if(totalSaving.getText().toString().equals(""))
            Toast.makeText(this, "Field is not set", Toast.LENGTH_SHORT).show();
        int total = Integer.valueOf(totalSaving.getText().toString());
        sendDataToServer(total);
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
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://10.0.2.2/Finex/totalSavings.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(list));

                    HttpResponse response = httpClient.execute(httpPost);
                    InputStream inputStream = response.getEntity().getContent();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;
                    while((bufferedStrChunk = bufferedReader.readLine()) != null){
                        stringBuilder.append(bufferedStrChunk);
                    }
                    return stringBuilder.toString();

                }catch (Exception e){

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

                }


            }
        }
        SendPostReq spr = new SendPostReq();
        spr.execute();
    }

}

