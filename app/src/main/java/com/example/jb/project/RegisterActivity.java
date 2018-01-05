package com.example.jb.project;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText regName;
    EditText regEmail;
    EditText regPassword;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regName = findViewById(R.id.name);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);

    }

    public void register(View v) {
        String name = regName.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();
        if(name.equals("") || password.equals("") || email.equals("")){
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        } else {
            SendDataToServer(name, email, password);
        }
    }

    public void SendDataToServer(final String name, final String email, final String password){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String QuickName = name ;
                String QuickEmail = email ;
                String QuickPassword = password;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", QuickName));
                nameValuePairs.add(new BasicNameValuePair("email", QuickEmail));
                nameValuePairs.add(new BasicNameValuePair("password", QuickPassword));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost("http://192.168.0.103/Finex/register.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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

                } catch (Exception e){
                    //Log.v(TAG,e.toString());
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                System.out.println(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    if(obj.getBoolean("success")){
                        Intent intent = new Intent(getApplicationContext(), SpendingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Email already used", Toast.LENGTH_LONG).show();
                        regName.setText("");
                        regEmail.setText("");
                        regPassword.setText("");
                    }

                } catch (JSONException e) {

                }


            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name, email, password);
    }

}




