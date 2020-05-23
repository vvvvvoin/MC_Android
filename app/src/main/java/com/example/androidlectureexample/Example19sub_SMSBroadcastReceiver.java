package com.example.androidlectureexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;

public class Example19sub_SMSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //broadcast를 받으면 이 method가 호출
        //sms가 도착하면 해당 method가 호출됨
        Log.i("SMSTEST", "sms가 도착했음");
        //sms가 도착하면 내용을 추출해서 activity에 전달하면됨

        //Intent안에 포함되어 있는 정보를 추출
        Bundle bundle = intent.getExtras();
        //Bundle안에 key, value 형태로 데이터가 여러개 저장되어 있는데
        //SMS의 정보는 pdus라는 키값으로 저장되어 있음
        Object[] obj = (Object[]) bundle.get("pdus");

        SmsMessage[] messages = new SmsMessage[obj.length];
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            String format = bundle.getString("format");
            messages[0] = SmsMessage.createFromPdu((byte[]) obj[0], format);
        }else {
            messages[0] = SmsMessage.createFromPdu((byte[]) obj[0]);
        }
        String sender = messages[0].getOriginatingAddress();
        String msg = messages[0].getMessageBody();
        String reDate = new Date(messages[0].getTimestampMillis()).toString();

        Log.i("SMSTEST", "전화번호 : " + sender);
        Log.i("SMSTEST", "내용 : " + msg);
        Log.i("SMSTEST", "시간 : " + reDate);

        Intent i = new Intent(context, Example19_BRSMSActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.putExtra("sender", sender);
        i.putExtra("msg", msg);
        i.putExtra("date", reDate);

        context.startActivity(i);


    }
}
