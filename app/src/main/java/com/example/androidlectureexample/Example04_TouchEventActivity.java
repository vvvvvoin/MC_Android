package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class Example04_TouchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example04_touch_event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, "소리없는 아우성", Toast.LENGTH_SHORT).show();
        Log.i("MYTEST", "터치됨");
        return super.onTouchEvent(event);
    }
}
