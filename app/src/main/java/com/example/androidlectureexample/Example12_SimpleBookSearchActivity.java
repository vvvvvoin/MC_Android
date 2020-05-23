package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Example12_SimpleBookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example12_simple_book_search);

        Button searchBtn = findViewById(R.id.searchBtn);
        final EditText searchTitle = findViewById(R.id.searchTitle);
        final ListView searchList = findViewById(R.id.searchList);

        // web application을 호출해야하기 때문에 activity에서 하면 ANR발생 확률 큼
        // Thread를 이용하며 데이터를 주고받기 위해 Handler를 이용
        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String[] bookList = (String[]) bundle.get("BOOKLIST");
                //listview에 데이터를 설정하려면 adapter를 이용
                final ArrayAdapter adapter =
                        new ArrayAdapter(getApplicationContext(),
                                android.R.layout.simple_list_item_1, bookList);
                searchList.setAdapter(adapter);

            }
        };
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread를 생성
                BookSeaarchRunaable runnable =
                        new BookSeaarchRunaable(handler, searchTitle.getText().toString());
                Thread t = new Thread(runnable);
                // Thread를 시작하려면 start()를 호출
                t.start();
            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                String item = (String) listView.getItemAtPosition(position);
                Intent i = new Intent();
                i.putExtra("title", item);
                ComponentName cname =  new ComponentName("com.example.androidlectureexample",
                        "com.example.androidlectureexample.Example13_DetailBookActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

    }
}

class BookSeaarchRunaable implements Runnable{
    private Handler handler;
    private String keyword;

    BookSeaarchRunaable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        String url = "http://70.12.60.93:8080/bookSearch/searchTitleByKeyword?keyword=" + keyword;
        //java network은 Exception처리를 해야함
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
            Log.i("BookSearch", "얻어온 내용은" + responseTxt.toString());
            // 일반적으로 서버쪽 웹 프로그램은 xml, json결과로 제공해줌
            // 받은 데이터를 자료구조화 시켜 안드로이드 앱에 적용
            // json처리 library중 하나인 JACKSON library를 이용
            ObjectMapper mapper = new ObjectMapper();
            //objectmapper를 이용하여 데이터를 java string array로 변환
            String[] resultArr = mapper.readValue(responseTxt.toString(), String[].class);
            //최종 결과 데이터를 activity에게 전달해야 함
            //번들생성
            Bundle bundle = new Bundle();
            bundle.putStringArray("BOOKLIST", resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);

        } catch (Exception e){
            Log.i("BookSearch", e.toString());
        }
    }
}