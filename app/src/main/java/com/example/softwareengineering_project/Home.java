package com.example.softwareengineering_project;
//보관함, 번역, 알람, 근처약, 이미지
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Calendar;

public class Home extends AppCompatActivity {
    ImageButton BTN_mypage, BTN_back, BTN_search, BTN_chatbot, BTN_alarm, BTN_cabinet, BTN_translator,
    BTN_setting, BTN_store, BTN_image_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent home_intent = new Intent(this, Home.class);
        Intent search_intent = new Intent(this, search_Activity.class);
        Intent chat_bot_intent = new Intent(this, Chat_Bot.class);

        BTN_back = (ImageButton) findViewById(R.id.back_BTN);
        BTN_mypage = (ImageButton) findViewById(R.id.mypage_BTN);
        BTN_search = (ImageButton) findViewById(R.id.search_BTN);
        BTN_chatbot = (ImageButton) findViewById(R.id.chatbot_BTN);
        BTN_alarm = (ImageButton) findViewById(R.id.alarm_BTN);
        BTN_image_search = (ImageButton) findViewById(R.id.image_search_BTN);
        BTN_cabinet = (ImageButton) findViewById(R.id.cabinet_BTN);
        BTN_translator = (ImageButton) findViewById(R.id.translate_BTN);
        BTN_setting = (ImageButton) findViewById(R.id.setting_BTN);
        BTN_store = (ImageButton) findViewById(R.id.store_BTN);

        BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_back_BTN", Toast.LENGTH_SHORT).show();
                startActivity(home_intent);
                finish(); //terminate ex_Activity
            }
        });

        BTN_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_mypage_BTN", Toast.LENGTH_SHORT).show();
            }
        });

        BTN_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_search_BTN", Toast.LENGTH_SHORT).show();
                startActivity(search_intent);
                finish(); //terminate ex_Activity
            }
        });
        BTN_chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_chatbot_BTN", Toast.LENGTH_SHORT).show();
                startActivity(chat_bot_intent);
                finish(); //terminate ex_Activity
            }
        });
        BTN_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "AlarmTest", Toast.LENGTH_SHORT).show();
            }
        });
        BTN_cabinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_cabinet_BTN", Toast.LENGTH_SHORT).show();
                startActivity(home_intent);
                finish(); //terminate ex_Activity
            }
        });
        BTN_translator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_translator_BTN", Toast.LENGTH_SHORT).show();
                startActivity(home_intent);
                finish(); //terminate ex_Activity
            }
        });
        BTN_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_store_BTN", Toast.LENGTH_SHORT).show();
                startActivity(home_intent);
                finish(); //terminate ex_Activity
            }
        });
        BTN_image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_image_BTN", Toast.LENGTH_SHORT).show();
                startActivity(home_intent);
                finish(); //terminate ex_Activity
            }
        });
        BTN_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_setting_BTN", Toast.LENGTH_SHORT).show();
                startActivity(home_intent);
                finish(); //terminate ex_Activity
            }
        });
    }
}
