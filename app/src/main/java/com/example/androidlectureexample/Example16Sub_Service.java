package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Example16Sub_Service extends Service {
    public Example16Sub_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getExtras().getString("data");
        //로직처리가 길어지면 Thread로 처리

        //결과데이터를 Service에 처리
        String resultData = data + " 를 받았습니다";

        //resultData를 Activity에 전달하여 TextView로 출력
        Intent resultIntent = new Intent(getApplicationContext(), Example16_ServiceDataTransferActivity.class);
        resultIntent.putExtra("result", resultData);
        //service에서 Activity를 호출하려면 Task가 필요하다
        //Activity를 새로 생성하는 것이 아닌 메모리에 존재하는 이전 Activity를 찾아서 실행
        //플래그 2개가 필요함
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(resultIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}













