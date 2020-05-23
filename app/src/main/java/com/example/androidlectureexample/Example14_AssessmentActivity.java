package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.databind.ObjectMapper;

;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;


public class Example14_AssessmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example14_assessment);

        final ImageView imageView = findViewById(R.id.detail_One_image);
        final TextView tv_title = findViewById(R.id.detail_One_booktitle);
        final TextView tv_author = findViewById(R.id.detail_One_author);
        final TextView tv_price = findViewById(R.id.detail_One_price);
        final TextView tv_isbn = findViewById(R.id.detail_One_ISBN);

        Intent i = getIntent();
        i.getExtras();
        String isbn = i.getExtras().get("isbn").toString();
        Log.i("onebook", isbn);
        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                final BookVO[] onebook = (BookVO[]) bundle.get("ONEBOOK");

                String imagUrl = onebook[0].getBimgurl();
                Glide.with(getApplicationContext()).load(imagUrl).into(imageView);

                tv_title.setText(onebook[0].getBtitle());
                tv_author.setText(onebook[0].getBauthor());
                tv_price.setText(String.valueOf(onebook[0].getBprice()) + " 원");
                tv_isbn.setText(onebook[0].getBisbn());

            }
        };

        BookSearchOneRunaable runnable =
                new BookSearchOneRunaable(handler, isbn);
        Thread t = new Thread(runnable);
        // Thread를 시작하려면 start()를 호출
        t.start();
    }
}

class BookSearchOneRunaable implements Runnable{
    private Handler handler;
    private String isbn;

    BookSearchOneRunaable(Handler handler, String isbn) {
        this.handler = handler;
        this.isbn = isbn;
    }

    @Override
    public void run() {
        String url = "http://70.12.60.93:8080/bookSearch/bookInfoOne?isbn=" + isbn;
        //java network은 Exception처리를 해야함
        try {
            //URL 객체 생성
            URL obj = new URL(url);
            //URL 객체를 이용하여 접속시도
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Log.i("onebook", "서버로부터 전달된 code : " + responseCode);

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(con.getInputStream()));

            String readLine = "";
            StringBuffer responseTxt = new StringBuffer();
            while((readLine = br.readLine()) != null){
                responseTxt.append(readLine);
            }
            br.close();     //통로사용이 끝났으면 자원반납
            Log.i("onebook", "얻어온 내용은" + responseTxt.toString());

            ObjectMapper mapper = new ObjectMapper();
            BookVO[] resultArr = mapper.readValue(responseTxt.toString(), BookVO[].class);

            Log.i("onebook", "변한된 BookVO 객체 값은 : " + resultArr[0].getBisbn());
            Bundle bundle = new Bundle();
            bundle.putSerializable("ONEBOOK",  resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);

        } catch (Exception e){
            Log.i("BookSearch", e.toString());
        }
    }
}
