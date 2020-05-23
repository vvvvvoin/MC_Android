package com.example.androidlectureexample;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class Example06_SendMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example06_send_message);
        Intent i = getIntent();

        TextView tv = findViewById(R.id.msgTv);
        //String msg = i.getExtras().get("sendMSG").toString();
        String msg = (String)i.getExtras().get("sendMSG");
        tv.setText(msg);

        Button btn = findViewById(R.id.closeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Example06_SendMessageActivity.this.finish();
            }
        });

    }

}
