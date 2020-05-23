package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.ResultSet;

/*
안드로이드는 임베디드 형태로 개발된 DB를 내장하고 있음
경량화된 DBMS로 SQLite 이다.

 */
public class Example21_SQLiteBasicActivity extends AppCompatActivity {
    private EditText _21_dbNameEt;
    private EditText _21_tableNameEt;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example21_sqlite_basic);

        _21_dbNameEt = findViewById(R.id._21_dbNameEt);
        _21_tableNameEt = findViewById(R.id._21_tableNameEt);

        Button _21_dbCreateBtn = findViewById(R.id._21_dbCreateBtn);
        _21_dbCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbName = _21_dbNameEt.getText().toString();
                database = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                Log.i("DBTEST", "DB를 생성했습니다");

            }
        });

        Button _21_tableCreateBtn = findViewById(R.id._21_tableCreateBtn);
        _21_tableCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = _21_tableNameEt.getText().toString();
                if (database == null){
                    Log.i("DBTEST", "DB를 생성해주세요");
                    return;
                }
                String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                        "(_id INTEGER PRIMARY KEY autoincrement, name TEXT, age INTEGER, mobile TEXT)";
                database.execSQL(sql);
                Log.i("DBTEST", "Table이 생성되었어요");


            }
        });
        final EditText _21_empNameEt = findViewById(R.id._21_empNameEt);
        final EditText _21_empAgeEt = findViewById(R.id._21_empAgeEt);
        final EditText _21_empMobileEt = findViewById(R.id._21_empMobileEt);

        Button _21_empInsertBtn = findViewById(R.id._21_empInsertBtn);
        _21_empInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _21_empNameEt.getText().toString();
                int age = Integer.parseInt(_21_empAgeEt.getText().toString());
                String mobile = _21_empMobileEt.getText().toString();

                if(database == null){
                    Log.i("DBTEST", "DB를 열어주세요");
                    return;
                }
                String sql = "INSERT INTO emp(name, age, mobile) VALUES " +
                        "('"+ name +"'," + age + ",'" + mobile + "')";
                database.execSQL(sql);
                Log.i("DBTEST", "Insert 되었어요");

                _21_empNameEt.setText("");
                _21_empAgeEt.setText("");
                _21_empMobileEt.setText("");

            }
        });
        final TextView _21_resultTv = findViewById(R.id._21_resultTv);

        Button _21_empSelectBtn = findViewById(R.id._21_empSelectBtn);
        _21_empSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "SELECT _id, name, age, mobile FROM emp";

                if(database == null){
                    Log.i("DBTEST", "DB를 열어주세요");
                    return;
                }

                Cursor cursor = database.rawQuery(sql, null);
                while (cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    int age = cursor.getInt(2);
                    String mobile = cursor.getString(3);

                    String result = "레코드 : " + id + ", " + name + ", " +age + ", " +mobile;
                    _21_resultTv.append(result + "\n");
                }

            }
        });


    }
}
