package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class alarm_design extends AppCompatActivity {
    Button alarm_design;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_design);
        getSupportActionBar().hide();

        alarm_design = (Button)findViewById(R.id.alarm_design);
        alarm_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"alarm",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}