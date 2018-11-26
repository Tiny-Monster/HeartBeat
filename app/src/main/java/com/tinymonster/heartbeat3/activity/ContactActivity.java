package com.tinymonster.heartbeat3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tinymonster.heartbeat3.R;

public class ContactActivity extends AppCompatActivity {
    private ImageView contact_img_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contact_img_return=(ImageView)findViewById(R.id.contact_img_return);
        contact_img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
