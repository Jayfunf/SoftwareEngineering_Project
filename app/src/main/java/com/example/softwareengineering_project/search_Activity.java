package com.example.softwareengineering_project;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class search_Activity extends AppCompatActivity {
    EditText search_Text;
    ImageButton search_Enter_Button, search_Home_Button;
    Array index;
    private MyAPI mMyAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        initMyAPI("https://maxcha98.pythonanywhere.com/");
        Button button = (Button) findViewById(R.id.button2);

        //search_Text = (EditText)findViewById(R.id.editText);
        //search_Enter_Button = (ImageButton)findViewById(R.id.search_enter_BTN);
        search_Home_Button = (ImageButton)findViewById(R.id.home_BTN);

        Intent home_intent = new Intent(this, Home.class);
        Intent result_intent = new Intent(this, result_Activity.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] category = {"알레르기약", "피부약", "진통제", "소염제", "해열제", "소화제", "두통약", "위염약", "멀미약", "감기약"};
                EditText editText1 = (EditText) findViewById(R.id.editText);
                String strText = editText1.getText().toString();
                /*float[][] array=  mophs(strText);
                Interpreter tflite = getTfliteInterpreter("gru_model.tflite");
                float[][] output = new float[1][10];
                tflite.run(array, output);
                float[] max = new float[2];
                for(int i = 0; i<10;i++){
                    if (output[0][i] > max[0]){
                        max[0] = output[0][i];
                        max[1] = (float) i;
                    }
                }
                System.out.println(category[(int) max[1]]);
                Toast.makeText(getApplicationContext(),category[(int) max[1]],Toast.LENGTH_SHORT).show();*/

                Log.d(TAG,"POST");
                Call<String> postCall = mMyAPI.post_posts(strText,"False","False","False");
                postCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG,"등록 완료");
                            System.out.println(response.body());
                            Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();
                            String[] class_mount = response.body().split("!");

                            result_Activity.name = class_mount[0];
                            result_Activity.ing = class_mount[1];
                            result_Activity.amount = class_mount[2];
                            result_Activity.symptom = class_mount[3];
                            result_Activity.usage = class_mount[4];

                            startActivity(result_intent);
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
        //search_Text.setSelection(search_Text.length()); //커서 위치 설정이라는데 동작안함.
        /*search_Enter_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });*/
        search_Home_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(home_intent);
                finish();
            }
        });
        /*editText1.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int KeyCode, KeyEvent event) {
                switch (KeyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        Toast.makeText(getApplicationContext(), search_Text.getText(), Toast.LENGTH_LONG).show(); //Toast for test
                        break;
                }
                return true;
            }
        });*/
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
    public float[][] mophs(String strText){
        String [] word_dict = {",", "이", "다", "하", "가", "ㄴ다", "의", "두통", "ㅁ", "는", "에", "ㄴ", "소화", ".", "증상", "염증", "통증",
                "피부", "코", "을", "머리", "구토", "및", "등", "고", "과", "·", "감", "알레르기", "통", "비염", "염", "멀미", "의하", "속", "어요",
                "불량", "팽만", "구역", "낳", "콧물", "재채기", "인후", "막히", "감기", "기침", "아프", "있", "은", "지", "발생", "가려움", "발진", "열",
                "오한", "근육통", "아", "어지럽", "속이", "안", "싶", "ㄹ", "같", "ㅂ니다", "되", "거나", "에서", "었", "유발", "완화", "인하", "복부",
                "성", "가렵", "두드러기", "위염", "약", "막", "힘", "발열", "관절통", "급성", "나오", "편두통", "편도", "선염", "운동", "후", "쑤시", "좋",
                "부", "를", "느끼", "오심", "창백", "변화", "위장관", "이나", "졸립", "복통", "고열", "곤란", "면", "나요", "다음", "질환", ":", "체",
                "개선", "약물", "로", "지연", "트림", "결막염", "부위", "쓰리", "또는", "습진", "여드름", "기미", "주근깨", "위산", "위부", "춥", "떨리",
                "목구멍", "목감기", "가래", "독감", "자주", "긴장성", "혈관성", "신경통", "유선염", "요통", "관절", "종", "이하", "근골", "피로", "사지",
                "산통", "신경성", "지근", "지근지근", "쑤", "시", "매스껍", "더", "륵", "하다", "토하", "때", "단순히", "어지럼증", "뿐", "아니", "라",
                "식은땀", "입", "마름", "심박수", "혈압", "증가", "와", "감소", "자율", "신경", "교란", "함께", "경험", "경우", "도", "많", "일반적", "메스껍",
                "막연", "미식", "거림", "덧붙이", "어서", "무기력", "침", "흘리", "것", "발한", "한숨", "하품", "맥박", "수의", "동반", "목", "따", "움", "미열",
                "연하", "호흡", "상기도", "감염", "뒷머리", "묵직", "콕콕", "전체", "멍하", "게", "흔히", "보이", "안구", "긴장형", "나타나", "않", "생기",
                "는데요", "가라앉", "없애", "살균", "치은염", "구내염", "발", "전후", "염증성", "부종", "수술", "외상", "유즙", "울", "가벼운", "절상", "찰과상",
                "화상", "으로", "감염증", "예방", "인간", "사용", "효소", "보충", "어", "주", "약품", "들", "시키", "잘", "에요", "안되", "거", "아요",
                "간기능", "저하", "보조", "치료", "(", ")", "식욕", "감퇴", "쓰", "리다", "신물", "올라오", "아리", "부비동", "급만", "혈관", "운동성", "반응",
                "알", "러", "봄철", "각막염", "눈물", "눈", "발적", "부어오르", "건조", "걸리", "명치", "갑작스럽", "증과", "상복", "동통", "식후", "심와",
                "부의", "그득", "함", "조기", "수", "습니다", "질병", "그", "밖", "상해", "아픔", "사람", "동물", "못하", "도록", "심하", "뭐", "나",
                "물집", "잡히", "햇볕", "탄", "색소", "침착증", "전신", "권태", "피부염", "과다", "불쾌감", "위통", "신트림", "몸살기", "운", "몸", "열이",
                "많이", "고온", "높", "감기약", "두통약", "멀미약", "소염제", "소화제", "진통제", "피부약", "해열제", "소", "진통"};
        ArrayList<Integer> result_input = new ArrayList<Integer>();
        ArrayList<String> vocList = new ArrayList<String>();
        ArrayList<String> resultVoc = new ArrayList<String>();
//                Log.i(strText,"확인");
        try {
            MorphemeAnalyzer ma = new MorphemeAnalyzer();
            ma.createLogger(null);
            List<MExpression> ret = ma.analyze(strText);

            ret = ma.postProcess(ret);
            ret = ma.leaveJustBest(ret);
            List<Sentence> stl = ma.divideToSentences(ret);
            for( int i = 0; i < stl.size(); i++ ) {
                Sentence st = stl.get(i);
                for( int j = 0; j < st.size(); j++ ) {
                    vocList.add(String.valueOf(st.get(j)));
                }
            }
            ma.closeLogger();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i=0;i< vocList.size();i++){
            String str1 = vocList.get(i);
            String[] spStr = str1.split("\\+");

            for(int j=0;j<spStr.length;j++){
                String[] spStr2 = spStr[j].split("/");
//                for (String el : spStr2) {
//                    System.out.println("진짜"+el);
//                }
                for(int k=0; k<spStr2.length;k++){
                    if(spStr2[k].equals("NNG") || spStr2[k].equals("NNG]")
                            || spStr2[k].equals("NNP") || spStr2[k].equals("NNP]")
                            || spStr2[k].equals("NNB") || spStr2[k].equals("NNB]")
                            || spStr2[k].equals("NR") || spStr2[k].equals("NR]")
                            || spStr2[k].equals("NNG") || spStr2[k].equals("NNG]")
                            || spStr2[k].equals("VV") || spStr2[k].equals("VV]")
                            || spStr2[k].equals("VA") || spStr2[k].equals("VA]")
                            || spStr2[k].equals("VX") || spStr2[k].equals("VX]")
                            || spStr2[k].equals("VCP") || spStr2[k].equals("VCP]")
                            || spStr2[k].equals("VCN") || spStr2[k].equals("VCN]")){
                        resultVoc.add(spStr2[k-1]);
                    }
                }
            }
        }
        for (String el : vocList) {
//                    System.out.println("진짜"+el);
            for(int i = 1;i<word_dict.length;i++){
                if (el.equals(word_dict[i])){
                    result_input.add(i);
                }
            }
        }
        float[][] array = new float[1][50];

        int size=50-result_input.size();

        for(Integer temp : result_input){

            array[0][size++] = temp;

        }
        System.out.println(array);
        return array;
    }
    private Interpreter getTfliteInterpreter(String modelPath) {
        try {
            return new Interpreter(loadModelFile(search_Activity.this, modelPath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}