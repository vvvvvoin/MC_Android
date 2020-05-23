package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/*
content provider는 App에서 관리하는 데이터를 다른 App이 접근할 수 있도록 해주는 기능
content provider는 안드로이드 구성요소 중의 하나로 안드로이드 시스템에 의해서 관리됨
androidManifest.xml에 등록해서 사용함
content provider를 이용하면 다른 App의 데이터에 접근 가능
content provider가 CRUD를 기반으로 하기에 db를 이용해서 데이터에 접근

1. db를 이용하기 때문에 SQLiteOpenHelper class 생성
 */

class PersonDatabaseHelper extends SQLiteOpenHelper{

    PersonDatabaseHelper(Context context){
        super(context, "person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY autoincrement, name TEXT, age INTEGER, mobile TEXT)";
        db.execSQL(sql);
        Log.i("DBTEST", "Table이 생성되었어요");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

public class Example23_CPExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example23_cp_exam);

        final TextView _23_resultTv = findViewById(R.id._23_resultTv);

        Button _23_empInsertBtn = findViewById(R.id._23_empInsertBtn);
        _23_empInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = "content://com.exam.person.provider/person";
                Uri uri = new Uri.Builder().build().parse(uriString);

                //hashmap형태로 db에 입력할 데이터를 저장
                ContentValues values = new ContentValues();
                values.put("name", "홍길동");
                values.put("age", 30);
                values.put("mobile", "010-1234-5678");

                getContentResolver().insert(uri, values);
                Log.i("DBTEST", "데이터가 입력되었습니다.");
            }
        });


        Button _23_empSelectBtn = findViewById(R.id._23_empSelectBtn);
        _23_empSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DBTEST", "Select clicked");
                String uriString = "content://com.exam.person.provider/person";
                Uri uri = new Uri.Builder().build().parse(uriString);

                //query 파리미터, (접근할 테이블, 조건, 조건내용, 정렬방식)
                //column 명을 Stirng[]으로
                String[] column = new String[]{"name", "age", "mobile"};
                Cursor cursor = getContentResolver().query(uri, column, null, null, "name ASC");
                while(cursor.moveToNext()){
                    String name = cursor.getString(0);
                    int age = cursor.getInt(1);
                    String mobile = cursor.getString(2);

                    String result = "데이터 : " + name + ", " + age + ", " + mobile;
                    _23_resultTv.append(result + "\n");
                }
            }
        });

    }
}























