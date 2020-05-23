package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class Example25_ArduinoActivity extends AppCompatActivity {
    private TextView _25_statusTv;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pr;

    class SharedObject {
        private Object MONITOR = new Object();
        private LinkedList<String> list = new LinkedList<String>();

        SharedObject() {
        }   // 생성자

        public void put(String s) {
            synchronized (MONITOR) {
                list.addLast(s);
                Log.i("ArduinoTest", "공용객체에 데이터 입력!!");
                MONITOR.notify();
            }
        }

        public String pop() {
            String result = "";
            synchronized (MONITOR) {
                if (list.isEmpty()) {
                    // list안에 문자열이 없으니까 일시 대기해야 해요!
                    try {
                        MONITOR.wait();
                        result = list.removeFirst();
                    } catch (Exception e) {
                        Log.i("ArduinoTest", e.toString());
                    }
                } else {
                    result = list.removeFirst();
                    Log.i("ArduinoTest", "공용객체에서 데이터 추출");
                }
            }

            return result;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example25_arduino);

        _25_statusTv = findViewById(R.id._25_statusTv);

        // 공용객체를 만들어요!
        final SharedObject shared = new SharedObject();

        // Java Network Server에 접속
        // Activity(UI Thread)에서 Network 처리코드를 사용할 수 없어요!
        // 별도의 Thread를 이용해서 처리해야 해요!
        // 1. Runnable 객체를 만들어요!
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("70.12.60.93", 1234);
                    br = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));
                    pr = new PrintWriter(socket.getOutputStream());
                    Log.i("ArduinoTest", "서버에 접속 성공!!");
                    Runnable e = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String str = "";
                                while (true) {
                                    if ((str = br.readLine()) != null) {
                                        if (str.equals("LED_ON_ACK")) {
                                            _25_statusTv.setText(str);
                                        }
                                        if (str.equals("LED_OFF_ACK")) {
                                            _25_statusTv.setText(str);
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                Log.i("ArduinoTest", e.toString());
                            }
                        }
                    };
                    // 2. Thread 객체를 생성한 후 실행
                    Thread t2 = new Thread(e);
                    t2.start();
                    while (true) {
                        String msg = shared.pop();
                        pr.println(msg);
                        pr.flush();
                    }
                } catch (IOException e) {
                    Log.i("ArduinoTest", e.toString());
                }
            }
        };
        Thread t = new Thread(r);
        t.start();




        Button ledOnBtn = findViewById(R.id.ledOnBtn);
        ledOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread가 사용하는 공용객체를 이용해서 메시지를 보낼꺼예요
                // 공용객체에 데이터를 입력
                shared.put("LED_ON");

            }
        });

        Button ledOffBtn = findViewById(R.id.ledOffBtn);
        ledOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread가 사용하는 공용객체를 이용해서 메시지를 보낼꺼예요
                // 공용객체에 데이터를 입력
                shared.put("LED_OFF");

            }
        });
    }
}
