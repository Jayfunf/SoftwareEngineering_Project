package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class regi_Activity extends AppCompatActivity {

    EditText Text_id, Text_pw,Text_email;
    ImageButton Enter, regi_home;
    private MyAPI mMyAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);
        initMyAPI("https://maxcha98.pythonanywhere.com/");


        Intent home_intent = new Intent(this, Home.class);
        regi_home=(ImageButton)findViewById(R.id.regi_home_BTN);
        regi_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home_intent);
                finish();
            }
        });

        Enter = (ImageButton)findViewById(R.id.resi_Enter_BTN);
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text_id = (EditText)findViewById(R.id.EditText_id);
                String getid = Text_id.getText().toString();
                Text_pw = (EditText)findViewById(R.id.EditText_pw);
                String getpw = Text_pw.getText().toString();
                Text_email = (EditText)findViewById(R.id.EditText_email);
                String getem = Text_email.getText().toString();

                Call<String> postCall = mMyAPI.post_posts("False","False","False",getid+","+getpw+","+getem);
                postCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG,"등록 완료");
                            System.out.println(response.body());
                            Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d(TAG,"Status Code : " + response.code());
                            Log.d(TAG,response.errorBody().toString());
                            Log.d(TAG,call.request().body().toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG,"Fail msg : " + t.getMessage());
                    }
                });
            }
        });
    }
    private void initMyAPI(String baseUrl){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Log.d(TAG,"initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }
}