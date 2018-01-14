package com.example.jb.project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewObjectiveActivity extends Activity {

    SeekBar sb;
    TextView nbMonths;
    EditText finalAmount, name;
    ListView list;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_objective);
        sb = findViewById(R.id.months_seekbar);
        nbMonths = findViewById(R.id.nb_months);
        finalAmount = findViewById(R.id.final_amount);
        name = findViewById(R.id.objective_name);
        list = findViewById(R.id.objectives_list);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int nbOfMonth = sb.getProgress();
                if (nbOfMonth <= 1)
                    nbMonths.setText(String.valueOf(sb.getProgress()) + " Month");
                else
                    nbMonths.setText(String.valueOf(sb.getProgress()) + " Months");
                if (nbOfMonth > 12) {
                    int nbYear = nbOfMonth / 12;
                    if (nbYear == 1 && nbOfMonth - nbYear * 12 == 1)
                        nbMonths.setText(String.valueOf(nbYear) + " year " + String.valueOf(nbOfMonth - nbYear * 12) + " month");
                    else if (nbYear == 1)
                        nbMonths.setText(String.valueOf(nbYear) + " year " + String.valueOf(nbOfMonth - nbYear * 12) + " months");
                    else if (nbOfMonth - nbYear * 12 == 1)
                        nbMonths.setText(String.valueOf(nbYear) + " years " + String.valueOf(nbOfMonth - nbYear * 12) + " month");
                    else
                        nbMonths.setText(String.valueOf(nbYear) + " years " + String.valueOf(nbOfMonth - nbYear * 12) + " months");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void plusClicked(View v){
        sb.setProgress(sb.getProgress()+1);
    }

    public void minusClicked(View v){
        sb.setProgress(sb.getProgress()-1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(NewObjectiveActivity.this, SavingsActivity.class);
        startActivity(i);

    }

    public void addObjective(View v){
        if(name.getText().toString().equals("")){
            Toast.makeText(this, "Please choose a name for your objective", Toast.LENGTH_SHORT).show();
            return;
        }
        if(finalAmount.getText().toString().equals("")){
            Toast.makeText(this, "Please input your final amount", Toast.LENGTH_SHORT).show();
            return;
        }
        if(sb.getProgress() == 0){
            Toast.makeText(this, "Please input the final delay for your objective", Toast.LENGTH_SHORT).show();
            return;
        }
        sendDataToServer();
        Intent i = new Intent(NewObjectiveActivity.this, SavingsActivity.class);
        startActivity(i);
    }

    public void sendDataToServer(){
        class SendData extends AsyncTask<String, Void, String>{
            @Override
            public String doInBackground(String... params){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                int months = sb.getProgress();
                String objName = name.getText().toString();
                String amount = finalAmount.getText().toString();
                SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                date.setMonth(date.getMonth()+months);
                String endDate =dateFormat.format(date);
                int id = sharedPref.getInt("user_id", 0);
                System.out.println(endDate);
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("id", String.valueOf(id)));
                list.add(new BasicNameValuePair("name", objName));
                list.add(new BasicNameValuePair("amount", amount));
                list.add(new BasicNameValuePair("endDate", endDate));

                try{
                    Connect j = new Connect();
                    JSONObject obj;
                    obj = j.makeHttpRequest("http://10.0.2.2/Finex/addObjective.php", "POST", list);
                    return obj.toString();
                } catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            public void onPostExecute(String result){
                super.onPostExecute(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        SendData sd =new SendData();
        sd.execute();
    }



}
