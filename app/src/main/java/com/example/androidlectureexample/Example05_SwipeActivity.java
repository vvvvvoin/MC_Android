package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Example05_SwipeActivity extends AppCompatActivity {
    double x1, x2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example05_swipe);

        LinearLayout l1 = (LinearLayout) findViewById(R.id.myLinearlayout);
        l1.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String msg = "";
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    x1 = event.getX();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    x2 = event.getX();
                    if(x1 < x2){
                        msg = "오른쪽 스와이프";
                    }else{
                        msg = "왼쪽으로 스와이프";
                    }
                    Toast.makeText(Example05_SwipeActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }
}
