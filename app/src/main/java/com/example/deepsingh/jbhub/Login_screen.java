package com.example.deepsingh.jbhub;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepsingh.jbhub.Service_handler.SERVER;
import com.example.deepsingh.jbhub.Service_handler.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Login_screen extends AppCompatActivity {
    EditText emailValidate, Edt_password;
    TextView Txt_signup;
    private ProgressDialog pDialog;
    String status, Message;
    String Str_name, Str_email, Str_password;
    String Device_id, Acess_tocken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        emailValidate = (EditText) findViewById(R.id.editText1);

        Edt_password = (EditText) findViewById(R.id.editText2);
        Txt_signup = (TextView) findViewById(R.id.textView);

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // onClick of button perform this simplest code.

        Txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Login_screen.this, sign_up.class);
                startActivity(i1);
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
            }
        });
        final Button buttonLogin = (Button) findViewById(R.id.button2);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                buttonLogin.startAnimation(buttonClick);
                // final String email = emailValidate.getText().toString().trim();
                if (emailValidate.getText().toString().contentEquals("")) {
                    Animation anm = Shake_Animation();
                    emailValidate.startAnimation(anm);
                } else if (!emailValidate.getText().toString().contains(".co.uk")) {
                    if (!emailValidate.getText().toString().matches(emailPattern)) {
                        Animation anm = Shake_Animation();
                        emailValidate.startAnimation(anm);
                        emailValidate.setError("Invalid email address");
                    }
                    else if (Edt_password.getText().toString().contentEquals("")) {
                        Animation anm = Shake_Animation();
                        Edt_password.startAnimation(anm);
                    } else {
//                    Intent i1 = new Intent(Login_screen.this, MainActivity.class);
//                    startActivity(i1);
//                    finish();
//                    overridePendingTransition(R.anim.slide_in_left,
//                            R.anim.slide_out_left);
                        Str_password = Edt_password.getText().toString();
                        Str_email = emailValidate.getText().toString();
                        new Login().execute();
                    }

                } else if (Edt_password.getText().toString().contentEquals("")) {
                    Animation anm = Shake_Animation();
                    Edt_password.startAnimation(anm);
                } else {
//                    Intent i1 = new Intent(Login_screen.this, MainActivity.class);
//                    startActivity(i1);
//                    finish();
//                    overridePendingTransition(R.anim.slide_in_left,
//                            R.anim.slide_out_left);
                    Str_password = Edt_password.getText().toString();
                    Str_email = emailValidate.getText().toString();
                    new Login().execute();
                }

            }
        });


    }

    public Animation Shake_Animation() {
        Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);


        return shake;
    }

    private class Login extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(Login_screen.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            //nameValuePairs.add(new BasicNameValuePair("email", email_fb));
            nameValuePairs.add(new BasicNameValuePair("emails", Str_email));
            nameValuePairs.add(new BasicNameValuePair("password", Str_password));
            nameValuePairs.add(new BasicNameValuePair("device_id", "dfdgrrfndn458u348rnienfen"));
            nameValuePairs.add(new BasicNameValuePair("os_type", "Android"));
            nameValuePairs.add(new BasicNameValuePair("os_type", "6.0"));
            nameValuePairs.add(new BasicNameValuePair("hardware", "Samsung"));
            nameValuePairs.add(new BasicNameValuePair("app_version", "1"));


            String jsonStr = sh.makeServiceCall(SERVER.LOGIN,
                    ServiceHandler.POST, nameValuePairs);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Getting JSON Array node
                // JSONArray array1 = null;
                try {
                    status = jsonObj.getString("status");

                    Message = jsonObj.getString("message");
                    if (status.contentEquals("true")) {
                        JSONObject jsonObj_data = null;
                        jsonObj_data = jsonObj.getJSONObject("data");
                        Device_id = jsonObj_data.getString("device_id");
                        Acess_tocken = jsonObj_data.getString("access_token");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();

            if (status.contentEquals("true")) {
                SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("Acess_tocken", Acess_tocken);
                editor.putString("device_id", Device_id);
                editor.commit();
                //Toast.makeText(Login_screen.this, status, Toast.LENGTH_LONG).show();

                Intent i1 = new Intent(Login_screen.this, MainActivity.class);
                startActivity(i1);
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
            } else {
                Toast.makeText(Login_screen.this, Message, Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}
