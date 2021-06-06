package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class my_page_Activity extends AppCompatActivity {

    ImageButton test1, test2, test3, Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Intent mypage_Home_intent = new Intent(this, Home.class);

        Home = (ImageButton)findViewById(R.id.mypage_Home_BTN);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mypage_Home_intent);
                finish();
            }
        });
        test1 = (ImageButton)findViewById(R.id.test1);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Test1",Toast.LENGTH_SHORT).show();
            }
        });

        test2 = (ImageButton)findViewById(R.id.test2);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Test2",Toast.LENGTH_SHORT).show();
            }
        });

        test3 = (ImageButton)findViewById(R.id.test3);
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Test3",Toast.LENGTH_SHORT).show();
            }
        });
    }
}