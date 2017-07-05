package com.example.deepsingh.jbhub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail_activity_ extends Activity {
    TextView Txt_vw, Txt_header;
    String Str_value;
    String Str_header;
    ImageView Img_back;
    TouchImageView imageView27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_);
        Txt_vw = (TextView) findViewById(R.id.textView31);
        Txt_header = (TextView) findViewById(R.id.textView18);
        Img_back = (ImageView) findViewById(R.id.imageView7);
        imageView27= (TouchImageView) findViewById(R.id.imageView27);
        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            Str_value = extras.getString("value");
            Str_header = extras.getString("value2");
            // and get whatever type user account id is
        }
        if(Str_header.contentEquals("Floor Plan")){
            Txt_vw.setVisibility(View.INVISIBLE);
            imageView27.setVisibility(View.VISIBLE);
            Picasso.with(Detail_activity_.this)
                    .load(Str_value)
                    .placeholder(R.drawable.backendbuilding)   // optional
                    // optional
                    .resize(400, 400).centerCrop()                        // optional
                    .into(imageView27);
        }
        Txt_vw.setText(Str_value);
        Txt_header.setText(Str_header);

    }

}
