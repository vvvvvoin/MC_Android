package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Example17_KAKAOBookSearchActivity extends AppCompatActivity {
    ListView kakaoBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example17_kakao_book_search);

        final EditText kakaoEt = findViewById(R.id.kakaoEditText);
        Button kakaSearchBtn = findViewById(R.id.kakaoSearchBtn);
        kakaoBookList = findViewById(R.id.kakaoBookList);

        kakaSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), Example17Sub_KAKAOBookSearchService.class);
                i.putExtra("keyword", kakaoEt.getText().toString());
                startService(i);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        ArrayList<String> booksTitle = (ArrayList<String>) intent.getExtras().get("result");
        ArrayAdapter adapter =
                new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, booksTitle);
        kakaoBookList.setAdapter(adapter);
        super.onNewIntent(intent);
    }
}
