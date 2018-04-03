package com.example.dell.pms;

/**
 * Created by DELL on 4/2/2018.
 */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class HTTPCaller implements Runnable {
    private String GETurl;
    public static int bad = 0;
    public static int good = 1;
    HTTPCaller (String Url)
    {
        GETurl = Url;
    }
    public void run()
    {
        try
        {
            URL url = new URL(GETurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            StringBuilder response = new StringBuilder();
            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                Log.e("First","Success");
                String line;
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while((line = in.readLine())!=null)
                {
                    response.append(line);
                }
            } else
            {
                Log.e("First","Failure");
            }
            Log.e("First",response.toString());
            JSONObject json = new JSONObject(response.toString());
            JSONArray feedSep = json.getJSONArray("feeds");
            for(int i=0;i<feedSep.length();i++)
            {
                JSONObject jsonObs = feedSep.getJSONObject(i);
                String res = jsonObs.getString("field1");
                Log.e("First "+i,res);
                String res1 = jsonObs.getString("created_at");
                Log.e("Created = "+i,res1);
                if(res.equals("0"))
                {
                    HTTPCaller.bad = HTTPCaller.bad + 1;
                } else if(res.equals("1")) {
                    HTTPCaller.good = HTTPCaller.good + 1;
                }
            }
        } catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
}