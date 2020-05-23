package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Example07_DataFromActivity extends AppCompatActivity {
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example07_data_from);
        //spinner안에 표현될 데이터를 만들어요
        final ArrayList<String> list  = new ArrayList<String>();
        list.add("포도");
        list.add("딸기");
        list.add("바나나");
        list.add("사과");
        //spinner의 reference
        Spinner spinner = findViewById(R.id.mySpinner);
        ArrayAdapter adapter =
                new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, list);
        //spinner에 adapter 부탁
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                result = list.get(position);
                Log.i("SELECTED", "선택됨");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button sendBtn = findViewById(R.id.sendDataBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("ResultValue", result);
                setResult(7000, returnIntent);
                Example07_DataFromActivity.this.finish();
            }
        });
    }
}
