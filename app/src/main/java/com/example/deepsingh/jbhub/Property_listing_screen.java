package com.example.deepsingh.jbhub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepsingh.jbhub.Service_handler.SERVER;
import com.example.deepsingh.jbhub.Service_handler.ServiceHandler;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.recycleview_adapter;

public class Property_listing_screen extends Activity {
    private RecyclerView recyclerView;
    private recycleview_adapter mAdapter;
    ArrayList<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
    ImageView Img_back;
    private ProgressDialog pDialog;
    String Access_tocken = "";
    String Device_id = "";
    String status = "";
    String Message = "";
    TextView Txt_result_count;
    ArrayList<HashMap<String, String>> list_searchresult = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_property_listing_screen);
        recyclerView = (RecyclerView) findViewById(R.id.Recle_list);
        Img_back = (ImageView) findViewById(R.id.imageView7);
        Txt_result_count = (TextView) findViewById(R.id.textView188);
        SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nodata"));
        Device_id = (shared.getString("device_id", "nodata"));
        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Property_listing_screen.this, MainActivity.class);
                startActivity(i1);
                finish();
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_right);
            }
        });
//        for (int i = 0; i < 6; i++) {
//            HashMap<String, String> prodHashMap = new HashMap<String, String>();
//            prodHashMap.put("Test", "$32");
//
//
//            listdata.add(prodHashMap);
//        }


        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Property_listing_screen.this, Property_detail_info.class);
                startActivity(i1);
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
            }
        });

        new Search_result().execute();
    }

    private class Search_result extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(Property_listing_screen.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            list_searchresult.clear();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);
            //nameValuePairs.add(new BasicNameValuePair("email", email_fb));


            String jsonStr = sh.makeServiceCall_withHeader(SERVER.SEARCH_RESULT,
                    ServiceHandler.POST, nameValuePairs, Access_tocken, Device_id);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;

                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jArr = null;
                try {
                    String Str_response = jsonObj.getString("data");
                    jArr = new JSONArray(Str_response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    status = jsonObj.getString("status");

                    Message = jsonObj.getString("message");

//
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(status.contentEquals("true")){
                for (int count = 0; count < jArr.length(); count++) {
                    JSONObject jsonObjj = null;
                    try {
                        jsonObjj = jArr.getJSONObject(count);
                        String image = jsonObjj.getString("image");
                        String Floor_plan = jsonObjj.getString("floor_plan");
                        String Adress = jsonObjj.getString("address");
                        String Price = jsonObjj.getString("price");
                        String Description = jsonObjj.getString("description");
                        String id = jsonObjj.getString("id");
                        HashMap<String, String> Search_result = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        Search_result.put("image", image);
                        Search_result.put("Floor_plan", Floor_plan);
                        Search_result.put("Address", Adress);
                        Search_result.put("Description", Description);

                        Search_result.put("Price", Price);
                        Search_result.put("id", id);


                        // adding contact to contact list
                        list_searchresult.add(Search_result);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                    // Getting JSON Array node
                    // JSONArray array1 = null;

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
                mAdapter = new recycleview_adapter(list_searchresult);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                int Result_count = list_searchresult.size();
                String Str_int_str = String.valueOf(Result_count);
                Txt_result_count.setText(Str_int_str+" Results");
            } else {
                Toast.makeText(Property_listing_screen.this, Message, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}
