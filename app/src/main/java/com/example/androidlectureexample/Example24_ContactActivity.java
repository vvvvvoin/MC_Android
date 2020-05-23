package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Example24_ContactActivity extends AppCompatActivity {
    private TextView _24_resultTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example24_contact);

        _24_resultTv = findViewById(R.id._24_resultTv);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = ActivityCompat.checkSelfPermission(
                    getApplicationContext(), android.Manifest.permission.READ_CONTACTS);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                //권한이 없으면
                //1. 처음 실행해서 물어본적이 없는 경우
                //2. 권한허용에 대해서 사용자에게 물어봤지만 거절을 선택한 경우
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                    //true일 경우 권한 거부한적이 있는 경우
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Example24_ContactActivity.this);
                    dialog.setTitle("권한요청");
                    dialog.setMessage("주소록 권한이 필요합니다. 권한을 허용할까요?");
                    dialog.setPositiveButton("허용", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, 100);
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
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
                }

            }else{
                //권한이 있으면
                Log.i("contactTest", "보안설정 통과");
                processContact();

            }
        }else {
            Log.i("contactTest", "보안설정 통과");
        }



    }

    private void processContact(){
        Cursor cursor =
                getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null,null,null);
        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Log.i("contactTest", "얻어온 ID : " + id);
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.i("contactTest", "얻어온 이름 : " + name);

            String msg = "";

            Cursor mobileCursor =
                    getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "  + id,
                            null,
                            null);
            while(mobileCursor.moveToNext()) {
                String mobile =
                        mobileCursor.getString(mobileCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                msg = "이름 : " + name +  " 전화번호 : " + mobile;
            }
            _24_resultTv.append(msg + "\n");



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
                Log.i("contactTest", "보안설정 통과");
                processContact();
            }
        }
    }


}
