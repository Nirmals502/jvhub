package com.example.deepsingh.jbhub;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.deepsingh.jbhub.Fragments.FAQ;
import com.example.deepsingh.jbhub.Fragments.Home_fragment;

import com.example.deepsingh.jbhub.Fragments.contact_us;
import com.example.deepsingh.jbhub.Fragments.post_property_option;
import com.example.deepsingh.jbhub.Fragments.profile_fragment;
import com.example.deepsingh.jbhub.Multipart_enttity.AndroidMultiPartEntity;
import com.example.deepsingh.jbhub.Multipart_enttity.Utility;
import com.example.deepsingh.jbhub.Service_handler.SERVER;
import com.example.deepsingh.jbhub.Service_handler.ServiceHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, profile_fragment.OnFragmentInteractionListener, Home_fragment.OnFragmentInteractionListener, post_property_option.OnFragmentInteractionListener, contact_us.OnFragmentInteractionListener, FAQ.OnFragmentInteractionListener {
    Runnable PendingRunnable;
    Toolbar toolbar;
    private ProgressDialog pDialog;
    String Access_tocken, Device_id;
    TextView Txt_name;
    ImageView Img_profile_pic;
    String Str_Name, Str_image_link;
    String selectedImagePath = "";
    Bitmap bitmap;
    String picturePath;
    String Str_result_code = "";
    String status = "";
    String error = "";
    File f;
    Cursor c;
    long totalSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                //Toast.makeText(MainActivity.this,"Open",Toast.LENGTH_LONG).show();

                SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);


                String Str_name = "";
                Str_name = (shared.getString("Name", "nodata"));
                String Str_image = "";
                Str_image = (shared.getString("Image", "nodata"));
                Txt_name.setText(Str_name);
                try {


                    if (!Str_image.contentEquals(Str_image_link)) {
                        Picasso.with(MainActivity.this)
                                .load(Str_image)
                                .placeholder(R.drawable.profile_icon)   // optional
                                // optional
                                .resize(400, 400).centerCrop().skipMemoryCache()                        // optional
                                .into(Img_profile_pic);
                    }
                } catch (java.lang.NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(toggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        toggle.syncState();


        SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nodata"));
        Device_id = (shared.getString("device_id", "nodata"));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        Txt_name = (TextView) headerView.findViewById(R.id.textView15);
        Img_profile_pic = (ImageView) headerView.findViewById(R.id.imageView4);
        navigationView.setNavigationItemSelectedListener(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Home_fragment fragment = new Home_fragment();


                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment, "2");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }, 0);

        Img_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Utility.checkPermission(MainActivity.this);

                if (result) {
                    selectImage_new();

                    //galleryIntent();


                }
            }
        });
        new User_profile().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.Nave_home) {


            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Home_fragment fragment = new Home_fragment();


                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment, "2");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }, 100);

            // Handle the camera action
        } else if (id == R.id.nav_camera) {
            // toolbar.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    profile_fragment fragment = new profile_fragment();


                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment, "2");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }, 100);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    post_property_option fragment = new post_property_option();


                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment, "2");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }, 100);

        } else if (id == R.id.nav_slideshow) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    contact_us fragment1 = new contact_us();


                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment1, "2");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }, 100);

        } else if (id == R.id.nav_manage) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    FAQ fragment1 = new FAQ();


                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment1, "2");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }, 100);

        } else if (id == R.id.nav_share) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();


                }
            });
            builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(MainActivity.this, Login_screen.class);
                    startActivity(i);
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        } else if (id == R.id.nav_send) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();


                }
            });
            builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(MainActivity.this, Login_screen.class);
                    startActivity(i);
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
//                if (PendingRunnable != null) {
//                  //  mHandler.post(PendingRunnable);
//                    PendingRunnable = null;
//                }
            }
        }, 2);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class User_profile extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            //nameValuePairs.add(new BasicNameValuePair("email", email_fb));


            String jsonStr = sh.makeServiceCall_withHeader(SERVER.CHECKIN,
                    ServiceHandler.POST, nameValuePairs, Access_tocken, Device_id);

            Log.d("Response: ", "> " + jsonStr);
            try {
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(jsonStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Getting JSON Array node
                        // JSONArray array1 = null;
                        JSONObject jsonObj_data = null;
                        jsonObj_data = jsonObj.getJSONObject("data");
                        Str_Name = jsonObj_data.getString("name");


                        Str_image_link = jsonObj_data.getString("profile_thumb_image");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
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

            Txt_name.setText(Str_Name);
            SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("Image", Str_image_link);
            editor.putString("Name", Str_Name);
            editor.commit();

            Picasso.with(MainActivity.this)
                    .load(Str_image_link)
                    .placeholder(R.drawable.profile_icon)   // optional
                    // optional
                    .resize(400, 400).centerCrop().skipMemoryCache()                        // optional
                    .into(Img_profile_pic);


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    private void selectImage_new() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Str_result_code = "1";
                // onCaptureImageResult(data);
                f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                new Background().execute();

                // new UploadFileToServer().execute();
            } else if (requestCode == 2) {
                Str_result_code = "2";
                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    picturePath = c.getString(columnIndex);

                    selectedImagePath = picturePath;

                    new Background().execute();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {

            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

//            try {
//                bitmap.recycle();
//            }catch (java.lang.RuntimeException e){
//                e.printStackTrace();
//            }
            return bmRotated;

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    public String saveimage(Bitmap bmp, String file_name) {
// Find the SD Card path
        String path = null;
        File filepath = Environment.getExternalStorageDirectory();
        OutputStream output;
        // Create a new folder in SD Card

        File dir = new File(filepath.getAbsolutePath()
                + "/JVHUB/");
        if (dir.exists() && dir.isDirectory()) {
            // do something here


            // Create a name for the saved image
            String timeStamp = "JVHUB_pic";
            File file = new File(dir, timeStamp + ".png");

            // Show a toast message on successful save

            try {

                output = new FileOutputStream(file);
                path = file.getAbsolutePath();

                // Compress into png format image from 0% - 100%
                bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            dir.mkdirs();
            String timeStamp = "JVHUB_pic";
            File file = new File(dir, timeStamp + ".png");

            // Show a toast message on successful save

            try {

                output = new FileOutputStream(file);
                path = file.getAbsolutePath();

                // Compress into png format image from 0% - 100%
                bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return path;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public class Background extends AsyncTask<String, Integer, String> {
        Bitmap bmRotated = null;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();

            Img_profile_pic.setImageBitmap(bmRotated);

            //str_image3,Str_image4,Str_imag5;
            if (isNetworkAvailable()) {
                new Upload_profile_pic().execute();
            } else {
                Toast.makeText(MainActivity.this, "No Network Available", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            if (Str_result_code.contentEquals("2")) {


                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(picturePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmapOptions.inSampleSize = 2;
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath, bitmapOptions));
                bmRotated = rotateBitmap(thumbnail, orientation);

                selectedImagePath = saveimage(bmRotated, "JVHUB");


                c.close();
            } else if (Str_result_code.contentEquals("1")) {
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmapOptions.inSampleSize = 2;

                    // selectedImagePath = selectedImageUri.getPath();
                    try {
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);

                        //img_profile_pic.setImageBitmap(bitmap);
                        Uri tempUri = getImageUri(MainActivity.this, bitmap);
                        File finalFile = new File(getRealPathFromURI(tempUri));

                        selectedImagePath = finalFile.getAbsolutePath();
                        ExifInterface exif = null;
                        try {
                            exif = new ExifInterface(f.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        bmRotated = rotateBitmap(bitmap, orientation);
                        //BITMAP_RESIZER
                        //   Bitmap bit_big_image_crop = cropToSquare(bit_big_image);
                        // Bitmap bit_big_image_crop = BITMAP_RESIZER(bit_big_image,400,400);
                        selectedImagePath = saveimage(bmRotated, "jvhub");
                        //  arrPath[position] = squareimagepath;


                        //   Log.w("path of image from gallery......******************.........", picturePath+"");
                        //  Img_image.setImageBitmap(bmRotated);

                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

    }

    private class Upload_profile_pic extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible


            // updating progress bar value
            pDialog.setProgress(progress[0]);

            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(SERVER.CHANGE_IMAGE);
            httppost.addHeader("X-TOKEN", Access_tocken);
            httppost.addHeader("X-DEVICE", Device_id);
            try {


                try {
                    AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                            new AndroidMultiPartEntity.ProgressListener() {

                                @Override
                                public void transferred(long num) {
                                    publishProgress((int) ((num / (float) totalSize) * 100));
                                }
                            });


                    File sourceFile = new File(selectedImagePath);

                    // Adding file data to http body

                    //entity.addPart("gender", new StringBody(gender));

                    entity.addPart("profileimage", new FileBody(sourceFile));


                    // Extra parameters if you want to pass to server

                    totalSize = entity.getContentLength();
                    httppost.setEntity(entity);
//                httppost.addHeader("X-TOKEN",Access_tocken);
//                httppost.addHeader("X-DEVICE",Device_id);

                    // Making server call
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString = EntityUtils.toString(r_entity);
                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                    }

                } catch (ClientProtocolException e) {
                    responseString = e.toString();
                } catch (IOException e) {
                    responseString = e.toString();
                }
                if (responseString != null) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(responseString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Getting JSON Array node
                    // JSONArray array1 = null;
                    try {
                        status = jsonObj.getString("status");
                        error = jsonObj.getString("errorCode");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();
            }
            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            //Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            //  showAlert(result);

            // new User_profile().execute();
            if (status.contentEquals("true")) {

                Toast.makeText(MainActivity.this, "Profile updated Successfully", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();

            //progressBar.setVisibility(View.GONE);
            super.onPostExecute(result);
        }

    }
}
