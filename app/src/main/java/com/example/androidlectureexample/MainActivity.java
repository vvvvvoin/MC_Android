package com.example.androidlectureexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button _01_linearlayoutBtn = findViewById(R.id._1_linearlayerBtn);
        _01_linearlayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //새로운 activity를 찾아서 실행
                //2가지 방법이 존재
                //1. explicit 방식
                Intent i  = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example01_LayoutActivity");
                i.setComponent(cname);
                startActivity(i);

                //2. implicit 방식
            }
        });
        Button _02_widgetBtn = (Button)findViewById(R.id._02_widgetBtn);
        _02_widgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example02_WidgetActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        Button _03_EventBtn = (Button)findViewById(R.id._03_EventBtn);
        _03_EventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example03_EventActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _04_toucheventBtn = findViewById(R.id._4_toucheventBtn);
        _04_toucheventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example04_TouchEventActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _05_SwipeEventBtn = findViewById(R.id._5_swipeEventBtn);
        _05_SwipeEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example05_SwipeActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _6_SendMessageBtn = findViewById(R.id._6_SendMessageBtn);
        _6_SendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alert창을 이용해서 문자열을 받고 activity로 전달
                //문자열을 받을 EditText를 생성
                final EditText editText = new EditText(MainActivity.this);
                //Alert Dialog 생성
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("activity data 전달");
                builder.setMessage("다음 Activity에 전달할 내용을 입력하세요");
                builder.setView(editText);
                builder.setPositiveButton("전달", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent();
                        ComponentName cname =  new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example06_SendMessageActivity");
                        i.setComponent(cname);
                        //데이터를 포함해서 새로운 activity에 보내야함
                        i.putExtra("sendMSG", editText.getText().toString());
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        final Button _07_DataFromBtn = findViewById(R.id._7_DataFromBtn);
        _07_DataFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example07_DataFromActivity");
                i.setComponent(cname);
                //activity로 부터 데이터를 받아오기 위한 용도
                //새롭게 만들어진 activity가 finish()되는 순간 데이터를 받아옴
                startActivityForResult(i, 3000);
            }
        });
        final Button _08_ANRBtn = findViewById(R.id._8_ANRBtn);
        _08_ANRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example08_ANRActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _9_CounterLogBtn = findViewById(R.id._9_CounterLogBtn);
        _9_CounterLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example09_CounterLogActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _10_CounterLogProgressBtn = findViewById(R.id._10_CounterLoProgressBtn);
        _10_CounterLogProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example10_CounterLogProgressActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        final Button _11_CounterLogHandlerBtn = findViewById(R.id._11_CounterLogHandlerBtn);
        _11_CounterLogHandlerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example11_CounterLogHandlerActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _12_BookSearchSimpleBtn = findViewById(R.id._12_BookSearchSimpleBtn);
        _12_BookSearchSimpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example12_SimpleBookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _13_BookSearchExtendBtn = findViewById(R.id._13_BookSearchExtendBtn);
        _13_BookSearchExtendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example13_ExtendBookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _14_ImplicitIntentBtn = findViewById(R.id._14_ImplicitIntentBtn);
        _14_ImplicitIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent(명시적 인텐트)
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example14_ImplicitIntentActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        /*
        app이 실행된다고 해서 반드시 Activity가 보이는 것은 아님 예) 카톡, 멜론 등
        service는 화면 없는 Activity 느낌
        */
        final Button _15_ServiceLifeCycleBtn = findViewById(R.id._15_ServiceLifeCycleBtn);
        _15_ServiceLifeCycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example15_ServiceLifeCycleActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _16_ActivityServiceDataBtn = findViewById(R.id._16_ActivityServiceDataBtn);
        _16_ActivityServiceDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example16_ServiceDataTransferActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _17_KAKAOBookSearchBtn = findViewById(R.id._17_KAKAOBookSearchBtn);
        _17_KAKAOBookSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example17_KAKAOBookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _18_BRTestBtn = findViewById(R.id._18_BRTestBtn);
        _18_BRTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example18_BRTestActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _19_BRSMSBtn = findViewById(R.id._19_BRSMSBtn);
        _19_BRSMSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example19_BRSMSActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _20_BRNotiBtn = findViewById(R.id._20_BRNotiBtn);
        _20_BRNotiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example20_BRNotiActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _21_SQLiteBasicBtn = findViewById(R.id._21_SQLiteBasicBtn);
        _21_SQLiteBasicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example21_SQLiteBasicActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _22_SQLiteHelperBtn = findViewById(R.id._22_SQLiteHelperBtn);
        _22_SQLiteHelperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example22_SQLiteHelperActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _23_CPExamBtn = findViewById(R.id._23_CPExamBtn);
        _23_CPExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example23_CPExamActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        final Button _24_contactBtn = findViewById(R.id._24_contactBtn);
        _24_contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example24_ContactActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
        final Button _25_arduinoBtn = findViewById(R.id._25_arduinoBtn);
        _25_arduinoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example25_ContactArduinoActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        final Button _26_arduinoChageLight = findViewById(R.id._26_arduinoChageLight);
        _26_arduinoChageLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname =
                        new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example26_ArduinoChangeLightActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });






    } // end onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3000 && resultCode == 7000){
            String msg = (String)data.getExtras().get("ResultValue");
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
