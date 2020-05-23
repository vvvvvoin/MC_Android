package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Example10_CounterLogProgressActivity extends AppCompatActivity {
    private TextView tv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example10__counter_log_progress);

        tv = findViewById(R.id.sumTv2);
        pb = findViewById(R.id.counterProgress);
        Button startBtn = findViewById(R.id.startBtn2);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thread를 만들어 처리해야함
                CounterRunnable counterRunnable = new CounterRunnable();
                Thread thread = new Thread(counterRunnable);
                thread.start();
            }
        });
        Button secondBtn = findViewById(R.id.secondBtn2);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    //android ui component(widget)는 thread safe하지 않음
    //android ui component는 오직 UI thread에 의해서만 제어되어야 한다
    //화면제어(android ui component, widget)는 오직 UI thread에 의해서만(activity) 제어할 수 있음
    //아래코드는 실행되지만 올바르지 않은 코드
    //외부 Thread에서 UI component를 직접제어할 수 없음
    //handler를 이용해서 이 문제를 해결한다
    class CounterRunnable implements Runnable{
        @Override
        public void run() {
            long sum = 0;
            for (long i = 0; i < 10000000000L; i ++){
                sum += i;
                if(i % 100000000 == 0){
                    long loop = i / 100000000;
                    pb.setProgress((int)loop);
                }
            }
            tv.setText("합계는 : " + sum);
        }
    }
}
