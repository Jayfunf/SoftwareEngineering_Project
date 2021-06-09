package com.example.softwareengineering_project;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity_alarm extends AppCompatActivity {

    public static int settt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fm= getFragmentManager();
        PopTime popTime=new PopTime();
        popTime.show(fm,"Show fragment");
    }

    void SetTime(int Hour ,int Minute ){
        savedata savedata = new savedata(this);
        savedata.Alarmset(Hour,Minute);
        savedata.SaveData(Hour,Minute);
        settt = Minute;
        Home.timealarm(settt);
        Toast.makeText(getApplicationContext(),Hour+"시"+Minute+"분" + " 알람설정 완료!",Toast.LENGTH_LONG).show();
        Intent home_intent = new Intent(this, Home.class);
        startActivity(home_intent);
    }
}