package com.example.jb.project;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CashTransferActivity extends AppCompatActivity {

    EditText transferAmount;
    Spinner transferFrom;
    Spinner transferTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_transfer);
        transferAmount = findViewById(R.id.transfer_amount);
        transferFrom = findViewById(R.id.transfer_from);
        transferTo = findViewById(R.id.transfer_to);
    }

    public void transfer(View v){
        if(transferAmount.getText().toString().matches("")) {
            Toast.makeText(this, "Please insert an amount", Toast.LENGTH_SHORT).show();
            return;
        }
        if(transferFrom.getSelectedItem().toString().matches("")) {
            Toast.makeText(this, "Please insert the from account", Toast.LENGTH_SHORT).show();
            return;
        }
        if(transferTo.getSelectedItem().toString().matches("")) {
            Toast.makeText(this, "Please insert the to account", Toast.LENGTH_SHORT).show();
            return;
        }
        if(transferFrom.getSelectedItem().toString() == transferTo.getSelectedItem().toString()){
            Toast.makeText(this, "Accounts should be different",Toast.LENGTH_SHORT).show();
            return;
        }
        sendDataToServer();
    }

    public void sendDataToServer(){
        class sendData extends AsyncTask<String, Void, String>{
            @Override
            public String doInBackground(String... params){
                SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                int id = sharedPref.getInt("user_id", 0);
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("id", String.valueOf(id)));
                list.add(new BasicNameValuePair("amount", transferAmount.getText().toString()));
                list.add(new BasicNameValuePair("from", transferFrom.getSelectedItem().toString()));
                list.add(new BasicNameValuePair("to", transferTo.getSelectedItem().toString()));
                try{
                    Connect j = new Connect();
                    JSONObject obj;
                    obj = j.makeHttpRequest("http://10.0.2.2/Finex/cashTransfer.php", "POST", list);
                    return obj.toString();
                } catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onPostExecute(String result){
                if(result == null){
                    Toast.makeText(getApplicationContext(), "Could not transfer", Toast.LENGTH_SHORT).show();
                } else {
                    transferAmount.setText("");
                    Toast.makeText(getApplicationContext(), "Cash transferred successfully", Toast.LENGTH_SHORT).show();
                    try{
                        JSONObject obj = new JSONObject(result);
                        SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("balance", obj.getString("balance"));
                        editor.putString("savings", obj.getString("savings"));
                        editor.apply();
                        System.out.println(obj);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }
        sendData sd = new sendData();
        sd.execute();
    }

}
