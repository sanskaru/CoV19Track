package com.sanskaru.cov19track;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StateStatsDetailed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_stats_detailed);

        Intent intent = getIntent();
        String s = intent.getStringExtra("state");

        TextView statenameView = (TextView) findViewById(R.id.statenameView);
        statenameView.setText(s);
    }
}