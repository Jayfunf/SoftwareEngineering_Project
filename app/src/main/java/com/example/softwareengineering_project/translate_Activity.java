package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class translate_Activity extends AppCompatActivity {
    ImageButton trans_home_BTN;
    EditText trans_editText;
    TextView trans_Text;
    Button trans_BTN;
    private MyAPI mMyAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        initMyAPI("https://maxcha98.pythonanywhere.com/");

        trans_home_BTN = (ImageButton)findViewById(R.id.setting_Home_BTN);
        trans_BTN = (Button)findViewById(R.id.trans_BTN);
        trans_Text = (TextView)findViewById(R.id.trans_Text);

        Intent home_intent = new Intent(this, Home.class);

        trans_home_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home_intent);
                finish();
            }
        });
        trans_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trans_editText = (EditText)findViewById(R.id.ET_trans);
                String str = trans_editText.getText().toString();

                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();

                Call<String> postCall = mMyAPI.post_posts("False", str,"False","False");
                postCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG,"등록 완료");
                            System.out.println(response.body());
                            Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();

                            trans_Text.setText(response.body());
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