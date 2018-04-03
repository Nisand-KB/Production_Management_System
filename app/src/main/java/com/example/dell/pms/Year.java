package com.example.dell.pms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Year extends AppCompatActivity {
    Spinner drop2,dropdown1;
    CardView c1;
    Button b1;
    TextView t1,q1,q2,q3,q4,q5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.year);
        dropdown1 = (Spinner) findViewById(R.id.date5);
        String[] items1 = new String[]{"Week 1", "Week 2", "Week 3","Week 4"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);
        drop2 = (Spinner) findViewById(R.id.date3);
        String[] items11 = new String[]{"01", "02", "03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<String> adapter11 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items11);
        drop2.setAdapter(adapter11);
       // q4= (TextView) findViewById(R.id.shift);
        q5= (TextView) findViewById(R.id.shift1);
        b1= (Button) findViewById(R.id.b2);
        c1= (CardView) findViewById(R.id.card1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1.setVisibility(View.VISIBLE);
                String text=drop2.getSelectedItem().toString();
             String text3=dropdown1.getSelectedItem().toString();
               // q4.setText(text);
                q5.setText(text+"  "+text3);
                q1 = (TextView) findViewById(R.id.total);
                q2 = (TextView) findViewById(R.id.good);
                q3 = (TextView) findViewById(R.id.bad);
                if(text3.equals("Week 1") && ((text.equals("04")))) {
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


                }else {
                    q1.setText("N/A");
                    q2.setText("N/A");
                    q3.setText("N/A");
                }

            }
        });
    }

}
