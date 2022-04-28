package com.sysflame.netdroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sysflame.netdroid.R;

public class GetStarted extends AppCompatActivity {

    Button getStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        getStart = findViewById(R.id.get_started);

        getStart.setOnClickListener(view -> startActivity(new Intent(GetStarted.this, HomeActivity.class)));

    }
}