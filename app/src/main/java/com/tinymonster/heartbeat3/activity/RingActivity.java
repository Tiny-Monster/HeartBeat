package com.tinymonster.heartbeat3.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tinymonster.heartbeat3.R;

public class RingActivity extends AppCompatActivity {
    private String RingId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
    }
}
