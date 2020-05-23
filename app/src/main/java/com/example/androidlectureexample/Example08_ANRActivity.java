package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Example08_ANRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example08_anr);

        //final TextView tv = (TextView)findViewById(R.id.sumTv);
        //버튼을 클릭시 오랜시간 연산이 수행
        //결과적으로 ANR(application not responding)발생
        //Activity는 사용자의 상호작용이 최우선
        //Activity는 Thread로 동작
        //로직처리는 background thread를 이용해서 처리
        Button startBtn = (Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                long sum = 0;
                for(long i =0; i < 100000000000L; i++){
                    sum += i;
                }
                Log.i("sumtest", "연산된결과는" + sum);
            }
        });

        Button secondBtn = (Button)findViewById(R.id.secondBtn);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Example08_ANRActivity.this,
                        "버튼이 클릭됨", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
