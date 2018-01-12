package com.example.jb.project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JB on 1/11/2018.
 */

public class CustomObjectivesAdapter extends BaseAdapter  {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ObjectivesListModel tempValues=null;
    int i=0;


    public CustomObjectivesAdapter(Activity a, ArrayList d,Resources resLocal){
        activity = a;
        data=d;
        res = resLocal;
        //Layout inflator to call external xml layout ()
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //The size of passed Array list
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    //Create a holder Class to contain inflated xml file elements
    public static class ViewHolder{
        public TextView nameTextView;
        public TextView amountTextView;
        public TextView endDateTextView;
        public ImageView delete;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.objectivetabitem, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.nameTextView = vi.findViewById(R.id.objective_name_list);
            holder.amountTextView = vi.findViewById(R.id.objective_amount_list);
            holder.endDateTextView = vi.findViewById(R.id.objective_endDate_list);
            holder.delete = vi.findViewById(R.id.delete_objective);

            vi.setTag( holder );

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("delete clicked");
                    SendData sd = new SendData(holder.nameTextView.getText().toString());
                    sd.execute();
                 }
                }
            );


            /************  Set holder with LayoutInflater ************/


        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.nameTextView.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( ObjectivesListModel ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.nameTextView.setText( tempValues.getName() );
            holder.amountTextView.setText( tempValues.getAmount() );
            holder.endDateTextView.setText(tempValues.getEndDate());

        }
        return vi;
    }

        class SendData extends AsyncTask<String, Void, String> {
            String name;

            public SendData(String name){
                this.name = name;
            }

            @Override
            protected String doInBackground(String... args) {
                SharedPreferences sharedPref = activity.getSharedPreferences("userInfo", MODE_PRIVATE);
                int id = sharedPref.getInt("user_id", 0);
                Connect c = new Connect();
                ArrayList<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("id",String.valueOf(id)));
                list.add(new BasicNameValuePair("name", name));
                try{
                    JSONObject obj;
                    obj = c.makeHttpRequest("http://10.0.2.2/Finex/deleteObjective.php","POST", list);
                    return obj.toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onPostExecute(String result){
                try {
                    JSONObject obj = new JSONObject(result);
                    if(obj.getBoolean("success")){
                        Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        activity.recreate();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

        }


}
