package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example25_ContactArduinoActivity extends AppCompatActivity {
    private ExecutorService executorService;
    private Socket s;
    private BufferedReader br;
    private PrintWriter pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example25_contact_arduino);

        Button connBtn = findViewById(R.id._25_ConnServer);
        Button disconnBtn = findViewById(R.id._25_DisconnServer);
        Button ledOn = findViewById(R.id._25_LEDOn);
        Button ledOff = findViewById(R.id._25_LEDOff);

        connBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService = Executors.newCachedThreadPool();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            s = new Socket("70.12.60.93", 1357);
                            //Toast.makeText(getApplicationContext(), "접속 성공", Toast.LENGTH_SHORT).show();
                            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                            pr = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                executorService.execute(r);
            }
        });

        disconnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable disconnRunnable = new Runnable() {
                    @Override
                    public void run() {
                        pr.println("@EXIT");
                        pr.flush();
                    }
                };
                executorService.execute(disconnRunnable);
            }
        });

        ledOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable ledOnRunnable = new Runnable() {
                    @Override
                    public void run() {
                        pr.println("1");
                        pr.flush();
                    }
                };
                executorService.execute(ledOnRunnable);
            }
        });

        ledOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable ledOffRunnable = new Runnable() {
                    @Override
                    public void run() {
                        pr.println("0");
                        pr.flush();
                    }
                };
                executorService.execute(ledOffRunnable);
            }
        });

    }

}




















