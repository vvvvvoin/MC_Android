package com.example.androidlectureexample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class Example23Sub_PersonContentProvider extends ContentProvider {
    private SQLiteDatabase database;
    //URI 형식 (content provider를 찾기 위한 특수한 문자열)
    //형태 : content://Authority/BASE_PATH(테이블이름)
    //ex) content://com.exam.person.provider/person
    public Example23Sub_PersonContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("DBTEST", "CP의 INSERT 호출");
        //db에 insert하는 방법 중 하나, 집적 sql문을 사용하지 않고
        //ContentValues values에 key, value 형태로 입력할 데이터를 묘사
        database.insert("person",null, values);
        return uri;
    }

    @Override
    public boolean onCreate() {
        Log.i("DBTEST", "CP의 onCreate 호출");
        //여기서 db생성, table생성 코드작성과 db reference를 획득하는 코드가 나오면됨
        PersonDatabaseHelper helper = new PersonDatabaseHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.i("DBTEST", "Query가 실행됨");
        //1번째 인자 : table명
        //2번째 인자 : projection (column명)
        //3번째 인자 : selection (where절 조건을 명시)
        //4번째 인자 : selection의 파라미터 값
        //5번째 인자 : Group by
        //6번째 인자 : having
        //7번째 인자 : 정렬방법
        Cursor cursor =
                database.query("person", projection, selection,
                        selectionArgs, null, null, sortOrder );
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
