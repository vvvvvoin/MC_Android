package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Example18_BRTestActivity extends AppCompatActivity {
    private BroadcastReceiver bReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example18_br_test);

        Button _18_br_registerBtn = findViewById(R.id._18_br_registerBtn);
        _18_br_registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("MY_BROADCAST_SIGNAL");

                bReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if(intent.getAction().equals("MY_BROADCAST_SIGNAL") ){
                            Toast.makeText(getApplicationContext(), "신호수신됨", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                registerReceiver(bReceiver, intentFilter);
            }
        });

        Button _18_br_unRegisterBtn = findViewById(R.id._18_br_unRegisterBtn);
        _18_br_unRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(bReceiver);
            }
        });

        Button _18_sendBroadcastBtn = findViewById(R.id._18_sendBroadcastBtn);
        _18_sendBroadcastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("MY_BROADCAST_SIGNAL");
                sendBroadcast(i);
            }
        });

    }
}
