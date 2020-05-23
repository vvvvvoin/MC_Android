package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

class MyDatabaseHelper extends SQLiteOpenHelper {

    MyDatabaseHelper(Context context, String dbName, int version){
        super(context, dbName, null, version);
        //만약 dbName으로 만든 db가 없으면 version정보와 같이
        //onCreate가 callback -> onOpen
        //만약 dbName으로 만든 db가 있므면
        // onOpen 호출
        //만약 dbName으로 만든 db가 있고 version값이 기본 version값과 다르면
        //onUpgrade 호출
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i("DBTEST", "onOpen 호출");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBTEST", "onCreate 호출");
        String sql = "CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY autoincrement, name TEXT, age INTEGER, mobile TEXT)";
        db.execSQL(sql);
        Log.i("DBTEST", "Table이 생성되었어요");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBTEST", "onUpgrade 호출");

    }

}

public class Example22_SQLiteHelperActivity extends AppCompatActivity {
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example22_sqlite_helper);

        Button _22_dbCreateBtn = findViewById(R.id._22_dbCreateBtn);
        _22_dbCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _22_dbNameEt = findViewById(R.id._22_dbNameEt);
                String dbName = _22_dbNameEt.getText().toString();

                //db를 생성하는데 존재한다면 open만 실행
                //helper클래스를 이용하여 db를 생성 및 open을 수행

                //helper class를 이용해서 db를 생성 및 open을 수행
                MyDatabaseHelper helper =
                        new MyDatabaseHelper(Example22_SQLiteHelperActivity.this, dbName, 2);
                //helper를 통해서 db refeernce를 획득
                database = helper.getWritableDatabase();
                //helper가 생성되면서 db가 만들어지고 onCreate -> onOpen수행


            }
        });
















    }
}
