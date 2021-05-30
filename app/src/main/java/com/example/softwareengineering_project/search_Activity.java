package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Array;

public class search_Activity extends AppCompatActivity {
    EditText search_Text;
    ImageButton search_Enter_Button, search_Home_Button;
    Array index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        search_Text = (EditText)findViewById(R.id.editText_search);
        search_Enter_Button = (ImageButton)findViewById(R.id.search_enter_BTN);
        search_Home_Button = (ImageButton)findViewById(R.id.home_BTN);

        Intent home_intent = new Intent(this, Home.class);

        //search_Text.setSelection(search_Text.length()); //커서 위치 설정이라는데 동작안함.
        search_Enter_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        search_Home_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(home_intent);
                finish();
            }
        });
        search_Text.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int KeyCode, KeyEvent event) {
                switch (KeyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        Toast.makeText(getApplicationContext(), search_Text.getText(), Toast.LENGTH_LONG).show(); //Toast for test
                        break;
                }
                return true;
            }
        });
    }
}