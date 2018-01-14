package com.example.jb.project;


import android.content.Intent;
import android.os.AsyncTask;
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
            //SendDataToServer(name, email, password);

            Connect j = new Connect();
            JSONObject obj;
            class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
                Connect j = new Connect();
                JSONObject obj;
                String name = regName.getText().toString();
                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();

                @Override
                protected String doInBackground(String... params) {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("name", name));
                    nameValuePairs.add(new BasicNameValuePair("email", email));
                    nameValuePairs.add(new BasicNameValuePair("password", password));
                    obj=j.makeHttpRequest("http://10.0.2.2/Finex/register.php", "POST", nameValuePairs);
                    return obj.toString();
                }
                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    try {
                        JSONObject obj = new JSONObject(result);
                        if(obj.getBoolean("success")){
                            Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Email already used", Toast.LENGTH_LONG).show();
                            regName.setText("");
                            regEmail.setText("");
                            regPassword.setText("");
                        }
                    } catch (Exception e){

                    }
                }
            }
            SendPostReqAsyncTask spr = new SendPostReqAsyncTask();
            spr.execute();
        }
    }
}




