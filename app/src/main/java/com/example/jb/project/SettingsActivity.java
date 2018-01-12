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

public class SettingsActivity extends AppCompatActivity {
    EditText totalSaving;
    EditText totalBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        totalSaving = findViewById(R.id.total_savings);
        totalBalance = findViewById(R.id.balance_val);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        totalSaving.setHint(sharedPref.getString("savings",""));
        totalBalance.setHint(sharedPref.getString("balance",""));
    }
    public void showSavingsDesc(View v){
        new AlertDialog.Builder(this)
                .setTitle("What is Total savings ?")
                .setMessage("If you already have previous savings put their value here so you can add new savings")
                .setNeutralButton("OK",null)
                .show();
    }

    public void showBalanceDesc(View v){
        new AlertDialog.Builder(this)
                .setTitle("What is Balance ? ")
                .setMessage("The balance is the total amount of money you have. \n" +
                        "Your income is added to your balance  and the expenses are deducted from it ")
                .setNeutralButton("Ok", null)
                .show();
    }

    public void logOut(View v){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        totalSaving.setHint(sharedPref.getString("savings",""));
        totalBalance.setHint(sharedPref.getString("balance",""));
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
                    SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("savings", totalSaving.getText().toString());
                    editor.apply();
                    Toast.makeText(getApplicationContext(), obj.getString("message").toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        SendPostReq spr = new SendPostReq();
        spr.execute();
    }

    public void setTotalBalance(View v){
        if(totalBalance.getText().toString().equals("")) {
            Toast.makeText(this, "Field is not set", Toast.LENGTH_SHORT).show();
            return;
        }
        final int total = Integer.valueOf(totalBalance.getText().toString());
        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Are you sure you want to change your total balance value")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendData sd = new sendData();
                        sd.execute();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    class sendData extends AsyncTask<String, Void, String>{
        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        int id = sharedPref.getInt("user_id", 0);
        @Override
        protected String doInBackground(String... params){
            String total = totalBalance.getText().toString();
            ArrayList<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("id",String.valueOf(id)));
            list.add(new BasicNameValuePair("total", total));

            Connect c = new Connect();

            try{
                JSONObject obj;
                obj = c.makeHttpRequest("http://10.0.2.2/Finex/totalBalance.php", "POST", list);
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
                SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("balance", totalBalance.getText().toString());
                editor.apply();
                Toast.makeText(getApplicationContext(), obj.getString("message").toString(), Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
