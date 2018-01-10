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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
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
                Connect j = new Connect();
                JSONObject obj;
                try {
                    obj = j.makeHttpRequest("http://10.0.2.2/Finex/signIn.php", "POST", list);
                    return obj.toString();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Check your connection",Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    System.out.println(obj.toString());
                    if(obj.getBoolean("success")){
                        //saving user's info locally so we can access them in other activities
                        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("user_id", obj.getInt("id_user"));
                        editor.putString("user_name", obj.getString("name"));
                        editor.putString("savings", obj.getString("savings"));
                        editor.putString("balance", obj.getString("balance"));
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
