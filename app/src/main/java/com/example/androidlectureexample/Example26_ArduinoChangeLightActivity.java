package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example26_ArduinoChangeLightActivity extends AppCompatActivity {
    String TAG = "Example26_ArduinoChangeLightActivity";
    private Socket socket;
    private ExecutorService executorService;
    private BufferedReader br;
    private PrintWriter pr;
    static TextView _26_progressTV;

    static class SharedObject {
        private Object MONITOR = new Object();
        private LinkedList<String> list = new LinkedList<String>();

        SharedObject() {
        }   // 생성자

        public void put(String s) {
            synchronized (MONITOR) {
                list.addLast(s);
                Log.i("ArduinoTest", "공용객체에 데이터 입력!!");
                MONITOR.notify();
            }
        }

        public String pop() {
            String result = "";
            synchronized (MONITOR) {
                if (list.isEmpty()) {
                    // list안에 문자열이 없으니까 일시 대기해야 해요!
                    try {
                        MONITOR.wait();
                        result = list.removeFirst();
                    } catch (Exception e) {
                        Log.i("ArduinoTest", e.toString());
                    }
                } else {
                    result = list.removeFirst();
                    Log.i("ArduinoTest", "공용객체에서 데이터 추출");
                }
            }
            return result;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example26_arduino_change_light);
        _26_progressTV = findViewById(R.id._26_progressTV);

        final SharedObject shared = new SharedObject();
        executorService = Executors.newCachedThreadPool();


        Runnable initNextwork = new Runnable() {
            @Override
            public void run() {
                try {
                    //socket = new Socket("70.12.60.91", 1234);
                    socket = new Socket("70.12.230.25", 1234);
                    br = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));
                    CountTask task = new CountTask(br, _26_progressTV);
                    task.execute();
                    pr = new PrintWriter(socket.getOutputStream());
                    while (true) {
                        String msg = shared.pop();
                        pr.println(msg);
                        pr.flush();
                    }
                } catch (Exception e) {
                    Log.i("arduinotest", e.toString());
                }
            }
        };

        executorService.execute(initNextwork);


        final TextView progressTV = findViewById(R.id._26_progressTV);
        SeekBar seekBar = findViewById(R.id._26_Seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shared.put("LIGHT:" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "seekbar low-high", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "seekbar hight-low", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class CountTask extends AsyncTask<String, String, Void> {
    String TAG = "CountTask";
    private BufferedReader br;
    private TextView tv;

    public CountTask(BufferedReader br, TextView textView) {
        this.br = br;
        this.tv = textView;
    }

    @Override
    protected Void doInBackground(String... params) {
        String str = "";
        while (true) {
            Log.v(TAG, "aaa==");
            try {
               str = br.readLine();
                    Log.i("arduinotest", "================" + str);
                    publishProgress(str);
                }
             catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        tv.setText(values[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
