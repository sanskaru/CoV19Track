package com.s090.cov19track;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static com.s090.cov19track.MainActivity.stringArrayAdapter;
import static java.util.Arrays.asList;


public class StatesFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.states_frag, container, false);
        ArrayList<String> strings = new ArrayList<String>(asList("1","2"));
        ArrayAdapter<String> adapter = stringArrayAdapter;
        ((ListView) myView.findViewById(R.id.listView)).setAdapter(adapter);
        return myView;
    }
}
