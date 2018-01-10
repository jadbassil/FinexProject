package com.example.jb.project;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class NewObjectiveActivity extends Activity {

    SeekBar sb;
    TextView nbMonths;
    EditText finalAmount, name;
    ListView list;
    JSONObject obj;


    private String[] l;

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

        obj = getUserObjectives();
        setList();

    }


    public void plusClicked(View v){
        sb.setProgress(sb.getProgress()+1);
    }

    public void minusClicked(View v){
        sb.setProgress(sb.getProgress()-1);
    }

    public void setList(){
        obj = getUserObjectives();
        try {
            JSONArray objectives = obj.optJSONArray("objectives");
            l = new String[objectives.length()];
            for (int i = 0; i < objectives.length(); i++) {
                JSONObject o = objectives.getJSONObject(i);
                String name =   o.optString("name");
                l[i] = o.optString("name");
            }
            System.out.println(l);
            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(NewObjectiveActivity.this, android.R.layout.simple_list_item_1, l);
            list.setAdapter(mArrayAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addObjective(View v){
        if(name.getText().toString().equals("")){
            Toast.makeText(this, "Please choose a name for your objective", Toast.LENGTH_SHORT).show();
            return;
        }
        if(finalAmount.getText().toString()==""){
            Toast.makeText(this, "Please input your final amount", Toast.LENGTH_SHORT).show();
            return;
        }
        if(sb.getProgress() == 0){
            Toast.makeText(this, "Please input the final delay for your objective", Toast.LENGTH_SHORT).show();
            return;
        }
        sendDataToServer();
        setList();
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

    public JSONObject getUserObjectives(){
        JSONObject obj = new JSONObject();
        class SendToServer extends AsyncTask<String, Void, String>{
            JSONObject obj;

            @Override
            public String doInBackground(String... params){
                SharedPreferences sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE);
                int id = sharedPref.getInt("user_id", 0);
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("id",String.valueOf(id)));
                Connect j = new Connect();
                JSONObject o = j.makeHttpRequest("http://10.0.2.2/Finex/getObjectives.php", "POST", list);
                return o.toString();
            }
            @Override
            public void onPostExecute(String result){
                try {
                    obj = new JSONObject(result);
                    obj = new JSONObject(result);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        SendToServer s = new SendToServer();
        try {
            s.execute();
            String result= s.get();
            obj = new JSONObject(result);
            return obj;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
