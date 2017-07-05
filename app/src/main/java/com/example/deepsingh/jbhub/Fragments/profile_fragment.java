package com.example.deepsingh.jbhub.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
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
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepsingh.jbhub.MainActivity;
import com.example.deepsingh.jbhub.Multipart_enttity.AndroidMultiPartEntity;
import com.example.deepsingh.jbhub.Multipart_enttity.Utility;
import com.example.deepsingh.jbhub.Property_listing_screen;
import com.example.deepsingh.jbhub.R;
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
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profile_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog pDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String selectedImagePath = "";
    ImageView Img_image;
    long totalSize = 0;
    String status = "";
    String error = "";
    String Access_tocken = "";
    String Device_id = "";
    String Str_name, Email, Profileimage;
    EditText Edt_Txt_Name, EdtTxt_Email;
    Bitmap bitmap;
    File f;
    Cursor c;
    String picturePath;
    private OnFragmentInteractionListener mListener;
    TextView Txt_profile_change;
    Button Btn_update;
    String Str_name_, Message;

    String Str_result_code = "";

    public profile_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profile_fragment newInstance(String param1, String param2) {
        profile_fragment fragment = new profile_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profile_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Img_image = (ImageView) view.findViewById(R.id.imageView6);
        Edt_Txt_Name = (EditText) view.findViewById(R.id.editText6);
        Txt_profile_change = (TextView) view.findViewById(R.id.textView19);
        EdtTxt_Email = (EditText) view.findViewById(R.id.editText5);
        Btn_update = (Button) view.findViewById(R.id.button7);
        SharedPreferences shared = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nodata"));
        Device_id = (shared.getString("device_id", "nodata"));
        Img_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Utility.checkPermission(getActivity());

                if (result) {
                    selectImage_new();

                    //galleryIntent();


                }
            }
        });
        Txt_profile_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Utility.checkPermission(getActivity());

                if (result) {
                    selectImage_new();

                    //galleryIntent();


                }
            }
        });
        Btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Edt_Txt_Name.getText().toString().contentEquals("")) {
                    Animation anm = Shake_Animation();
                    Edt_Txt_Name.startAnimation(anm);
                    Edt_Txt_Name.setError("Enter name");
                } else {
                    Str_name_ = Edt_Txt_Name.getText().toString();
                    new Update_profile().execute();
                }
            }
        });
        EdtTxt_Email.setEnabled(false);
        new User_profile().execute();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public Animation Shake_Animation() {
        Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);


        return shake;
    }

    private void selectImage_new() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        if (resultCode == getActivity().RESULT_OK) {
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
                    c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
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
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private class Upload_profile_pic extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            pDialog = new ProgressDialog(getActivity());
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

                Toast.makeText(getActivity(), "Profile updated Successfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
            new User_profile().execute();
            //progressBar.setVisibility(View.GONE);
            super.onPostExecute(result);
        }

    }

    private class User_profile extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
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
                        Str_name = jsonObj_data.getString("name");
                        Email = jsonObj_data.getString("email");

                        Profileimage = jsonObj_data.getString("profile_thumb_image");
//                    if (str.contentEquals("true")) {
//                        jsonnode = jsonObj.getJSONObject("data");
//                        json_User = jsonnode.getJSONObject("user");
//
//                        // looping through All data
//                    }
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
            SharedPreferences shared = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("Image", Profileimage);
            editor.putString("Name", Str_name);
            editor.commit();
            Edt_Txt_Name.setText(Str_name);
            EdtTxt_Email.setText(Email);

            Picasso.with(getActivity())
                    .load(Profileimage)
                    .placeholder(R.drawable.profile_icon)   // optional
                    // optional
                    .resize(400, 400).centerCrop().skipMemoryCache()                        // optional
                    .into(Img_image);


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    // Img_image.setImageBitmap(bmRotated);
    public class Background extends AsyncTask<String, Integer, String> {
        Bitmap bmRotated = null;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();

            Img_image.setImageBitmap(bmRotated);

            //str_image3,Str_image4,Str_imag5;
            if (isNetworkAvailable()) {
                new profile_fragment.Upload_profile_pic().execute();
            } else {
                Toast.makeText(getActivity(), "No Network Available", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
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
                        Uri tempUri = getImageUri(getActivity(), bitmap);
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

    private class Update_profile extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
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
            nameValuePairs.add(new BasicNameValuePair("phone", "0000000"));
            nameValuePairs.add(new BasicNameValuePair("name", Str_name_));


            String jsonStr = sh.makeServiceCall_withHeader(SERVER.UPDATE_PROFILE,
                    ServiceHandler.POST, nameValuePairs, Access_tocken, Device_id);

            Log.d("Response: ", "> " + jsonStr);

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

                    status = jsonObj.getString("status");

                    Message = jsonObj.getString("message");

//
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//                    if (str.contentEquals("true")) {
//                        jsonnode = jsonObj.getJSONObject("data");
//                        json_User = jsonnode.getJSONObject("user");
//
//                        // looping through All data
//                    }

            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();

            Toast.makeText(getActivity(), Message, Toast.LENGTH_LONG).show();
            new User_profile().execute();
        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}
