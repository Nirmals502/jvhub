package com.example.deepsingh.jbhub;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepsingh.jbhub.Multipart_enttity.Utility;
import com.example.deepsingh.jbhub.Service_handler.SERVER;
import com.example.deepsingh.jbhub.Service_handler.ServiceHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.Detail_adapter;
import adapter.recycleview_adapter;

public class Property_detail_info extends Activity {
    private RecyclerView recyclerView;
    private Detail_adapter mAdapter;
    ArrayList<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
    ViewPager mViewPager;
    CustomPagerAdapter mCustomPagerAdapter;
    ImageView Img_back;
    String id_;
    ProgressDialog pDialog;
    String Access_tocken = "";
    String Device_id = "";
    String status = "";
    String Message = "";
    ArrayList<String> list_ = new ArrayList<String>();

    TextView Price, Txt_adress, Txt_floor_plan, Txt_Counter;
    String id;
    String user_id;
    String specification;
    String description;
    String price;
    String floor_plan;
    String category_name;
    String lat;
    String lng;
    String images;
    String Address;
    String Str_str_counter;
    Button Btn_book_viewing;
    String Str_phone;
    TextView Txt_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_detail_screen_);
        CollapsingToolbarLayout layoutCollapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        layoutCollapsing.setMinimumHeight(380);


        recyclerView = (RecyclerView) findViewById(R.id.Recle_list);


        mViewPager = (ViewPager) findViewById(R.id.View_pager);

        Img_back = (ImageView) findViewById(R.id.imageView7);
        Price = (TextView) findViewById(R.id.textView23);
        Txt_adress = (TextView) findViewById(R.id.textView22);
        Txt_floor_plan = (TextView) findViewById(R.id.textView24);
        Txt_Counter = (TextView) findViewById(R.id.textView29);
        Btn_book_viewing = (Button) findViewById(R.id.button4);
        Txt_header = (TextView) findViewById(R.id.textView18);
        SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nodata"));
        Device_id = (shared.getString("device_id", "nodata"));
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            id_ = extras.getString("id");
            // and get whatever type user account id is
        }
        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        for(int i=0; i< 6;i++)
//    {
        HashMap<String, String> prodHashMap = new HashMap<String, String>();
        prodHashMap.put("image", "star");
        prodHashMap.put("Name", "Key Features");

        listdata.add(prodHashMap);
        HashMap<String, String> prodHashMap2 = new HashMap<String, String>();
        prodHashMap2.put("image", "description");
        prodHashMap2.put("Name", "Description");
        listdata.add(prodHashMap2);
        HashMap<String, String> prodHashMap3 = new HashMap<String, String>();
        prodHashMap3.put("image", "map");
        prodHashMap3.put("Name", "Map");
        listdata.add(prodHashMap3);

        HashMap<String, String> prodHashMap4 = new HashMap<String, String>();
        prodHashMap4.put("image", "floor");
        prodHashMap4.put("Name", "Floor Plan");
        listdata.add(prodHashMap4);

//        HashMap<String, String> prodHashMap5 = new HashMap<String, String>();
//        prodHashMap5.put("image", "epx");
//        prodHashMap5.put("Name", "EPC");
//        listdata.add(prodHashMap5);
//        HashMap<String, String> prodHashMap6 = new HashMap<String, String>();
//        prodHashMap6.put("image", "epx");
//        prodHashMap6.put("Name", "Additional Detail");
//        listdata.add(prodHashMap6);


        // }


//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i1 = new Intent(Property_detail_info.this, Property_detail_info.class);
//                startActivity(i1);
//                finish();
//                overridePendingTransition(R.anim.slide_in_left,
//                        R.anim.slide_out_left);
//            }
//        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String str_position = String.valueOf(position + 1);
                Txt_Counter.setText(str_position + " of " + Str_str_counter);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Btn_book_viewing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Utility.checkPermission(Property_detail_info.this);

                if (result) {


                    //galleryIntent();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Str_phone));

//                if (ActivityCompat.checkSelfPermission(Property_detail_info.this,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
                    startActivity(callIntent);

                }
            }


        });

        new Search_result_info().execute();
    }

    private class Search_result_info extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonObjj;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(Property_detail_info.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);
            //  nameValuePairs.add(new BasicNameValuePair("id", id));

            try {


                String jsonStr = sh.makeServiceCall_withHeader("http://app.jvhub.co.uk/api/properties/show/" + id_,
                        ServiceHandler.POST, nameValuePairs, Access_tocken, Device_id);

                Log.d("Response: ", "> " + jsonStr);

                if (jsonStr != null) {
                    JSONObject jsonObj = null;

                    try {
                        jsonObj = new JSONObject(jsonStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //    String Str_response = jsonObj.("data");
                    //jArr = new JSONArray(Str_response);

//                {"data":{"id":"29","user_id":"3","caption":null,"specification":"dfffff","description":"dfgss","price":"75000.00",
//                    "category":"2",
//                        "property_type":"2","floor_plan":"Fully Furnished with two room set","address":"xgdddd",
//                        "location":"Montpellier, France","plat":"43.61076900","plng":"3.87671600","types":"1",
//                        "status":"1","created_at":"2017-06-08 07:39:33","updated_at":"2017-06-08 09:09:57",
//                        "category_name":"Advisor","property_type_name":"Flip",
//                        "images":[{"id":"73","media_url":"http:\/\/www.app.jvhub.co.uk\/assets\/uploads\/29\/media_url_160x120_14969075734076.png",
//                        "media_thumb_url":"http:\/\/www.app.jvhub.co.uk\/assets\/uploads\/29\/media_thumb_url_150x150_14969075734076.png",
//                        "types":"1"}]},"message":"Success","status":true,"statusCode":200}

                    try {
                        jsonObjj = jsonObj.getJSONObject("data");

                        id = jsonObjj.getString("id");
                        user_id = jsonObjj.getString("user_id");
                        specification = jsonObjj.getString("specification");
                        description = jsonObjj.getString("description");
                        price = jsonObjj.getString("price");
                        floor_plan = jsonObjj.getString("floor_plan");
                        // category_name = jsonObjj.getString("category_name");
                        Address = jsonObjj.getString("address");
                        lat = jsonObjj.getString("plat");
                        lng = jsonObjj.getString("plng");
                        images = jsonObjj.getString("images");
                        Str_phone = jsonObjj.getString("contact_phone");
                        // contact_phone
                        JSONArray jArr_images = null;

                        try {

                            jArr_images = new JSONArray(images);
                            for (int count_images = 0; count_images < jArr_images.length(); count_images++) {
                                try {
                                    JSONObject jsonObjj_images = null;
                                    jsonObjj_images = jArr_images.getJSONObject(count_images);
                                    String Str_images = jsonObjj_images.getString("media_url");
                                    list_.add(Str_images);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                        HashMap<String, String> Search_result = new HashMap<String, String>();
//
//                        // adding each child node to HashMap key => value
//                        Search_result.put("image", image);
//                        Search_result.put("Floor_plan", Floor_plan);
//                        Search_result.put("Address", Adress);
//                        Search_result.put("Description", Description);
//
//                        Search_result.put("Price", Price);
//                        Search_result.put("id", id);


                    // adding contact to contact list
                    //     list_searchresult.add(Search_result);


                    // Getting JSON Array node
                    // JSONArray array1 = null;
                    try {
                        status = jsonObj.getString("status");

                        Message = jsonObj.getString("message");

//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else

                {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            mAdapter = new Detail_adapter(listdata, specification, description, lat, lng, floor_plan);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            if (status.contentEquals("true")) {
//            Price = (TextView) findViewById(R.id.textView23);
//            Txt_adress = (TextView) findViewById(R.id.textView22);
//            Txt_floor_plan = (TextView) findViewById(R.id.textView24);
               // String number = listdata.get(position).get("Price");
                double amount = Double.parseDouble(price);
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formatted = formatter.format(amount);

                // holder.Price.setText("£" + listdata.get(position).get("Price"));
                //holder.Price.setText("£" + formatted);
                Price.setText("£" + formatted);
                Txt_adress.setText(Address);
                Txt_floor_plan.setText(specification);
                mCustomPagerAdapter = new CustomPagerAdapter(Property_detail_info.this, list_);
                mViewPager.setAdapter(mCustomPagerAdapter);
                int int_counter = list_.size();
                Str_str_counter = String.valueOf(int_counter);
                Txt_Counter.setText("1 of " + Str_str_counter);
                Txt_header.setText(Address);

            } else {
                Toast.makeText(Property_detail_info.this, Message, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    //    int[] mResources = {
//            R.drawable.prop,
//            R.drawable.propnew,
//            R.drawable.prop,
//            R.drawable.prop,
//            R.drawable.prop,
//            R.drawable.prop
//    };
    ArrayList<String> list_here = new ArrayList<String>();

    public CustomPagerAdapter(Context context, ArrayList<String> list_) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list_here = list_;
    }

    @Override
    public int getCount() {
        return list_here.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Picasso.with(mContext).load(list_here.get(position)).into(imageView);
        //imageView.setImageResource(mResources[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}
