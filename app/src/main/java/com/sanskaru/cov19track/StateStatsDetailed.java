package com.sanskaru.cov19track;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class StateStatsDetailed extends AppCompatActivity
{

    String stateName = "Null";
    TextView stateStatsView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        String str = "https://api.covid19india.org/v3/min/data.min.json";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (
                        Request.Method.GET, str, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                stateStatsView.setText("Response: " + response.toString());
                                try
                                {
                                    JSONObject apiContent = response;

                                    Log.i("stateName", stateName);
                                    Log.i("API State", apiContent.toString());

                                    JSONObject stateAPI = apiContent.getJSONObject(stateName);
                                    JSONObject distObj = stateAPI.getJSONObject("districts");

                                    JSONArray districts = distObj.toJSONArray(distObj.names());

                                    Log.i("State API Total", apiContent.toString());
                                    Log.i("Selected state", stateAPI.toString());
                                    Log.i("This.Districts", distObj.toString());

                                    String[] distNames = new String[distObj.names().length() + 1];
                                    for (int i = 0; i < distObj.names().length(); i++) distNames[i] = (String) distObj.names().get(i).toString();

                                    for (int i = 0; i < districts.length(); i++) Log.i("Districts " + i, districts.get(i).toString());

                                    Log.i("District names", distObj.names().toString());

                                    stateStatsView.setVisibility(View.INVISIBLE);

                                    LinearLayout stats_viewgroup = (LinearLayout) findViewById(R.id.stats_viewgroup);
                                    LayoutInflater inflater = LayoutInflater.from(stats_viewgroup.getContext());

                                    for (int i = 0; i < districts.length(); i++)
                                    {
                                        LinearLayout viewGroup = new LinearLayout(stats_viewgroup.getContext());
                                        LinearLayout.LayoutParams params = new LinearLayout
                                                .LayoutParams(
                                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                                 200);
                                        params.topMargin = 10;
                                        params.bottomMargin = 10;
                                        params.leftMargin = 10;
                                        params.rightMargin = 10;
                                        viewGroup.setLayoutParams(params);

                                        View view = inflater.inflate(R.layout.state_view, viewGroup, false);

                                        JSONObject object = districts.getJSONObject(i);
                                        JSONObject objectification = object.getJSONObject("total");
                                        Log.i("Objectification of object " + i, objectification.toString());

                                        String deaths = "0", recovered = "0", confirmed = "0", name = "0";

                                        if (objectification.has("deaths"))
                                            deaths = objectification.getString("deaths");
                                        if (objectification.has("confirmed"))
                                            confirmed = objectification.getString("confirmed");
                                        if (objectification.has("recovered"))
                                            recovered = objectification.getString("recovered");

                                        Log.i("MKBHD", deaths + " " + recovered + " " + confirmed + " " + name);

                                        ((TextView) view.findViewById(R.id.card_deaths)).setText("Deaths: " + deaths);
                                        ((TextView) view.findViewById(R.id.card_recovered)).setText("Recovered: " + recovered);
                                        ((TextView) view.findViewById(R.id.card_total)).setText("Confirmed: " + confirmed);
                                        ((TextView) view.findViewById(R.id.card_title)).setText(distObj.names().get(i).toString());

                                        stats_viewgroup.addView(view);
                                        stats_viewgroup.setAlpha(1f);

                                    }
                                }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stateStatsView.setText("That didn't work!");

                    }
                });

        queue.add(jsonObjectRequest);

    }
}