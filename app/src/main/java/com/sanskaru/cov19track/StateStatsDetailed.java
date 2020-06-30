package com.sanskaru.cov19track;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StateStatsDetailed extends AppCompatActivity
{

    String stateName = "Null";
    TextView stateStatsView;

    public class CallToAPIStates extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            try
            {
                URL url=new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(stream);
                int data=reader.read();
                while(data!=-1)
                {
                    char read=(char) data;
                    result+=read;
                    data=reader.read();
                }
                return result;

            }
            catch (Exception e)
            {
                e.printStackTrace();


            }

            return "F";
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            Log.i("API states Content", s);

            try
            {
                JSONObject apiContent = new JSONObject(s);

                Log.i("stateName", stateName);

                JSONObject stateAPI = apiContent.getJSONObject(stateName);
                JSONObject distObj = stateAPI.getJSONObject("districts");

                JSONArray districts = distObj.toJSONArray(distObj.names());

                Log.i("State API Total", apiContent.toString());
                Log.i("Selected state", stateAPI.toString());
                Log.i("This.Districts", distObj.toString());

                for(int i=0; i < districts.length(); i++) Log.i("Iski maakabhosda Array "+i, districts.get(i).toString());

                Log.i("Districts maakabhosda",distObj.names().toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if (s == "F") Toast.makeText(getApplicationContext(),
                    "An error occured. Please check your internet connection.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_stats_detailed);

        Intent intent = getIntent();
        String s = intent.getStringExtra("state");

        Log.i("State name passed",s);

        if(s.equals("Andaman and Nicobar Islands")) stateName = "AN";
        else if(s.equals("Andhra Pradesh")) stateName = "AP";
        else if(s.equals("Arunachal Pradesh")) stateName = "AR";
        else if(s.equals("Assam")) stateName = "AS";
        else if(s.equals("Bihar")) stateName = "BR";
        else if(s.equals("Chandigarh")) stateName = "CH";
        else if(s.equals("Chattisgarh")) stateName = "CT";
        else if(s.equals("Dadra and Nagar Haveli and Daman and Diu")) stateName = "DN";
        else if(s.equals("Delhi")) stateName = "DL";
        else if(s.equals("Goa")) stateName = "GA";
        else if(s.equals("Gujarat")) stateName = "GJ";
        else if(s.equals("Haryana")) stateName = "HR";
        else if(s.equals("Himachal Pradesh")) stateName = "HP";
        else if(s.equals("Jammu and Kashmir")) stateName = "JK";
        else if(s.equals("Jharkhand")) stateName = "JH";
        else if(s.equals("Karnataka")) stateName = "KA";
        else if(s.equals("Kerala")) stateName = "KL";
        else if(s.equals("Ladakh")) stateName = "LA";
        else if(s.equals("Lakshadweep")) stateName = "LD";
        else if(s.equals("Maharashtra")) stateName = "MH";
        else if(s.equals("Meghalaya")) stateName = "ML";
        else if(s.equals("Manipur")) stateName = "MN";
        else if(s.equals("Madhya Pradesh")) stateName = "MP";
        else if(s.equals("Mizoram")) stateName = "MZ";
        else if(s.equals("Nagaland")) stateName = "NL";
        else if(s.equals("Odisha")) stateName = "OR";
        else if(s.equals("Punjab")) stateName = "PB";
        else if(s.equals("Puducherry")) stateName = "PY";
        else if(s.equals("Rajasthan")) stateName = "RJ";
        else if(s.equals("Sikkim")) stateName = "SK";
        else if(s.equals("Telangana")) stateName = "TG";
        else if(s.equals("Tamil Nadu")) stateName = "TN";
        else if(s.equals("Tripura")) stateName = "TR"; // TT is total, UN is unassigned
        else if(s.equals("Uttar Pradesh")) stateName = "UP";
        else if(s.equals("Uttarakhand")) stateName = "UT";
        else if(s.equals("West Bengal")) stateName = "WB";

        Log.i("String name", stateName);

        TextView statenameView = (TextView) findViewById(R.id.statenameView);
        statenameView.setText(s);




        stateStatsView = (TextView) findViewById(R.id.stateStatsView);

        CallToAPIStates callToAPIStates = new CallToAPIStates();
        callToAPIStates.execute("https://api.covid19india.org/v3/min/data.min.json");








    }
}