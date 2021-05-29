package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class search_Activity extends AppCompatActivity {
    EditText search_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        search_Text = (EditText)findViewById(R.id.editText_search);
        //search_Text.setSelection(search_Text.length()); //커서 위치 설정이라는데 동작안함.


    }
}