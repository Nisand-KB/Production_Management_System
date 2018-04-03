package com.example.dell.pms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Today extends AppCompatActivity {
    CardView c1;
    Button b1;
   TextView  t1,q1,q2,q3,q4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today);
        final Spinner dropdown = (Spinner) findViewById(R.id.s1);
        String[] items = new String[]{"Shift 1", "Shift 2", "Shift 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        t1= (TextView) findViewById(R.id.date);
        Calendar c =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        t1.setText(formattedDate);
        q4= (TextView) findViewById(R.id.shift);
        b1= (Button) findViewById(R.id.b2);
        c1= (CardView) findViewById(R.id.card1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1.setVisibility(View.VISIBLE);
                String text=dropdown.getSelectedItem().toString();
                q4.setText(text);

                String firsturl = "https://api.thingspeak.com/channels/464813/feeds.json?api_key=GPNLWRT2NAET0VKD&results=2880";
                Thread thread = new Thread(new HTTPCaller(firsturl));
                try {
                    thread.start();
                    thread.join();
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
                q1 = (TextView) findViewById(R.id.total);
                q2 = (TextView) findViewById(R.id.good);
                q3 = (TextView) findViewById(R.id.bad);
                int total = HTTPCaller.good + HTTPCaller.bad;

                    q1.setText("" + total);
                    q2.setText("" + HTTPCaller.good);
                    q3.setText("" + HTTPCaller.bad);
                    HTTPCaller.good = 1;
                    HTTPCaller.bad = 0;




            }
        });
    }
}
