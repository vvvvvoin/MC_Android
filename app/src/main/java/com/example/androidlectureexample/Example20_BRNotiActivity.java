package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Example20_BRNotiActivity extends AppCompatActivity {
    private BroadcastReceiver br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example20_br_noti);

        Button _20_registerNotiBtn = findViewById(R.id._20_registerNotiBtn);
        _20_registerNotiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. intentFilter를 먼저 만들고 action설정
                IntentFilter filter = new IntentFilter();
                filter.addAction("MY_NOTI_SIGNAL");

                //2. Broadcast를 받는 Receiver를 생성
                br = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        NotificationManager nManager =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        String channelID = "MY_CHANNEL";
                        String channelName = "MY_CHANNEL_NAME";
                        int importance = NotificationManager.IMPORTANCE_HIGH;

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            //안드로이드 오레오 이상일때는 channel을 만들어서 사용해야함
                            NotificationChannel nChannel = new NotificationChannel(channelID, channelName, importance);
                            nChannel.enableVibration(true);
                            nChannel.setVibrationPattern(new long[]{100,200,100,200});
                            nChannel.enableLights(true);
                            nChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                            nManager.createNotificationChannel(nChannel);
                        }
                        //Notification을 만들기 위해 Builder를 이용
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(context.getApplicationContext(), channelID);
                        //notification을 띄우기 위해 Intnet생성
                        Intent nIntent = new Intent(getApplicationContext(), Example20_BRNotiActivity.class);
                        //notification을 activity위에 띄워야 하기때문에 설정 필요
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        int requestID = (int)System.currentTimeMillis();

                        PendingIntent pIntent =
                                PendingIntent.getActivity(getApplicationContext(),
                                        requestID, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentTitle("Notification 제목");
                        builder.setContentText("notification 내용");
                        builder.setDefaults(Notification.DEFAULT_ALL);  //알림의 기본 사운드 진동 설정
                        builder.setAutoCancel(true); //알림 터시치 반응 후 삭제
                        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));//알림음 설정
                        builder.setSmallIcon(android.R.drawable.btn_star);//아이콘 설정
                        builder.setContentIntent(pIntent);

                        nManager.notify(0,builder.build());
                    }
                };
                registerReceiver(br, filter);
            }
        });

        Button _20_sendSignalBtn = findViewById(R.id._20_sendSignalBtn);
        _20_sendSignalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("MY_NOTI_SIGNAL");
                sendBroadcast(i);
            }
        });

    }
}
