package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class Example17Sub_KAKAOBookSearchService extends Service {
    public Example17Sub_KAKAOBookSearchService() {
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
        String keyword = intent.getExtras().getString("keyword");

        //netword연결을 수행하므로 thread를 이용한다
        kakaoBookSearchRunnable runnable = new kakaoBookSearchRunnable(keyword);
        Thread t = new Thread(runnable);
        t.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class kakaoBookSearchRunnable implements Runnable{
        private String keyword;

        kakaoBookSearchRunnable(String keyword){
            this.keyword = keyword;
        }

        @Override
        public void run() {
            String url = "https://dapi.kakao.com/v3/search/book?target=title";
            url += ("&query=" + keyword);
            try{
                //1. URL 객체 생성
                URL obj = new URL(url);
                //2. URL Connection 을 개방
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                //3. 연결에 대한 설ㅈ어
                //대표적인 호출방식(GET, POST), 인증에 대한 처리
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", "KakaoAK 9fd7ec8dcba519ae58fc8e0bd406ec39");
                //접속이 성공되면 JSON데이터를 받게된다
                //데이터 연결통로 만들어 데이터를 읽어드린다
                //InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer sb = new StringBuffer();
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                Log.i("kakao", sb.toString());
                br.close();
                //doucment : [{책1권}, {책1권}, {책1권}, ....]
                //value값을 객체화 시킨다
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map =
                        mapper.readValue(sb.toString(), new TypeReference<Map<String, Object>>(){});
                Object jsonObject = map.get("documents");
                Log.i("test", jsonObject.toString());
                String jsonStinrg = mapper.writeValueAsString(jsonObject);
                Log.i("test", jsonStinrg);

                ArrayList<KAKAOBookVO> searchBooks =
                        mapper.readValue(jsonStinrg, new TypeReference< ArrayList<KAKAOBookVO>>() {});
                ArrayList<String> resultData = new ArrayList<>();
                for(KAKAOBookVO vo : searchBooks){
                    resultData.add(vo.getTitle());
                }

                Intent resultIntent = new Intent(getApplicationContext(), Example17_KAKAOBookSearchActivity.class);
                resultIntent.putExtra("result", resultData);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(resultIntent);

            }catch (Exception e){
                Log.i("kakao", e.toString());
            }
        }
    }
}




class KAKAOBookVO{
    private ArrayList<String> authors;
    private String contents;
    private String datetime;
    private String isbn;
    private String price;
    private String publisher;
    private String sale_price;
    private String status;
    private String thumbnail;
    private String title;
    private ArrayList<String> translators;
    private String url;

    public KAKAOBookVO() {
    }

    public KAKAOBookVO(ArrayList<String> authors, String contents, String datetime, String isbn, String price, String publisher, String sale_price, String status, String thumbnail, String title, ArrayList<String> translators, String url) {
        this.authors = authors;
        this.contents = contents;
        this.datetime = datetime;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.sale_price = sale_price;
        this.status = status;
        this.thumbnail = thumbnail;
        this.title = title;
        this.translators = translators;
        this.url = url;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(ArrayList<String> translators) {
        this.translators = translators;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}



















