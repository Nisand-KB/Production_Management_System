package com.example.dell.pms;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class Month extends AppCompatActivity {
    CardView c1;
    Button b1;
    TextView q1,q2,q3,q8,q9;
    Spinner dropdown,drop2,drop3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month);


        drop2 = (Spinner) findViewById(R.id.sp1);
        String[] items11 = new String[]{"01", "02", "03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<String> adapter11 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items11);
        drop2.setAdapter(adapter11);

        drop3 = (Spinner) findViewById(R.id.sp2);
        String[] items12 = new String[]{"2018", "2017", "2016","below 2016"};
        ArrayAdapter<String> adapter12 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items12);
        drop3.setAdapter(adapter12);


        b1= (Button) findViewById(R.id.b2);
        c1= (CardView) findViewById(R.id.card1);
       // q8= (TextView) findViewById(R.id.ms);
        q9= (TextView) findViewById(R.id.mm);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1.setVisibility(View.VISIBLE);
               // String t1=dropdown.getSelectedItem().toString();
                String t2=drop2.getSelectedItem().toString();
                String t3=drop3.getSelectedItem().toString();
               /// q8.setText(t1);
                q9.setText(t2+"-"+t3);
                q1 = (TextView) findViewById(R.id.total);
                q2 = (TextView) findViewById(R.id.good);
                q3 = (TextView) findViewById(R.id.bad);
                if(t3.equals("2018") && (t2.equals("04"))) {
                    String firsturl = "https://api.thingspeak.com/channels/464813/feeds.json?api_key=GPNLWRT2NAET0VKD&results=2880";
                    Thread thread = new Thread(new HTTPCaller(firsturl));
                    try {
                        thread.start();
                        thread.join();
                    } catch (Exception exp) {
                        exp.printStackTrace();
                    }

                    int total = HTTPCaller.good + HTTPCaller.bad;

                    q1.setText("" + total);
                    q2.setText("" + HTTPCaller.good);
                    q3.setText("" + HTTPCaller.bad);
                    HTTPCaller.good = 1;
                    HTTPCaller.bad = 0;

                }else{
                    q1.setText("N/A");
                    q2.setText("N/A");
                    q3.setText("N/A");
                }


            }
        });
    }
}
