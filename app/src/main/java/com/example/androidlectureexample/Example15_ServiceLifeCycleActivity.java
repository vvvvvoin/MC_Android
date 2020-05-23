package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Example15_ServiceLifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example15_service_life_cycle);

        Button startServiceBtn = findViewById(R.id.StartServiceBtn);
        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //service component를 시작
                Intent i = new Intent(getApplicationContext(), Example15Sub_LifeCycleService.class);
                i.putExtra("msg", "소리없는 아우성");
                startService(i);
            }
        });

        Button stopServiceBtn = findViewById(R.id.StopServiceBtn);
        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Example15Sub_LifeCycleService.class);
                stopService(i);
            }
        });
    }
}
