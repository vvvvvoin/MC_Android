package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class Example15Sub_LifeCycleService extends Service {
    private Thread myThread;

    public Example15Sub_LifeCycleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("service", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //1초 간격으로 1 부터 10 까지 숫자를 로그로 출력
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< 100; i++){
                    try {
                        Thread.sleep(1000);
                        //sleep을 하려고 할때 만약 interrupt가 걸리면 exception발생
                        Log.i("service", "현재 숫자는 : " + i);
                    }catch (Exception e){
                        Log.i("service", e.toString());
                        break;
                    }
                }
            }
        });
        myThread.start();
        Log.i("service", "thread 이름은 : " + myThread.toString());

        // run() method가 호출되고 실행이 끝나면 dead상태로 바뀜
        // dead상태에서 다시 실행시킬 수 있는 방법이 없음
        // 유일하게 thread를 다시 생성해서 실행시켜야함
        Log.i("service", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //stopService()가 호출되면 onDestroy()가 호출됨
        //현재 Service에 의해서 동작되는 모든 Thread를 종료
        super.onDestroy();
        if(myThread != null && myThread.isAlive()){
            //Thread가 존재하고 현재 thread가 실행 중이면 중지시킴
            Log.i("service", "디스토로이드");
            myThread.interrupt();
        }
        Log.i("service", "onDestroy");
    }
}
