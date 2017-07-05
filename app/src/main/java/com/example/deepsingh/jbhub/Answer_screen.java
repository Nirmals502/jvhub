package com.example.deepsingh.jbhub;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Answer_screen extends Activity {
    TextView Txt_answer;
    ImageView Img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_answer_screen);
        Txt_answer = (TextView) findViewById(R.id.textView31);

        Img_back = (ImageView) findViewById(R.id.imageView7);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            String Answer = extras.getString("Answer");
            Txt_answer.setText(Answer);
            // and get whatever type user account id is
        }
        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
