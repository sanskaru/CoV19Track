package com.s090.cov19track;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.security.AccessController.getContext;
import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

   static ListView listView;
//    static TextView textView;
    TextView confirmed_cases, active_cases, death_s, recovered_cases;
    Fragment fragment;
    public static ArrayAdapter<String> stringArrayAdapter;
    public void AddToList(JSONArray array)
    {

        listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> list = new ArrayList<String>();

        for(int i=0;i<array.length();i++)
        {

            try
            {
                JSONObject state= array.getJSONObject(i);

                String totalConfirmed = state.getString("totalConfirmed"), loc = state.getString("loc"), discharged = state.getString("discharged"), deaths = state.getString("deaths");

                list.add("State: "+loc+"\n"+"Total: "+totalConfirmed+"\n"+"Recovered: "+discharged+"\n"+"Deaths: "+deaths);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        stringArrayAdapter = adapter;
       listView.setAdapter(adapter);

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
            try
            {

                JSONObject apiContent = new JSONObject(s);

                JSONObject data = apiContent.getJSONObject("data");

        //        Log.i("Data Object", data.toString());

                String unofficial = data.getString("unofficial-summary");
                JSONArray unoffArray = data.getJSONArray("unofficial-summary");

         //       Log.i("Unofficial Summary", unoffArray.getString(0));

                JSONObject unoffObject = unoffArray.getJSONObject(0);

                confirmed_cases = (TextView) findViewById(R.id.total_cases);
                active_cases = (TextView) findViewById(R.id.active_cases);
                death_s = (TextView) findViewById(R.id.death_s);
                recovered_cases=(TextView) findViewById(R.id.recovered_cases);

                String total = unoffObject.getString("total"), recovered = unoffObject.getString("recovered"), active = unoffObject.getString("active"), deaths = unoffObject.getString("deaths");

                confirmed_cases.setText(total);
             //   confirmed_cases.setTextColor(Color.RED);

                recovered_cases.setText(recovered);
            //    recovered_cases.setTextColor(Color.GREEN);

                active_cases.setText(active);
            //    active_cases.setTextColor(Color.YELLOW);

                death_s.setText(deaths);
            //    death_s.setTextColor(Color.DKGRAY);

                JSONArray regional = data.getJSONArray("regional"); // sorry for the extremely cluttered code, but that's how the JSON file is.

        //        Log.i("Regional", regional.toString());

                AddToList(regional);

                // regional array contains statewise data in alphabetical order


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

        BottomNavigationView navbar = (BottomNavigationView) findViewById(R.id.navbar);
        navbar.setOnNavigationItemSelectedListener(listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();
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