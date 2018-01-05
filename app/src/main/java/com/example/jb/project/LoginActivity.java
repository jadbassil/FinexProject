package com.example.jb.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView register;
    Button signIn;
    EditText emailE;
    EditText passwordE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        signIn = findViewById(R.id.sign_in);
        emailE =  findViewById(R.id.email);
        passwordE = findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    public void signIn(View v){
        String email = emailE.getText().toString();
        String password = passwordE.getText().toString();
        if(password.equals("") || email.equals("")){
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        } else {
            sendDataToServer(email, password);
        }
    }

    public void sendDataToServer(final String email, final String password){
        class sendPostReq extends AsyncTask<String, Void, String>{
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("email", email));
                list.add(new BasicNameValuePair("password", password));

                try{
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://192.168.0.103/Finex/signIn.php");
                    post.setEntity(new UrlEncodedFormEntity(list));
                    HttpResponse response = client.execute(post);
                    InputStream inputStream = response.getEntity().getContent();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk;
                    while((bufferedStrChunk = bufferedReader.readLine()) != null){
                        stringBuilder.append(bufferedStrChunk);
                    }
                    return stringBuilder.toString();
                } catch(Exception e){
                    System.out.println(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if(obj.getBoolean("success")){
                        //saving user's info locally so we can access them in other activities
                        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("user_id", obj.getInt("id_user"));
                        editor.putString("user_name", obj.getString("name"));
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), SpendingActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                        emailE.setText("");
                        passwordE.setText("");
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }
        sendPostReq spr = new sendPostReq();
        spr.execute();
    }

}
