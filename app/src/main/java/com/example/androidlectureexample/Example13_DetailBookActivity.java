package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class Example13_DetailBookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example13_detail_book);

        final ImageView imageView = findViewById(R.id.detail_image);
        final TextView tv_title = findViewById(R.id.detail_booktitle);
        final TextView tv_author = findViewById(R.id.detail_author);
        final TextView tv_price = findViewById(R.id.detail_price);

        Intent i = getIntent();
        String title = i.getExtras().get("title").toString();
        Log.i("detailbook", title);
        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String image = (String)bundle.get("image");
                String title = (String) bundle.get("title");
                String author = (String) bundle.get("author");
                String price = (String) bundle.get("price");
                try {
                    URL url = new URL(image);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    imageView.setImageBitmap(bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tv_title.setText(title);
                tv_author.setText(author);
                tv_price.setText(price);

            }
        };

        BookDetailRunaable runnable =
                new BookDetailRunaable(handler, title);
        Thread t = new Thread(runnable);
        // Thread를 시작하려면 start()를 호출
        t.start();

    }
}

class BookDetailRunaable implements Runnable{
    private Handler handler;
    private String title;

    BookDetailRunaable(Handler handler, String title) {
        this.handler = handler;
        this.title = title;
    }

    @Override
    public void run() {
        String url = "http://70.12.60.93:8080/bookSearch/search?keyword=" + title;
        try {
            //URL 객체 생성
            URL obj = new URL(url);
            //URL 객체를 이용하여 접속시도
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            //정상접속 여부 확인
            //HTTP protocol 접속상태값
            //200 : 접속성공, 404 : not found, 500 : internal server error
            //403 : forbidden
            //Android app은 특정기능을 사용하기 위해서 보안설정을 해야함
            int responseCode = con.getResponseCode();
            Log.i("BookSearch", "서버로부터 전달된 code : " + responseCode);
            //네트워크 보안설정이 필요, 안드로이드 9 버전부터 강화됨
            //웹 프로토콜이 HTTP에서 HTTPS로 변경됨
            //androidManifest.xml파일에 설정

            //서버와의 연결객체를 이용해서 서버와 데이터 통로를 하나 열어야함, java stream
            //기본적인 연결통로를 이용해서 더 효율적인 연결통로로 바꾸는 과정
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(con.getInputStream()));
            //서버가 보내주는 데이터를 읽어서 하나의 문자열로 만든다.
            String readLine = "";
            StringBuffer responseTxt = new StringBuffer();
            while((readLine = br.readLine()) != null){
                responseTxt.append(readLine);
            }
            br.close();     //통로사용이 끝났으면 자원반납
            String detail = responseTxt.toString();
            Log.i("detailbook", detail);
            JSONArray jarray = new JSONArray(detail);
            JSONObject jObject = jarray.getJSONObject(0);
            String image = jObject.optString("img");
            String title = jObject.optString("title");
            String author = jObject.optString("author");
            String price = jObject.optString("price");
            Log.i("detailbook", "실행됨");

            Bundle bundle = new Bundle();
            bundle.putString("image", image);
            bundle.putString("title", title);
            bundle.putString("author", author);
            bundle.putString("price", price);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);

        } catch (Exception e){
            Log.i("BookSearch", e.toString());
        }
    }
}