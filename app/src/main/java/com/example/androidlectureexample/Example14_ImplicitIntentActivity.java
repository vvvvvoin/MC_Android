package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Example14_ImplicitIntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example14_implicit_intent);

        Button implicitIntentBtn = findViewById(R.id.implicitIntentBtn);
        implicitIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("MY_ACTION");
                i.addCategory("INTENT_TEST");
                i.putExtra("send data", "hello");
                startActivity(i);
            }
        });
        Button dialBtn = findViewById(R.id.dialBtn);
        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //전화걸기 Activity를 호출하려면 2가지 중 1가지 이용
                //1. 클래스명을 알면 호출할 수 있음
                //2. 묵시적 Intent를 이용해서 알려있는지 Action을 통해서 이용
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:01012345678"));
                startActivity(i);
            }
        });
        Button browserBtn = findViewById(R.id.browserBtn);
        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://naver.com"));
                startActivity(i);
            }
        });
        /*
        전화걸기 기능을 사용하려면 AndroidManifest.xml에 권한설정 추가
        안드로이드 6.0 이상에서는 manifest파일에 기술하는 것 이외에 사용자 권한을 따로 요청
        권한은
        1. 일반권한 normal permission
        2. 위험권한 dangerous permission (개인정보와 연관된 것들)
        */
        Button callBtn = findViewById(R.id.callBtn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자의 안드로이드 버전이 M 버전 이상인지 확인
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    int permissionResult =
                            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
                    if(permissionResult == PackageManager.PERMISSION_DENIED){
                        //권한이 없는 경우
                        //권한 설정을 거부한 적이 있는지 그렇지 않은지 확인
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                            //임의로 권한을 끄거나, 권한 요청화면에서 거부를 눌렀을 경우
                            AlertDialog.Builder dialog =
                                    new AlertDialog.Builder(Example14_ImplicitIntentActivity.this);
                            dialog.setTitle("권한요청에 대한 Dialog");
                            dialog.setMessage("전화걸기 기능 권한을 허용할까요?");
                            dialog.setPositiveButton("허용", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                }
                            });
                            dialog.setNegativeButton("거부", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.show();
                        }else{
                            //권한을 거부한 적이 없음
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                        }
                    }else{
                        //권한이 있는 경우
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:01012345678"));
                        startActivity(i);
                    }
                }else {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:01012345678"));
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:01012345678"));
                startActivity(i);
            }
        }
    }
}
