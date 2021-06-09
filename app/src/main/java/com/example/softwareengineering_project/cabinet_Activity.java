package com.example.softwareengineering_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class cabinet_Activity extends AppCompatActivity {

    ImageButton home_BTN;
    Button button1;
    String[] result = new String[1000];
    DataBases.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet);

        ListView listView = (ListView) findViewById(R.id.listview1) ;
        myDBHelper = new DataBases.myDBHelper(this);
        sqlDB = myDBHelper.getWritableDatabase();
        myDBHelper.onCreate(sqlDB);

        Intent home_intent = new Intent(this, Home.class);
        home_BTN =(ImageButton)findViewById(R.id.cabinet_Home_BTN);
        home_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home_intent);
                finish();
            }
        });
        button1 =(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant", "Recycle"})
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                Cursor c = sqlDB.rawQuery("SELECT * FROM STORAGE",null);
//                Toast.makeText(getApplicationContext(),cursor.getString(0),0).show();
                System.out.println(c);
                int count = c.getCount();   // db에 저장된 행 개수를 읽어온다

                // 저장된 행 개수만큼의 배열을 생성

                for (int i = 0; i < count; i++) {

                    c.moveToNext();   // 첫번째에서 다음 레코드가 없을때까지 읽음

                    String name = c.getString(1);

                    String ingredient = c.getString(2);

                    String amount = c.getString(3);
                    String symptoms = c.getString(4);
                    String how_to_take = c.getString(5);

                    result[i] =name + "\n" + ingredient + "\n" + amount + "\n" + symptoms + "\n" + how_to_take;   // 각각의 속성값들을 해당 배열의 i번째에 저장
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1,result);   // ArrayAdapter(this, 출력모양, 배열)

                listView.setAdapter(adapter);   // listView 객체에 Adapter를 붙인다
                listView.setOnItemClickListener(listener);

                sqlDB.close();
            }
            AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {

                @SuppressLint("WrongConstant")
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // TODO Auto-generated method stub
                    sqlDB = myDBHelper.getWritableDatabase();
                    sqlDB.execSQL("DELETE FROM storage WHERE Name = '"+result[position]+"';");
                    sqlDB.close();
                    Toast.makeText(getApplicationContext(), "삭제됨", 0).show();

                    System.out.println(position);
                }

            };

        });
    }
    static final class DataBases {

        public final class CreateDB implements BaseColumns {
            public static final String _id = "_id";
            public static final String Name = "Name";
            public static final String Ingredient = "Ingredient";
            public static final String Amount = "Amount";
            public static final String Symptoms = "symptoms";
            public static final String How_to_Take = "how_to_take";
            public static final String _TABLENAME0 = "storage";
            public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                    + _id + " integer not null , "
                    + Name + " text not null , "
                    + Ingredient + " text not null , "
                    + Amount + " text not null , "
                    + Symptoms + " text not null , "
                    + How_to_Take + " text not null);";
        }

        public static class myDBHelper extends SQLiteOpenHelper {
            public myDBHelper(Context context) {
                super(context, "groupDB", null, 1);
            }


            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(DataBases.CreateDB._CREATE0);
                db.execSQL("INSERT INTO storage VALUES(0,'test','test','test','test','test');");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS storage");
                onCreate(db);

            }
        }
    }
}
