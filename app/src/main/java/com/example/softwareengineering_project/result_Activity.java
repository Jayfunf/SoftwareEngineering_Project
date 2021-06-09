package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class result_Activity extends AppCompatActivity {
    TextView TV_name, TV_ing, TV_amount, TV_symptom, TV_usage;
    ImageButton cabinet_BTN, result_cabinet_BTN;
    public static String name;
    public static String ing;
    public static String amount;
    public static String symptom;
    public static String usage;

    cabinet_Activity.DataBases.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        myDBHelper = new cabinet_Activity.DataBases.myDBHelper(this);
        sqlDB = myDBHelper.getWritableDatabase();
        myDBHelper.onCreate(sqlDB);

        TV_name = (TextView)findViewById(R.id.TV_name);
        TV_ing = (TextView)findViewById(R.id.TV_ingredient);
        TV_amount = (TextView)findViewById(R.id.TV_amount);
        TV_symptom = (TextView)findViewById(R.id.TV_symptom);
        TV_usage = (TextView)findViewById(R.id.TV_usage);
        cabinet_BTN = (ImageButton)findViewById(R.id.result_cabinet_BTN);

        TV_name.setText(name);
        TV_ing.setText(ing);
        TV_amount.setText(amount);
        TV_symptom.setText(symptom);
        TV_usage.setText(usage);

        cabinet_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
            }
        });
        result_cabinet_BTN = (ImageButton)findViewById(R.id.result_cabinet_BTN);
        result_cabinet_BTN.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                //sqlDB = myDBHelper.getWritableDatabase();
                //myDBHelper.onUpgrade(sqlDB,1,2);
                //sqlDB.close();

                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO storage VALUES (0,'"+name+"','"+ing+"','"+amount+"','"+symptom+"','"+usage+"');");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨", 0).show();
            }
        });
    }
}