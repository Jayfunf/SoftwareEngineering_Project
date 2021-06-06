package com.example.softwareengineering_project;
//보관함, 번역, 알람, 근처약, 이미지
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends AppCompatActivity {

    public static final int MULTIPLE_PERMISSIONS = 1801;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

    ImageButton BTN_mypage, BTN_back, BTN_search, BTN_chatbot, BTN_alarm, BTN_cabinet, BTN_translator,
    BTN_setting, BTN_store, BTN_image_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkPermissions();

        Intent home_intent = new Intent(this, Home.class);
        Intent search_intent = new Intent(this, search_Activity.class);
        Intent chat_bot_intent = new Intent(this, Chat_Bot.class);
        Intent cv_intent = new Intent(this, cv_Activity.class);
        Intent setting_intent = new Intent(this, setting_Activity.class);
        Intent Map_intent = new Intent(this, MapsActivity.class);
        Intent mypage_intent = new Intent(this, my_page_Activity.class);

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
                finish();
            }
        });

        BTN_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_mypage_BTN", Toast.LENGTH_SHORT).show();
                startActivity(mypage_intent);
                finish();
            }
        });

        BTN_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_search_BTN", Toast.LENGTH_SHORT).show();
                System.out.println("Test_search_BTN");
                startActivity(search_intent);
                finish();
            }
        });
        BTN_chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_chatbot_BTN", Toast.LENGTH_SHORT).show();
                startActivity(chat_bot_intent);
                finish();
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
                finish();
            }
        });
        BTN_translator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_translator_BTN", Toast.LENGTH_SHORT).show();
                startActivity(home_intent);
                finish();
            }
        });
        BTN_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_store_BTN", Toast.LENGTH_SHORT).show();
                //startActivity(Map_intent);
                //finish(); //terminate ex_Activity
                String url = "http://maps.google.co.kr/maps?q=약국&hl=kor";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        BTN_image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_image_BTN", Toast.LENGTH_SHORT).show();
                startActivity(cv_intent);
                finish(); //terminate ex_Activity
            }
        });
        BTN_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test_setting_BTN", Toast.LENGTH_SHORT).show();
                startActivity(setting_intent);
                finish(); //terminate ex_Activity
            }
        });
    }
    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);

            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                boolean isDeny = false;
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //permission denyed
                            isDeny = true;
                        }
                    }
                }
                if (isDeny) {
                    showNoPermissionToastAndFinish();
                }
            }
        }
    }
    private void showNoPermissionToastAndFinish() {
        Toast toast = Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.",
                Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}
