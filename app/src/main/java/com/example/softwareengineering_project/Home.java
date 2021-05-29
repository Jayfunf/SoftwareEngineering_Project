package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Array;

public class Home extends AppCompatActivity {
    ImageButton BTN_mypage,BTN_back, BTN_search, BTN_chatbot;
    EditText EditText_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent home_intent = new Intent(this, Home.class);
        Intent search_intent = new Intent(this, search_Activity.class);
        Intent chat_bot_intent = new Intent(this, Chat_Bot.class);

        //EditText_main = (EditText)findViewById(R.id.EditText_main);
        BTN_back = (ImageButton) findViewById(R.id.back_BTN);
        BTN_mypage = (ImageButton) findViewById(R.id.mypage_BTN);
        BTN_search = (ImageButton) findViewById(R.id.search_BTN);
        BTN_chatbot = (ImageButton) findViewById(R.id.chatbot_BTN);


        BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Test_back_BTN",Toast.LENGTH_SHORT).show();
                finish(); //terminate ex_Activity
                startActivity(home_intent);
            }
        });

        BTN_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Test_mypage_BTN",Toast.LENGTH_SHORT).show();
            }
        });

        BTN_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Test_search_BTN",Toast.LENGTH_SHORT).show();
                startActivity(search_intent);
            }
        });
        BTN_chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Test_chatbot_BTN",Toast.LENGTH_SHORT).show();
                startActivity(chat_bot_intent);
            }
        });
    }
}