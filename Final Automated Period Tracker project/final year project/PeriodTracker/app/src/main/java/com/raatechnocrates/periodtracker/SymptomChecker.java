package com.raatechnocrates.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SymptomChecker extends AppCompatActivity {

    RadioButton A1,A2,A3,A4,A5,A6;
    RadioGroup G1,G2,G3,G4,G5,G6;
    TextView Q2,Q3,Q4,Q5,Q6;

    LinearLayout ll2,ll3,ll4,ll5,ll6;

    Button bCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_checker);

        getSupportActionBar().setTitle("PCOS Symptom Checker");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        G1 = (RadioGroup)findViewById(R.id.G1);
        G2 = (RadioGroup)findViewById(R.id.G2);
        G3 = (RadioGroup)findViewById(R.id.G3);
        G4 = (RadioGroup)findViewById(R.id.G4);
        G5 = (RadioGroup)findViewById(R.id.G5);
        G6 = (RadioGroup)findViewById(R.id.G6);

        Q2 = (TextView)findViewById(R.id.Q2);
        Q3 = (TextView)findViewById(R.id.Q3);
        Q4 = (TextView)findViewById(R.id.Q4);
        Q5 = (TextView)findViewById(R.id.Q5);
        Q6 = (TextView)findViewById(R.id.Q6);

        ll2 = (LinearLayout)findViewById(R.id.ll2);
        ll3 = (LinearLayout)findViewById(R.id.ll3);
        ll4 = (LinearLayout)findViewById(R.id.ll4);
        ll5 = (LinearLayout)findViewById(R.id.ll5);
        ll6 = (LinearLayout)findViewById(R.id.ll6);

        bCheck = (Button)findViewById(R.id.btnCheck);
    }

    public void check_click(View v) {
        int selectedId1 = G1.getCheckedRadioButtonId();
        int selectedId2 = G2.getCheckedRadioButtonId();
        int selectedId3 = G3.getCheckedRadioButtonId();
        int selectedId4 = G4.getCheckedRadioButtonId();
        int selectedId5 = G5.getCheckedRadioButtonId();
        int selectedId6 = G6.getCheckedRadioButtonId();

        A1 = (RadioButton) findViewById(selectedId1);
        A2 = (RadioButton) findViewById(selectedId2);
        A3 = (RadioButton) findViewById(selectedId3);
        A4 = (RadioButton) findViewById(selectedId4);
        A5 = (RadioButton) findViewById(selectedId5);
        A6 = (RadioButton) findViewById(selectedId6);


        if(selectedId1==-1){
            Toast.makeText(this,"Please answer question 1", Toast.LENGTH_SHORT).show();
        }
        else if(selectedId2==-1){
            Toast.makeText(this,"Please answer question 2", Toast.LENGTH_SHORT).show();
        }
        else if(selectedId3==-1){
            Toast.makeText(this,"Please answer question 3", Toast.LENGTH_SHORT).show();
        }
        else if(selectedId4==-1){
            Toast.makeText(this,"Please answer question 4", Toast.LENGTH_SHORT).show();
        }
        else if(selectedId5==-1){
            Toast.makeText(this,"Please answer question 5", Toast.LENGTH_SHORT).show();
        }
        else if(selectedId6==-1){
            Toast.makeText(this,"Please answer question 6", Toast.LENGTH_SHORT).show();
        }
        else{
            String ans1 = A1.getText().toString();
            String ans2 = A2.getText().toString();
            String ans3 = A3.getText().toString();
            String ans4 = A4.getText().toString();
            String ans5 = A5.getText().toString();
            String ans6 = A6.getText().toString();



            double total=0;
            //Q1
            if (ans1.equalsIgnoreCase("Yes"))
            {
                total=total+2;
            }
            else if (ans1.equalsIgnoreCase("Can't Say"))
            {
                total=total+1;
            }
            else
            {
                total=total+0;
            }
            //Q2
            if (ans2.equalsIgnoreCase("Yes"))
            {
                total=total+2;
            }
            else if (ans2.equalsIgnoreCase("Can't Say"))
            {
                total=total+1;
            }
            else
            {
                total=total+0;
            }
            //Q3
            if (ans3.equalsIgnoreCase("Yes"))
            {
                total=total+2;
            }
            else if (ans3.equalsIgnoreCase("Can't Say"))
            {
                total=total+1;
            }
            else
            {
                total=total+0;
            }
            //Q4
            if (ans4.equalsIgnoreCase("Yes"))
            {
                total=total+2;
            }
            else if (ans4.equalsIgnoreCase("Can't Say"))
            {
                total=total+1;
            }
            else
            {
                total=total+0;
            }
            //Q5
            if (ans5.equalsIgnoreCase("Yes"))
            {
                total=total+2;
            }
            else if (ans5.equalsIgnoreCase("Can't Say"))
            {
                total=total+1;
            }
            else
            {
                total=total+0;
            }
            //Q6
            if (ans6.equalsIgnoreCase("Yes"))
            {
                total=total+2;
            }
            else if (ans6.equalsIgnoreCase("Can't Say"))
            {
                total=total+1;
            }
            else
            {
                total=total+0;
            }

            double percentage = (total/12)*100;
            String pcos_per = String.format("%.2f", percentage);
            String add_inf = "";
            if (percentage>50)
            {
                add_inf = " Please consult a doctor at the earliest.";
            }

            String message = "You have " + pcos_per + "% possiblity of PCOS symptoms." + add_inf;

            if (percentage>50)
            {
                message = "You have high chance (" + percentage + "%) of having PCOS";
            }
            else
            {
                message = "You have low chance (" + percentage + "%) of having PCOS";
            }

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

            builder1.setMessage(message + add_inf);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Okay",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    public void rg1_click(View v) {
        Q2.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
    }
    public void rg2_click(View v) {
        Q3.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);
    }
    public void rg3_click(View v) {
        Q4.setVisibility(View.VISIBLE);
        ll4.setVisibility(View.VISIBLE);
    }
    public void rg4_click(View v) {
        Q5.setVisibility(View.VISIBLE);
        ll5.setVisibility(View.VISIBLE);
    }
    public void rg5_click(View v) {
        Q6.setVisibility(View.VISIBLE);
        ll6.setVisibility(View.VISIBLE);
    }
    public void rg6_click(View v) {
        bCheck.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}