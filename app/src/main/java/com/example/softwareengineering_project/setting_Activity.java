package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class setting_Activity extends AppCompatActivity {

    ImageButton BTN_sound, BTN_nor,BTN_Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent home_intent = new Intent(this, Home.class);

        BTN_sound = (ImageButton)findViewById(R.id.sound_setting_BTN);
        BTN_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"soundSetting",Toast.LENGTH_SHORT).show();
                setVolumeControlStream(AudioManager.STREAM_MUSIC);
            }
        });
        BTN_nor = (ImageButton)findViewById(R.id.normal_BTN);
        BTN_nor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"nornor",Toast.LENGTH_SHORT).show();
            }
        });
        BTN_Home = (ImageButton)findViewById(R.id.setting_Home_BTN);
        BTN_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"집으로!",Toast.LENGTH_SHORT).show();
                startActivity(home_intent);
                finish();
            }
        });

    }
}