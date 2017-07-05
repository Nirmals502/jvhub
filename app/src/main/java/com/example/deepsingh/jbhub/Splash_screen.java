package com.example.deepsingh.jbhub;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.app.Activity;
import android.view.Window;

public class Splash_screen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    String Access_tocken = "";
    String Str_Check_value = "";
    String Str_experienced = "", Str_fund = "", Str_category = "", StrDesired = "", Str_priceminimu = "", Str_price_maximum = "",
            Str_property_type = "", Str_plan_to_purchase = "", Str_preffer_time = "", Str_looking = "", Str_invest = "", Str_risk = "", Str_location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method wil be executed once the timer is over
                // Start your app main activity
                SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                Access_tocken = (shared.getString("Acess_tocken", "nodata"));
                Str_experienced = (shared.getString("Str_experienced", "nodata"));
                Str_fund = (shared.getString("Str_fund", "nodata"));
                StrDesired = (shared.getString("StrDesired", "nodata"));
                Str_priceminimu = (shared.getString("Str_priceminimu", "nodata"));
                Str_price_maximum = (shared.getString("Str_price_maximum", "nodata"));
                Str_plan_to_purchase = (shared.getString("Str_plan_to_purchase", "nodata"));
                Str_preffer_time = (shared.getString("Str_preffer_time", "nodata"));
                Str_looking = (shared.getString("Str_looking", "nodata"));
                Str_invest = (shared.getString("Str_invest", "nodata"));
                Str_property_type = (shared.getString("Str_property_type", "nodata"));

                if (!Access_tocken.contentEquals("nodata")) {
                    // do some thing
                    if (!Str_experienced.contentEquals("Show All")
                            || !Str_fund.contentEquals("Show All")
                            || !StrDesired.contentEquals("Show All")
                            || !Str_plan_to_purchase.contentEquals("Show All")
                            || !Str_preffer_time.contentEquals("Show All") ||
                            !Str_looking.contentEquals("Show All") ||
                            !Str_invest.contentEquals("Show All") ||
                            !Str_property_type.contentEquals("Show All")) {
                        Intent i1 = new Intent(Splash_screen.this, Property_listing_screen.class);

                        startActivity(i1);

                        finish();

                        overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_left);
                    } else {
                        Intent i1 = new Intent(Splash_screen.this, MainActivity.class);

                        startActivity(i1);

                        finish();

                        overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_left);
                    }

                } else {
                    Intent i = new Intent(Splash_screen.this, Login_screen.class);
                    startActivity(i);

                    finish();

                    overridePendingTransition(R.anim.slide_in_left,
                            R.anim.slide_out_left);
                }


            }
        }, SPLASH_TIME_OUT);
    }
}
