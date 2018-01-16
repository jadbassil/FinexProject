package com.example.jb.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavingAmountActivity extends AppCompatActivity {
    EditText amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_amount);
        amount = findViewById(R.id.add_saving_amount);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SavingAmountActivity.this, SavingsActivity.class);
        startActivity(i);
    }

    public void addSaving(View v){
        class SendData extends AsyncTask<String, Void, String>{
            SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
            @Override
            protected String doInBackground(String... strings) {
                int id =sharedPref.getInt("user_id", 0);
                ArrayList<NameValuePair> list = new ArrayList<>(0);
                list.add(new BasicNameValuePair("id", String.valueOf(id)));
                list.add(new BasicNameValuePair("amount", amount.getText().toString()));
                Connect c = new Connect();
                JSONObject obj;
                obj = c.makeHttpRequest("http://10.0.2.2/Finex/addSaving.php", "POST", list);
                System.out.println(obj);
                return obj.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("savings", obj.getString("savings"));
                    editor.apply();
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        SendData s = new SendData();
        s.execute();
    }
}
