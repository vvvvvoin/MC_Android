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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Example13_ExtendBookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example13_extend_book_search);

        Button extendSearchBtn = findViewById(R.id.extendSearchBtn);
        final EditText extendSearchTitle = findViewById(R.id.extendSearchTitle);
        final ListView ExtendSearchList  = findViewById(R.id.ExtendSearchList);
        //network를 이용하여 데이터를 받아오기 위해 Thread가 필요하고
        //thread간 데이터를 전달하기 위해 handler가 필요함
        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                final BookVO[] bookList = (BookVO[]) bundle.get("BOOKLIST");
                //listview에 데이터를 설정하려면 adapter를 이용
                //책 제목에 대한 String[]가 필요함
                String[] titles = new String[bookList.length];
                int i = 0;
                for (BookVO vo : bookList){
                    titles[i++]= vo.getBtitle();
                }
                final ArrayAdapter adapter =
                        new ArrayAdapter(getApplicationContext(),
                                android.R.layout.simple_list_item_1, titles);
                ExtendSearchList.setAdapter(adapter);

                ExtendSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("booksearch", "선택한 위치는 : " + position);
                        Intent i = new Intent();
                        i.putExtra("isbn", bookList[position].getBisbn());
                        ComponentName cn = new ComponentName("com.example.androidlectureexample",
                                "com.example.androidlectureexample.Example14_AssessmentActivity");
                        i.setComponent(cn);
                        startActivity(i);
                    }
                });
            }
        };

        //버튼을 클릭하면 thread가 생성되고 evnet 처리
        extendSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBookInfoRunnable runnable = new MyBookInfoRunnable(handler, extendSearchTitle.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });
    }
}

class MyBookInfoRunnable implements Runnable{
    private Handler handler;
    private String keyword;

    public MyBookInfoRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        String url = "http://70.12.60.93:8080/bookSearch/bookInfo?keyword=" + keyword;
        try {
            //URL 객체 생성
            URL obj = new URL(url);
            //URL 객체를 이용하여 접속시도
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Log.i("BookSearch", "서버로부터 전달된 code : " + responseCode);

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

            ObjectMapper mapper = new ObjectMapper();
            BookVO[] resultArr = mapper.readValue(responseTxt.toString(), BookVO[].class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("BOOKLIST", resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}

 class BookVO {
    private String bisbn;
    private String btitle;
    private String bdate;
    private int bpage;
    private int bprice;
    private String bauthor;
    private String btranslator;
    private String bsupplement;
    private String bpublisher;
    private String bimgurl;
    private String bimgbase64;

    public BookVO() {
        super();
    }

    public String getBisbn() {
        return bisbn;
    }

    public void setBisbn(String bisbn) {
        this.bisbn = bisbn;
    }

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public int getBpage() {
        return bpage;
    }

    public void setBpage(int bpage) {
        this.bpage = bpage;
    }

    public int getBprice() {
        return bprice;
    }

    public void setBprice(int bprice) {
        this.bprice = bprice;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public String getBtranslator() {
        return btranslator;
    }

    public void setBtranslator(String btranslator) {
        this.btranslator = btranslator;
    }

    public String getBsupplement() {
        return bsupplement;
    }

    public void setBsupplement(String bsupplement) {
        this.bsupplement = bsupplement;
    }

    public String getBpublisher() {
        return bpublisher;
    }

    public void setBpublisher(String bpublisher) {
        this.bpublisher = bpublisher;
    }

    public String getBimgurl() {
        return bimgurl;
    }

    public void setBimgurl(String bimgurl) {
        this.bimgurl = bimgurl;
    }

    public String getBimgbase64() {
        return bimgbase64;
    }

    public void setBimgbase64(String bimgbase64) {
        this.bimgbase64 = bimgbase64;
    }
}







