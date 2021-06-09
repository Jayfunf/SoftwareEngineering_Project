package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class my_page_Activity extends AppCompatActivity {

    ImageButton test1, Home, Enter;
    EditText Text_id, Text_pw;
    TextView wel, TV_id;
    private MyAPI mMyAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        Intent mypage_Home_intent = new Intent(this, Home.class);
        Intent regi_intent = new Intent(this, regi_Activity.class);
        initMyAPI("https://maxcha98.pythonanywhere.com/");

        wel = (TextView)findViewById(R.id.TV_my_page_wel);
        TV_id = (TextView)findViewById(R.id.TV_my_page);

        wel.setVisibility(View.INVISIBLE);
        TV_id.setVisibility(View.INVISIBLE);

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
                startActivity(regi_intent);
                finish();
            }
        });

        Enter = (ImageButton)findViewById(R.id.idpw_Enter_BTN);
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text_id = (EditText)findViewById(R.id.EditText_id);
                String getid = Text_id.getText().toString();
                Text_pw = (EditText)findViewById(R.id.EditText_pw);
                String getpw = Text_pw.getText().toString();

                //Toast.makeText(getApplicationContext(),getid,Toast.LENGTH_SHORT).show();

                Call<String> postCall = mMyAPI.post_posts("False","False",getid+","+getpw,"False");
                postCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG,"등록 완료");
                            //System.out.println(response.body());
                            if(response.body() == "false"){
                                Toast.makeText(getApplicationContext(),"아이디와 비밀번호를 확인하세요!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "로그인 완료!", Toast.LENGTH_SHORT).show();
                                Text_id.setVisibility(View.INVISIBLE);
                                Text_pw.setVisibility(View.INVISIBLE);
                                TV_id.setText(getid);
                                wel.setVisibility(View.VISIBLE);
                                TV_id.setVisibility(View.VISIBLE);
                            }

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