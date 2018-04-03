package com.example.dell.pms;


import android.app.Activity;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Home_activity extends AppCompatActivity implements View.OnClickListener {
    TextView totalTxt,goodTxt,badTxt;
    int q=1;
    int e;
    int z = 0;
    Handler handler= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        handler.postDelayed(runnable,1000);
        Spinner dropdown = (Spinner) findViewById(R.id.s1);
        String[] items = new String[]{"A10", "A11", "A12", "A13", "A14"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.b1);
        button.setOnClickListener(this);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            String firsturl = "https://api.thingspeak.com/channels/464813/feeds.json?api_key=GPNLWRT2NAET0VKD&results=2880";
            Thread thread = new Thread(new HTTPCaller(firsturl));
            try {
                thread.start();
                thread.join();
            } catch (Exception exp) {
                exp.printStackTrace();
            }
            totalTxt = (TextView) findViewById(R.id.textview11);
            goodTxt = (TextView) findViewById(R.id.textview12);
            badTxt = (TextView) findViewById(R.id.textview13);
            int total = HTTPCaller.good + HTTPCaller.bad;
            if (z <= total) {

z=total;
                totalTxt.setText("" + total);
                goodTxt.setText("" + HTTPCaller.good);
                badTxt.setText("" + HTTPCaller.bad);
                HTTPCaller.good = 1;
                HTTPCaller.bad = 0;
                Log.e("FIrst", "Complete");
                handler.postDelayed(this, 20000);
            }else {
                HTTPCaller.good = 1;
                HTTPCaller.bad = 0;
                handler.postDelayed(this, 20000);
            }
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), View_analysis.class);
        startActivity(intent);
    }

}