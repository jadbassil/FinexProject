package com.example.jb.project;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;


public class BalanceActivity extends AppCompatActivity {
    @SuppressLint("RestrictedApi")

    EditText balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        balance = findViewById(R.id.balance_val);
    }

    public void showDesc(View v){
        new AlertDialog.Builder(this)
                .setTitle("What is Balance ? ")
                .setMessage("The balance is the total amount of money you have. \n" +
                        "Your monthly salary is added to your balance  and the expenses are deducted from it ")
                .setNeutralButton("Ok", null)
                .show();
    }

    public void openAddIncome(View v){
        Intent intent = new Intent(BalanceActivity.this,AddingAmount.class);
        startActivity(intent);
    }

    public void setTotalBalance(View v){
        if(balance.getText().toString().equals("")) {
            Toast.makeText(this, "Field is not set", Toast.LENGTH_SHORT).show();
            return;
        }
        final int total = Integer.valueOf(balance.getText().toString());
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
            String total = balance.getText().toString();
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
                editor.putString("balance", balance.getText().toString());
                editor.apply();
                Toast.makeText(getApplicationContext(), obj.getString("message").toString(), Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}

