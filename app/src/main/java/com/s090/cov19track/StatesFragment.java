package com.s090.cov19track;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static com.s090.cov19track.MainActivity.deaths;
import static com.s090.cov19track.MainActivity.discharged;
import static com.s090.cov19track.MainActivity.loc;
import static com.s090.cov19track.MainActivity.stringArrayAdapter;
import static com.s090.cov19track.MainActivity.totalConfirmed;
import static java.util.Arrays.asList;


public class StatesFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.states_frag, container, false);
  //      ArrayList<String> strings = new ArrayList<String>(asList("1","2"));
 //       ArrayAdapter<String> adapter = stringArrayAdapter;
//        ((ListView) myView.findViewById(R.id.listView)).setAdapter(adapter);

        LinearLayout statesView = ((LinearLayout) myView.findViewById(R.id.statesView));
        LayoutInflater layoutInflater = LayoutInflater.from(statesView.getContext());
        for (int i=0; i<loc.length; i++)
        {
            View view = layoutInflater.inflate(R.layout.state_view, statesView);

            ((TextView) view.findViewById(R.id.card_deaths)).setText("Deaths: "+deaths[i]);
            ((TextView) view.findViewById(R.id.card_recovered)).setText("Recovered: "+discharged[i]);
            ((TextView) view.findViewById(R.id.card_total)).setText("Total: "+totalConfirmed[i]);
            ((TextView) view.findViewById(R.id.card_title)).setText(loc[i]);

            Log.i("Strings: ", loc[i]+" "+deaths[i]+" "+discharged[i]+" "+totalConfirmed[i]);

        }

        return myView;
    }
}
