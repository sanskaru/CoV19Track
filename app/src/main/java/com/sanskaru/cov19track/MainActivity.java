package com.sanskaru.cov19track;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    TextView confirmed_cases, active_cases, death_s, recovered_cases;
    Fragment fragment;

    public static String[] totalConfirmed = new String[35], loc = new String[35], discharged = new String[35], deaths = new String[35];
    public void AddToList(JSONArray array)
    {
        for(int i=0;i<array.length();i++)
        {
            try
            {
                JSONObject state= array.getJSONObject(i);

                totalConfirmed[i] = state.getString("totalConfirmed");
                loc[i] = state.getString("loc");
                discharged[i] = state.getString("discharged");
                deaths[i] = state.getString("deaths");

            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class CallToAPI extends AsyncTask<String, Void, String>
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("API Content", s);
            if(s=="F") Toast.makeText(getApplicationContext(), "An error occured. Please check your internet connection.", Toast.LENGTH_LONG).show();
            try
            {

                JSONObject apiContent = new JSONObject(s);

                JSONObject data = apiContent.getJSONObject("data");

                JSONArray unoffArray = data.getJSONArray("unofficial-summary");

                JSONObject unoffObject = unoffArray.getJSONObject(0);

                confirmed_cases = (TextView) findViewById(R.id.total_cases);
                active_cases = (TextView) findViewById(R.id.active_cases);
                death_s = (TextView) findViewById(R.id.death_s);
                recovered_cases=(TextView) findViewById(R.id.recovered_cases);

                String total = unoffObject.getString("total"), recovered = unoffObject.getString("recovered"), active = unoffObject.getString("active"), deaths = unoffObject.getString("deaths");

                confirmed_cases.setText(total);

                recovered_cases.setText(recovered);

                active_cases.setText(active);

                death_s.setText(deaths);

                JSONArray regional = data.getJSONArray("regional"); // sorry for the extremely cluttered code, but that's how the JSON file is.

                AddToList(regional); // regional array contains statewise data in alphabetical order

            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        CallToAPI call=new CallToAPI();
        call.execute("https://api.rootnet.in/covid19-in/stats/latest");

        Calendar calendar = Calendar.getInstance();

        BottomNavigationView navbar = (BottomNavigationView) findViewById(R.id.navbar);
        navbar.setOnNavigationItemSelectedListener(listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();

    }

    public void openStateStats(View view)
    {
        String stateName = ((TextView) view.findViewById(R.id.card_title)).getText().toString();
        Intent intent = new Intent(getApplicationContext(), StateStatsDetailed.class);
        intent.putExtra("state", stateName);
        startActivity(intent);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            fragment = null;
            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                {
                    fragment = new HomeFragment();
                    break;
                }
                case R.id.nav_states:
                {
                    fragment = new StatesFragment();
                    break;
                }
                case R.id.nav_about:
                {
                    fragment = new AboutFragment();
                    break;
                }
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();

            CallToAPI call=new CallToAPI();
            call.execute("https://api.rootnet.in/covid19-in/stats/latest");
            return true;
        }
    };
}