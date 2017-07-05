package com.example.deepsingh.jbhub;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class success_screen extends Activity {
    Button Btn_continue;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_screen);
        Btn_continue = (Button) findViewById(R.id.button8);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method wil be executed once the timer is over
                // Start your app main activity

                    // do some thing
                Intent i1 = new Intent(success_screen.this, MainActivity.class);
                startActivity(i1);
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
                    finish();


            }
        }, SPLASH_TIME_OUT);
    }
//        Btn_continue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i1 = new Intent(success_screen.this, MainActivity.class);
//                startActivity(i1);
//                finish();
//                overridePendingTransition(R.anim.slide_in_left,
//                        R.anim.slide_out_left);
//            }
//        });
//    }
}
