package com.example.jb.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Expenses  extends AppCompatActivity {
    String item1;
    String item2;
    ListView list;
    ListView list1;
    TextView expense;
    TextView txt;
    String []type={"Me","Restaurant","Car","Entertainment","Home","Education","Devices","Gifts","Children","Subscriptions"};
    Integer[] imageId={R.drawable.me,R.drawable.resto,R.drawable.car,R.drawable.entert,R.drawable.home,R.drawable.education,R.drawable.devices,R.drawable.gifts,R.drawable.children,R.drawable.subs};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        list = (ListView) findViewById(R.id.primaryExp);
        list1 = (ListView) findViewById(R.id.secondaryExp);
        expense = (TextView) findViewById(R.id.expense);
        txt=(TextView)findViewById(R.id.txt);
    }
    public void showlists(View v){
        PrimaryList adapter=new PrimaryList(Expenses.this,type,imageId);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item1=list.getItemAtPosition(position).toString();
                switch(item1){
                    case "Me":{
                        String []type={"Shave","Mobile bill","Clothes","Therapy","Perfume","Shoes","Glasses","Wallet","Bag","Photography"};
                        Integer[] imageId={R.drawable.scissors,R.drawable.phone,R.drawable.jumper,R.drawable.doctorsbag,R.drawable.spray,R.drawable.shoes,R.drawable.glasses,R.drawable.wallet,R.drawable.bag,R.drawable.camera};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }
                    case "Restaurant":{
                        String []type={"Coffee Shop","Breakfast","Lunch","Dinner","Sweets"};
                        Integer[] imageId={R.drawable.coffee,R.drawable.breakfast,R.drawable.lunch,R.drawable.dinner,R.drawable.sweet};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }
                    case "Car":{
                        String []type={"Fuel","Fine","Oil","Car Wash","Spare Parts","Maintenance","Accessories","Rent","Insurance","Driving License","Parking"};
                        Integer[] imageId={R.drawable.fuel,R.drawable.siren,R.drawable.oil,R.drawable.carwash,R.drawable.services,R.drawable.maintenance,R.drawable.access,R.drawable.money,R.drawable.carinsurance,R.drawable.license,R.drawable.parking};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }
                    case "Entertainment":{
                        String []type={"Walk","Amusement Park","Games","Movies","NightClubs"};
                        Integer[] imageId={R.drawable.walk,R.drawable.balloon,R.drawable.game,R.drawable.movie,R.drawable.night};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }
                    case "Home":{
                        String []type={"Home Supplies","Furniture","Rent","Internet","Electricity","Phone","Water","Gas","Maintenance"};
                        Integer[] imageId={R.drawable.market,R.drawable.furniture,R.drawable.money,R.drawable.wifi,R.drawable.elec,R.drawable.telephone,R.drawable.water,R.drawable.gas,R.drawable.hammer};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }
                    case "Education":{
                        String []type={"Books","Training Course","Tuition fees","Printing","Pens","Notebooks","Backpack"};
                        Integer[] imageId={R.drawable.book,R.drawable.course,R.drawable.tuition,R.drawable.print,R.drawable.pen,R.drawable.noteb,R.drawable.backpk};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }
                    case "Devices":{
                        String []type={"Phone","Tablet","Computer","Printer","Camera","Sports","Accessories"};
                        Integer[] imageId={R.drawable.phone,R.drawable.tablet,R.drawable.computer,R.drawable.print,R.drawable.camera,R.drawable.sport,R.drawable.access};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }
                    case "Children":{
                        String []type={"Clothes","Pocket money","Therapy","Games","Amusement Park","Diapers","Milk","School Furniture"};
                        Integer[] imageId={R.drawable.jumper,R.drawable.money,R.drawable.doctorsbag,R.drawable.game,R.drawable.balloon,R.drawable.nappy,R.drawable.milk,R.drawable.book};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }
                    case "Subscriptions":{
                        String []type={"Gym","Channels","Magazine","NewsPaper","Web"};
                        Integer[] imageId={R.drawable.gym,R.drawable.channel,R.drawable.magazine,R.drawable.newspaper,R.drawable.web};
                        PrimaryList adpater1=new PrimaryList(Expenses.this,type,imageId);
                        list1.setAdapter(adpater1);
                        break;
                    }

                }
            }
        });
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item2=list1.getItemAtPosition(position).toString();
                expense.setText(" "+ item1 +"/"+ item2);
            }
        });

    }
}
