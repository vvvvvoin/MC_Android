package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Example11_CounterLogHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example11_counter_log_handler);

        final TextView tv = findViewById(R.id.sumTv3);
        final ProgressBar pb = findViewById(R.id.counterProgress3);

        //데어터를 주고받는 역할을 수행하는 Handler객체 생성
        // handler 객체는 메세지를 전달한는 method와
        // 메세지를 전달받아서 로직처리하는 method 2개를 주로 사용
        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //받은 메세지를 이용해서 화면처리
                Bundle bundle = msg.getData();
                String count = bundle.getString("count");
                pb.setProgress(Integer.parseInt(count));
            }
        };
        Button startBtn = findViewById(R.id.startBtn3);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thread 생성 후 activity와 데이터를 통신할 수 있는 handler객체를 전달해야함
                MySumThread runnable = new MySumThread(handler);
                Thread t = new Thread(runnable);
                t.start();

            }
        });
    }
}
//연산을 담당하는 thread를 위한 Runnable interface구현한 class
class MySumThread implements Runnable{
    private Handler handler;

    public MySumThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        long sum = 0;
        for (long i = 0; i < 10000000000L; i ++){
            sum += i;
            if(i % 100000000 == 0){
                long loop = i / 100000000;
                //메세지를 만들어서 handler를 이용해서 전달함 (pb.setProgress((int)loop)를 사용못하니)
                //1. bundle 객체를 만듬
                Bundle bundle = new Bundle();
                bundle.putString("count", String.valueOf(loop));
                //2. bundle을 가지는 메세지 객체를 생성
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }
    }
}














