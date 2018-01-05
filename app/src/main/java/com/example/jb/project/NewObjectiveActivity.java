package com.example.jb.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class NewObjectiveActivity extends AppCompatActivity {

    SeekBar sb;
    TextView nbMonths, amountPerMonth;
    EditText finalAmount, savedAmount;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_objective);
        sb = findViewById(R.id.months_seekbar);
        nbMonths = findViewById(R.id.nb_months);
        amountPerMonth = findViewById(R.id.amount_per_month);
        finalAmount = findViewById(R.id.final_amount);
        savedAmount = findViewById(R.id.saved_amount);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int nbOfMonth = sb.getProgress();
                if(nbOfMonth <= 1)
                    nbMonths.setText(String.valueOf(sb.getProgress())+" Month");
                else
                    nbMonths.setText(String.valueOf(sb.getProgress())+" Months");
                if(nbOfMonth>12){
                    int nbYear = nbOfMonth/12;
                    if (nbYear == 1 && nbOfMonth-nbYear*12 == 1)
                        nbMonths.setText(String.valueOf(nbYear) + " year " + String.valueOf(nbOfMonth - nbYear*12) + " month");
                    else if (nbYear == 1)
                        nbMonths.setText(String.valueOf(nbYear) + " year " + String.valueOf(nbOfMonth - nbYear*12) + " months");
                    else if(nbOfMonth-nbYear*12 == 1)
                        nbMonths.setText(String.valueOf(nbYear) + " years " + String.valueOf(nbOfMonth - nbYear*12) + " month");
                    else
                        nbMonths.setText(String.valueOf(nbYear) + " years " + String.valueOf(nbOfMonth - nbYear*12) + " months");
                }
                if(finalAmount.getText().toString().length() != 0 && savedAmount.getText().toString().length() != 0) {
                    float fa = Float.valueOf(finalAmount.getText().toString());
                    float sa = Float.valueOf(savedAmount.getText().toString());
                    float aPerMonth = (fa - sa) / nbOfMonth;
                    amountPerMonth.setText(String.valueOf(aPerMonth) + " Per Month");
                } else
                    amountPerMonth.setText("(_) Per Month");
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


}
