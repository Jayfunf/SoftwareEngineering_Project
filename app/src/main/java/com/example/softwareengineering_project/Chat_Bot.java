package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Chat_Bot extends AppCompatActivity {
    ImageButton chatbot_Home_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        getSupportActionBar().hide();

        Intent home_intent = new Intent(this, Home.class);
        chatbot_Home_Button = (ImageButton)findViewById(R.id.chat_Home_BTN);

        chatbot_Home_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(home_intent);
                finish();
            }
        });

    }
}