package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/*
문자메세지에 대한 보안처리
androidmanifest.XML 파일에 기본 보안 설정
Broadcast Receiver Component 생성
 */
public class Example19_BRSMSActivity extends AppCompatActivity {
    private TextView _19_smsSenderTv ;
    private TextView _19_smsMessageTv;
    private TextView _19_smsDateTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example19__br_sms);

        _19_smsSenderTv = findViewById(R.id._19_smsSenderTv);
        _19_smsMessageTv = findViewById(R.id._19_smsMessageTv);
        _19_smsDateTv = findViewById(R.id._19_smsDateTv);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = ActivityCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.RECEIVE_SMS);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                //권한이 없으면
                //1. 처음 실행해서 물어본적이 없는 경우
                //2. 권한허용에 대해서 사용자에게 물어봤지만 거절을 선택한 경우
                if(shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)){
                    //true일 경우 권한 거부한적이 있는 경우
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Example19_BRSMSActivity.this);
                    dialog.setTitle("권한요청");
                    dialog.setMessage("SMS 수신기능 권한이 필요합니다. 권한을 허용할까요?");
                    dialog.setPositiveButton("허용", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 100);
                        }
                    });
                    dialog.setNegativeButton("거절", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }else{
                    //false일 경우 한번도 물어본적 없는 경우
                    requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 100);
                }

            }else{
                //권한이 있으면
                Log.i("SMSTEST", "보안설정 통과");

            }
        }else {
            Log.i("SMSTEST", "보안설정 통과");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //사용자가 권한을 설정하게 되면 이 부분이 마지막으로 호출됨
        //사용자가 권한설정을 하거나 그렇지 않거나 두가지 경우 모두 callback
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //사용자가 권한 허용을 눌렀을 경우
                Log.i("SMSTEST", "보안설정 통과");
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String sender = intent.getExtras().getString("sender");
        String msg = intent.getExtras().getString("msg");
        String date = intent.getExtras().getString("date");

        _19_smsSenderTv.setText(sender);
        _19_smsMessageTv .setText(msg);
        _19_smsDateTv.setText(date);
    }
}
