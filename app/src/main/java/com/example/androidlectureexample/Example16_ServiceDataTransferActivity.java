package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Example16_ServiceDataTransferActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example16_service_data_transfer_activity);

        tv = findViewById(R.id.dataFromServiceTv);
        final EditText editText = findViewById(R.id.dataToService);
        Button dataToServiceBtn = findViewById(R.id.DataToServiceBtn);

        dataToServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Example16Sub_Service.class);
                i.putExtra("data", editText.getText().toString());
                startService(i);
            }
        });
    }

    //service로 부터 intent가 도착하면 method가 호출됨
    @Override
    protected void onNewIntent(Intent intent) {
        String result = intent.getExtras().getString("result");
        tv.setText(result);
        super.onNewIntent(intent);
    }
}
