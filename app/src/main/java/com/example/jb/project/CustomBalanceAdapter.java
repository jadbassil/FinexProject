package com.example.jb.project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JB on 1/16/2018.
 */

public class CustomBalanceAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    balanceListModel tempValues=null;
    int i=0;

    public CustomBalanceAdapter(Activity a, ArrayList d,Resources resLocal){
        activity = a;
        data=d;
        res = resLocal;
        //Layout inflator to call external xml layout ()
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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

    public static class ViewHolder{
        public TextView month;
        public TextView incomes;
        public TextView expense;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final CustomBalanceAdapter.ViewHolder holder;
        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.balancetabitem, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new CustomBalanceAdapter.ViewHolder();
            holder.month = vi.findViewById(R.id.month_balance_list);
            holder.incomes = vi.findViewById(R.id.income_balance_list);
            holder.expense = vi.findViewById(R.id.expense_balance_list);
            vi.setTag( holder );



        }
        else
            holder=(CustomBalanceAdapter.ViewHolder)vi.getTag();

        tempValues=null;
        tempValues = ( balanceListModel ) data.get( position );

        holder.month.setText(tempValues.getMonth());
        holder.incomes.setText(tempValues.getIncome());
        holder.expense.setText(tempValues.getExpenses());

        return vi;
    }


}
